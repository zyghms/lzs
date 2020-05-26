package com.zygh.lz.mapper;

import com.zygh.lz.admin.Basic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasicMapper {

    //查询全部基础设施
    List<Basic> selectAll();

    //根据id删除
    int deleteByPrimaryKey(Integer sysBasicId);

    //添加
    int insert(Basic record);

    int insertSelective(Basic record);

    
    Basic selectByPrimaryKey(Integer sysBasicId);

    
    int updateByPrimaryKeySelective(Basic record);

    
    int updateByPrimaryKey(Basic record);

}