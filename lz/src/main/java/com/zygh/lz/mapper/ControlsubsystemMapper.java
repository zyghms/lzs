package com.zygh.lz.mapper;

import com.zygh.lz.admin.Controlsubsystem;

public interface ControlsubsystemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Controlsubsystem record);

    int insertSelective(Controlsubsystem record);

    Controlsubsystem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Controlsubsystem record);

    int updateByPrimaryKey(Controlsubsystem record);
}