package com.arno.blog.framework.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.arno.blog.framework.provider.MybatisProvider;
import com.arno.blog.framework.utils.Pageable;

/**
 * 基本的增刪改查方法定義區
 *
 * @author Arno
 */
public interface CrudMapper<T, ID extends Serializable> extends BaseMapper<T, ID> {

    /**
     * 保存
     *
     * @param entity 要保存的對象
     * @return
     */
    @InsertProvider(type = MybatisProvider.class, method = MybatisProvider.SAVE)
    int save(T entity);

    /**
     * 更新
     *
     * @param entity 要更新的物件，必須要有id
     * @return
     */
    @UpdateProvider(type = MybatisProvider.class, method = MybatisProvider.UPDATE)
    int update(T entity);

    /**
     * 批量保存
     *
     * @param entities 要批量保存的物件集合
     * @return
     */
    @InsertProvider(type = MybatisProvider.class, method = MybatisProvider.SAVE_BATCH)
    int saveBatch(List<T> entities);

    /**
     * 批量更新
     * 無樂觀鎖
     *
     * @param entities 要批量更新的物件集合，每個物件必須要有id
     * @return
     */
    @UpdateProvider(type = MybatisProvider.class, method = MybatisProvider.UPDATE_BATCH)
    int updateBatch(List<T> entities);

    /**
     * 查詢所有
     * 內部使用select * 全表查詢，性能較低，不建議使用
     *
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_ALL)
    @ResultMap("BaseResultMap")
    List<T> findAll();

    /**
     * 查詢所有非基本欄位
     * 創建人，創建時間，更新人，更新時間，樂觀鎖，邏輯刪除不查
     *
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_ALL_NO_BASE)
    @ResultMap("BaseResultMap")
    List<T> findAllNoBase();

    /**
     * 查詢一個
     *
     * @param id id
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_BY_ID)
    @ResultMap("BaseResultMap")
    T findById(ID id);

    /**
     * 用多個id查詢
     *
     * @param list id集合
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_BY_IDS)
    @ResultMap("BaseResultMap")
    List<T> findByIds(List<ID> list);

    /**
     * 查詢總數
     *
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.COUNT)
    Long count();

    /**
     * 刪除一個
     *
     * @param id 要刪除的id
     * @return
     */
    @UpdateProvider(type = MybatisProvider.class, method = MybatisProvider.REMOVE_BY_ID)
    int removeById(ID id);

    /**
     * 根據id批量刪除
     *
     * @param list 要刪除的id列表
     * @return
     */
    @UpdateProvider(type = MybatisProvider.class, method = MybatisProvider.REMOVE_BY_IDS)
    int removeByIds(List<ID> list);

    /**
     * 根據page查詢
     *
     * @param page 分頁查詢參數
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_AUTO_BY_PAGE)
    @ResultMap("BaseResultMap")
    Pageable<T> findAutoByPage(Pageable<T> page);

    /**
     * 根據實體類作為參數查詢，只返回查詢出來的第一條資料
     *
     * @param t
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_ONE)
    @ResultMap("BaseResultMap")
    T findOne(T t);

    /**
     * 根據實體類作為參數查詢，返回全部
     *
     * @param t
     * @return
     */
    @SelectProvider(type = MybatisProvider.class, method = MybatisProvider.FIND_BY_ENTITY)
    @ResultMap("BaseResultMap")
    List<T> findByEntity(T t);

}
