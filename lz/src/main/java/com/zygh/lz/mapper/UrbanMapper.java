package com.zygh.lz.mapper;

import com.zygh.lz.admin.Urban;

import java.util.List;

public interface UrbanMapper {
    int deleteByPrimaryKey(Integer sysUrbanId);

    int insert(Urban record);

    int insertSelective(Urban record);

    Urban selectByPrimaryKey(Integer sysUrbanId);

    int updateByPrimaryKeySelective(Urban record);

    int updateByPrimaryKey(Urban record);

    //查询全部区域
    List<Urban>  selectAllUrban();

    List<Urban> seleteDimUrban(Urban urban) ;

    //根据区域统计
    List<Urban> selectUrbanByCount(Integer sectionId);
}