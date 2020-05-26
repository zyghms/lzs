package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Menu;
import com.zygh.lz.admin.Role;
import com.zygh.lz.admin.Subsystem;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.MenuMapper;
import com.zygh.lz.mapper.RoadMapper;
import com.zygh.lz.mapper.RoleMapper;
import com.zygh.lz.mapper.SubsystemMapper;
import com.zygh.lz.service.menuService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class menuServiceImpl implements menuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SubsystemMapper subsystemMapper;

    @Override
    public ResultBean subMenu(Integer sys_role_id, Integer sys_subsystem_id) {
        Role role = roleMapper.selectByPrimaryKey(sys_role_id);
        String menuuse = role.getMenuUsetype();
        String[] menuuses = menuuse.split(",");
        List<Integer> munuuses = new ArrayList<Integer>();
        for (int i = 0; i < menuuses.length; i++) {
            int munuuse = Integer.valueOf(menuuses[i]);
            munuuses.add(munuuse);
        }
        List<Menu> menus = menuMapper.selectBySubID(sys_subsystem_id);
        List<Integer> munus2 = new ArrayList<Integer>();
        for (int j = 0; j < menus.size(); j++) {
            int munuuse = (Integer) menus.get(j).getSysMenuId();
            munus2.add(munuuse);
        }
        List menuid = getTheSameSection(munuuses, munus2);
        List<Menu> menuList = new ArrayList<Menu>();
        for (int j = 0; j < menuid.size(); j++) {
            menuList.add(menuMapper.selectByPrimaryKey((Integer) menuid.get(j)));
        }
        if(menuList.size() > 0){
            return ResultUtil.setOK("success",menuList);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 查询所有的子系统以及所包含的菜单
     * @return
     */
    @Override
    public ResultBean selectAllSubAndMenu() {
        List<Subsystem> subsystemList = subsystemMapper.selectAllSubsystem();
        for (Subsystem subsystem : subsystemList) {
            List<Menu> menuList = menuMapper.selectBySubID(subsystem.getSysSubsystemId());
            subsystem.setMenuList(menuList);
        }
        return ResultUtil.setOK("success",subsystemList);
    }

    /**
     * 查询多有菜单和子系统
     * @return
     */
    @Override
    public ResultBean selectAllByMenu() {
        List<Menu> menus = menuMapper.selectAllInfoByMenu();
        if(menus.size() > 0){
            return ResultUtil.setOK("success",menus);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     *  新增菜单
     * @param menu
     * @return
     */
    @Override
    public ResultBean insertIntoMenu(Menu menu) {
        return ResultUtil.execOp(menuMapper.insertSelective(menu),"新增");
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @Override
    public ResultBean dateMenu(Menu menu) {
        return ResultUtil.execOp(menuMapper.updateByPrimaryKeySelective(menu),"修改");
    }

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    @Override
    public ResultBean delMenu(Integer id) {
        return ResultUtil.execOp(menuMapper.deleteByPrimaryKey(id),"删除");
    }

    /**
     * 模糊查询
     * @return
     */
    @Override
    public ResultBean selectByName(String name,Integer id) {
        List<Menu> menus = menuMapper.selectByName(name, id);
        if(menus.size() > 0){
            return ResultUtil.setOK("success",menus);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }



    /**
     * 得到两个list交集
     *
     * @param list1
     * @param list2
     * @return
     */

    public List getTheSameSection(List list1, List list2) {
        List resultList = new ArrayList();
        for (Object item : list2) {//遍历list1
            if (list1.contains(item)) {//如果存在这个数
                resultList.add(item);//放进一个list里面，这个list就是交集
            }
        }
        return resultList;
    }
}
