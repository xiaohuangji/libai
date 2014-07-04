package com.dajie.wika.weixin.dao.inject;

import org.mybatis.spring.mapper.MapperFactoryBean;

/**
 * Created by wills on 2/13/14.
 */
@Deprecated
public class DAOFactoryBean<T> extends MapperFactoryBean<T> {
    public DAOFactoryBean(Class<T> daoInterface){
        super.setMapperInterface(daoInterface);
    }
}
