package com.zygh.lz.mapper;

import com.zygh.lz.admin.Xrelation;

public interface XrelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xrelation record);

    int insertSelective(Xrelation record);

    Xrelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xrelation record);

    int updateByPrimaryKey(Xrelation record);
}