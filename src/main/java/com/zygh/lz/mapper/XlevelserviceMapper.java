package com.zygh.lz.mapper;

import com.zygh.lz.admin.Xlevelservice;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface XlevelserviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xlevelservice record);

    int insertSelective(Xlevelservice record);

    Xlevelservice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xlevelservice record);

    int updateByPrimaryKey(Xlevelservice record);

    //等级勤务所有应到人数
    Integer selectorderlyAll(String sectionName);

    //一级勤务按大队应到人数
    List<HashMap> selectorderlyoneyd(String sectionName);
    Integer selectyjyd(String sectionName);
    //二级勤务按大队应到人数
    List<HashMap> selectorderlytweyd(String sectionName);
    Integer selectejyd(String sectionName);
    //三级勤务按大队应到人数
    List<HashMap> selectorderlythreeyd(String sectionName);
    Integer selectsjyd(String sectionName);

    //按等级大队查询区域
    List<Xlevelservice> selectxleveBydj(@Param("hierarchy") Integer hierarchy,
                                        @Param("sectionName") String sectionName);

    List<Xlevelservice> selectXleveAll();

}