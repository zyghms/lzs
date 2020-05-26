package com.zygh.lz.mapper;

import com.zygh.lz.admin.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer sysMenuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer sysMenuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List selectBySubID(Integer sys_subsystem_id) ;

    //根据菜单名字和子系统id进行查询
    List<Menu> selectByName(@Param("name") String name,@Param("id") Integer id);
    // 联表查询所有菜单和子系统
    List<Menu> selectAllInfoByMenu();

}