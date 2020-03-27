package com.arno.blog.framework.plugin;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.framework.utils.ThreadLocalContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 分頁外掛程式，使用 Page 對象作為輸入和輸出。
 * 只要方法參數中包含 Page 參數，則自動分頁。
 *
 * @author 楊德石
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageablePlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] queryArgs = invocation.getArgs();
        // 查找方法參數中的 分頁請求物件
        int paramIndex = 1;
        Pageable<Object> pageRequest = findPageableObject(queryArgs[paramIndex]);
        // 如果需要分頁
        if (pageRequest != null) {
            int mappedIndex = 0;
            final MappedStatement mappedStatement = (MappedStatement) queryArgs[mappedIndex];
            final Object parameter = queryArgs[paramIndex];
            final BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            // 刪除尾部的 ';'
            String sql = boundSql.getSql().trim().replaceAll(";$", "");
            int total = 0;
            if (pageRequest.getCurrentPage() > 0) {
                // 1. 搞定總記錄數（如果需要的話）
                total = queryTotal(sql, mappedStatement, boundSql);
            }
            // 2. 搞定limit 查詢
            // 2.1 獲取分頁SQL，並完成參數準備
            String limitSql = ThreadLocalContext.get().getDialect().getPageableSql(sql, pageRequest);
            int rowBoundIndex = 2;
            queryArgs[rowBoundIndex] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            queryArgs[mappedIndex] = copyFromNewSql(mappedStatement, boundSql, limitSql);
            // 2.2 繼續執行剩餘步驟，獲取查詢結果
            Object ret = invocation.proceed();
            // 3. 組成分頁物件
            pageRequest.setList((List<Object>) ret);
            pageRequest.setTotalCount(total);
            // 設置總頁數
            int totalPage = (int) Math.ceil(total * 1.0 / pageRequest.getPageSize());
            pageRequest.setTotalPage(totalPage);
            // 4. MyBatis 需要返回一個List物件，這裡只是滿足MyBatis而作的臨時包裝
            List<Pageable<?>> tmp = new ArrayList<>(1);
            tmp.add(pageRequest);
            return tmp;
        }
        return invocation.proceed();
    }

    /**
     * 在方法參數中查找 分頁請求物件
     *
     * @param params Mapper介面方法中的參數物件
     * @return
     */
    private Pageable findPageableObject(Object params) {
        if (params == null) {
            return null;
        }
        // 單個參數 表現為參數物件
        if (Pageable.class.isAssignableFrom(params.getClass())) {
            return (Pageable) params;
        } else if (params instanceof ParamMap) {
            // 多個參數 表現為 ParamMap
            ParamMap<Object> paramMap = (ParamMap<Object>) params;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                Object paramValue = entry.getValue();
                if (paramValue != null && Pageable.class.isAssignableFrom(paramValue.getClass())) {
                    return (Pageable) paramValue;
                }
            }
        }
        return null;
    }

    private Object processTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject mo = SystemMetaObject.forObject(target);
            return processTarget(mo.getValue("h.target"));
        }
        return target;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties p) {
    }

    /**
     * 查詢總記錄數
     *
     * @param sql
     * @param mappedStatement
     * @param boundSql
     * @return
     * @throws SQLException
     */
    private int queryTotal(String sql, MappedStatement mappedStatement, BoundSql boundSql) throws SQLException {
        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            String countSql = ThreadLocalContext.get().getDialect().getCountSql(sql);
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            //對參數賦值
            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
            parameterHandler.setParameters(countStmt);

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            return totalCount;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("查詢總記錄數出錯", e);
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.error("方法執行異常: ResultSet.close()", e);
                }
            }
            if (countStmt != null) {
                try {
                    countStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.error("方法執行異常: PreparedStatement.close()", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    log.error("方法執行異常: Connection.close()", e);
                }
            }
        }
    }

    private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
        return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuffer keyProperties = new StringBuffer();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        //setStatementTimeout()
        builder.timeout(ms.getTimeout());
        //setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());
        //setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }
}
