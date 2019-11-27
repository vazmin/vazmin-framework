package com.github.vazmin.framework.core.service;

/**
 * Service数据层泛型，指定主键为Long型
 * @since v2.0 2014-04-02
 *
 * @param <E> 要操作的数据表对应的实体类
 */
public abstract class LongPKBaseService<E> extends GenericService<E, Long>{
}