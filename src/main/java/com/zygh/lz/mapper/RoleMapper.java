package com.zygh.lz.mapper;

import com.zygh.lz.admin.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer sysRoleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer sysRoleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAllRole();

}