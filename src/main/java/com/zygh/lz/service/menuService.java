package com.zygh.lz.service;

import com.zygh.lz.admin.Menu;
import com.zygh.lz.vo.ResultBean;

public interface menuService {
    ResultBean subMenu(Integer sys_role_id, Integer sys_subsystem_id);
    //查询所有的子系统以及所包含的菜单
    ResultBean selectAllSubAndMenu();
    //查询多有菜单和子系统
    ResultBean selectAllByMenu();
    //添加菜单
    ResultBean insertIntoMenu(Menu menu);
    //修改菜单
    ResultBean dateMenu(Menu menu);
    //删除菜单
    ResultBean delMenu(Integer id);
    //模糊查询
    ResultBean selectByName(String name,Integer id);


}
