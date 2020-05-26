package com.zygh.lz.mapper;

import com.zygh.lz.admin.Subsystem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubsystemMapper {
    int deleteByPrimaryKey(Integer sysSubsystemId);

    int insert(Subsystem record);

    int insertSelective(Subsystem record);

    Subsystem selectByPrimaryKey(Integer sysSubsystemId);

    int updateByPrimaryKeySelective(Subsystem record);

    int updateByPrimaryKey(Subsystem record);

    List<Subsystem> selectAllSubsystem() ;

    List<Subsystem> selectControlsystem() ;
    List<Subsystem> selectControlsubsystem(@Param("id") Integer id);
    List<Subsystem> selectRank() ;
    List<Subsystem> selectRepair() ;
}