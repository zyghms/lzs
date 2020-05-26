package com.zygh.lz.controller;

import com.zygh.lz.admin.Menu;
import com.zygh.lz.service.menuService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MenuController {
    @Autowired
    private menuService menuService;

    //根据角色、根据子系统id推送菜单的函数
    @GetMapping("subMenu")
    @ViLog(logType = "1",module = "菜单管理>根据角色、根据子系统id推送菜单的函数")
    public ResultBean subMenu(Integer sys_role_id, Integer sys_subsystem_id, HttpServletRequest request) {
        return menuService.subMenu(sys_role_id,sys_subsystem_id);
    }

    //查询所有的子系统以及所包含的菜单
    @GetMapping("selectAllSubAndMenu")
    @ViLog(logType = "1",module = "菜单管理>查询所有的子系统以及所包含的菜单")
    public ResultBean selectAllSubAndMenu(HttpServletRequest request){
        return menuService.selectAllSubAndMenu();
    }

    // 查询多有菜单和子系统
    @GetMapping("selectAllByMenu")
    @ViLog(logType = "1",module = "菜单管理>查询多有菜单和子系统")
    public ResultBean selectAllByMenu(HttpServletRequest request){
        return menuService.selectAllByMenu();
    }

    //添加菜单
    @PostMapping("insertIntoMenu")
    @ViLog(logType = "2",module = "菜单管理>添加菜单")
    public ResultBean insertIntoMenu(Menu menu,HttpServletRequest request){
        return menuService.insertIntoMenu(menu);
    }
    //修改菜单
    @GetMapping("updateInfoById")
    @ViLog(logType = "3",module = "菜单管理>修改菜单")
    public ResultBean dateMenu(Menu menu,HttpServletRequest request){
        return menuService.dateMenu(menu);
    }
    //删除菜单
    @GetMapping("delMenu")
    @ViLog(logType = "4",module = "菜单管理>菜单删除")
    public ResultBean delMenu(Integer id,HttpServletRequest request){
        return menuService.delMenu(id);
    }
    //模糊查询
    @GetMapping("selectByName")
    @ViLog(logType = "1",module = "菜单管理>菜单模糊查询")
    public ResultBean selectByName(String name,Integer id,HttpServletRequest request){
        return menuService.selectByName(name,id);
    }
}
