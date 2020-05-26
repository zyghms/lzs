package com.zygh.lz.mapper;

import com.zygh.lz.admin.Register;
import org.apache.ibatis.annotations.Param;

public interface RegisterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Register record);

    int insertSelective(Register record);

    Register selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Register record);

    int updateByPrimaryKey(Register record);

    //判断用户是否存过
    Register selectByUserPss(String userName);

    //判断唯一标识是否一样
    Register selectByIMEI(String IMEI);

    //根据用户名修改
    int updateRegisterByUser(@Param("IMEI") String IMEI,@Param("user") String user);


}