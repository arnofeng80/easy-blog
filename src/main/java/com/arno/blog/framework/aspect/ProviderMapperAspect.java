package com.arno.blog.framework.aspect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.ibatis.binding.MapperProxy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.arno.blog.framework.mapper.BaseMapper;
import com.arno.blog.framework.utils.ProviderContext;
import com.arno.blog.framework.utils.ReflectionUtils;
import com.arno.blog.framework.utils.ThreadLocalContext;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * mybatis切面
 * @author Arno
 */
@Aspect
@Component
@Order(10)
@Slf4j
public class ProviderMapperAspect {

    private static Map<String, ProviderContext> providerContextMap = Maps.newConcurrentMap();

    @Pointcut("execution(* com.arno.blog.*.mapper..*(..))")
    public void mapperExecution() {
    }

    @Before("mapperExecution()")
    public void setDynamicDataSource(JoinPoint point) {
        Class entityClass = null;
        Object target = point.getTarget();
        // 判斷切點是否為BaseMapper子類，用來獲取類
        if (BaseMapper.class.isAssignableFrom(target.getClass())) {
            MapperProxy mapperProxy = (MapperProxy) Proxy.getInvocationHandler(target);
            Class mapperInterface = (Class) ReflectionUtils.getFieldValue(mapperProxy, "mapperInterface");
            ParameterizedType parameterizedType = (ParameterizedType) mapperInterface.getGenericInterfaces()[0];
            Type[] types = parameterizedType.getActualTypeArguments();
            try {
                entityClass = Class.forName(types[0].getTypeName());
                if (!providerContextMap.containsKey(entityClass.getName())) {
                    ProviderContext providerContext = new ProviderContext();
                    providerContext.setEntityClass(entityClass);
                    providerContext.setIdClass(Class.forName(types[1].getTypeName()));
                    providerContextMap.put(entityClass.getName(), providerContext);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        if (entityClass != null) {
            ThreadLocalContext.get().setProviderContext(providerContextMap.get(entityClass.getName()));
        }
    }
}