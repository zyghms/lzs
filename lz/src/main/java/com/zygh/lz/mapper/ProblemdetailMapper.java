package com.zygh.lz.mapper;

import com.zygh.lz.admin.Problemdetail;

import java.util.List;

public interface ProblemdetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Problemdetail record);

    int insertSelective(Problemdetail record);

    Problemdetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Problemdetail record);

    int updateByPrimaryKey(Problemdetail record);

    Problemdetail selectdetailbytype(String type);

    //查询所有
    List<Problemdetail> selectAllByProblemdetails();


    List<Problemdetail> selectAllByProblemdetail(Integer id);
}