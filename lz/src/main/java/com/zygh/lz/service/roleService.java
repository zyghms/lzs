package com.zygh.lz.service;

import com.zygh.lz.admin.Role;
import com.zygh.lz.vo.ResultBean;

public interface roleService {
    //查询所有角色
    ResultBean selectAllRole();
    //新增角色
    ResultBean addRole(Role role);
    //修改
    ResultBean updateRoleById(Role role);
    //删除
    ResultBean delRoleById(Integer id);
}
