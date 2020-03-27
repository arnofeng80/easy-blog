package com.arno.blog.framework.mapper;

import java.io.Serializable;

/**
 * Mapper標誌，最終每個mapper都繼承這個
 * @author Arno
 */
public interface MybatisMapper<T, ID extends Serializable> extends CrudMapper<T, ID> {
}