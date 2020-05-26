package com.zygh.lz.mapper;

import com.zygh.lz.admin.Xsuboffice;

public interface XsubofficeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xsuboffice record);

    int insertSelective(Xsuboffice record);

    Xsuboffice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xsuboffice record);

    int updateByPrimaryKey(Xsuboffice record);
}