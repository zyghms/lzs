package com.zygh.lz.controller;

import com.zygh.lz.admin.Role;
import com.zygh.lz.service.roleService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RoleController {
    @Autowired
    private roleService roleService;

    //查询所有角色
    @GetMapping("selectAllRole")
    @ViLog(logType = "1",module = "道路列表>查询所有角色")
    public ResultBean selectAllRole(HttpServletRequest request) {
        return roleService.selectAllRole();
    }

    //新增角色
    @PostMapping("addRole")
    @ViLog(logType = "2",module = "道路列表>新增角色")
    public ResultBean addRole(Role role,HttpServletRequest request) {
        return roleService.addRole(role);
    }

    //修改
    @GetMapping("modifi")
    @ViLog(logType = "3",module = "道路列表>修改角色")
    public ResultBean modifi(Role role,HttpServletRequest request) {
        return roleService.updateRoleById(role);
    }

    //删除
    @GetMapping("delRoleById")
    @ViLog(logType = "4",module = "道路列表>删除角色")
    public ResultBean delRoleById(Integer id,HttpServletRequest request) {
        return roleService.delRoleById(id);
    }

}
