package com.zygh.lz.mapper;

import com.zygh.lz.admin.Xdeclare;

public interface XdeclareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xdeclare record);

    int insertSelective(Xdeclare record);

    Xdeclare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xdeclare record);

    int updateByPrimaryKey(Xdeclare record);
}