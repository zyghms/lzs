package com.zygh.lz.service;

import com.zygh.lz.admin.Register;
import com.zygh.lz.admin.Staff;
import com.zygh.lz.admin.Xarea;
import com.zygh.lz.vo.ResultBean;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.List;

public interface staffService {
    //登录
    ResultBean selectByLogin(String staff_tel, String staff_password) ;
    //注册
    ResultBean register(Staff staff);
    //校验用户是否存在
    ResultBean usercheck(String name);
    //根据id修改
    ResultBean updaStaffInfoById(Staff staff);
    //删除
    ResultBean delStaffInfoById(Integer id);
    //根据id查询该人的部门、路长等级和姓名
    ResultBean selectInfoByid(Integer staffId);

    //手机端登录
    ResultBean appLogin(String user, String password, String IMEI, String sf);
    //手机端登出
    ResultBean  appLoginOut(String IMEI, String user);
    //修改唯一标识
    ResultBean updateRegiser(Register register);
    //默认人
    ResultBean selectdefault(String probleDetail);
    //修改密码
    ResultBean modifuByPass(Staff staff);

    ResultBean selectStaffBySectionName(Integer staffid);

    //查询区域里的所有警员信息
    ResultBean selectStaffInfo();

    //查询在岗人数
    ResultBean selectStaffByzg(Integer sectionId, Integer sectionPid, String sectionName);

    //根据名字模糊查询
    ResultBean selectStaffByName(String Name, String staffHierarchy);

    //根据职位判断看的在线人数
    ResultBean selectStaffInfoByid(Integer id);
    ResultBean selectStaffZx(Integer id);

    ResultBean selectStaffYdByAll(String changeShifts, Integer SectionId);

    //查询所有在线民警，并把该民警岗位带出
    ResultBean selectpoliceZx(String station)throws Exception;

    ResultBean selectStaffByid(Integer id);

    //根据id查询民警应巡查路段里程上班时长
    ResultBean selectStaffXareaByid(Integer id)throws Exception;

    //根据id查询民警应巡查路段
    ResultBean selectXareaByid(Integer id)throws Exception;

    //查询昨日总警力
    ResultBean selecttotalforces();
    //按大队查询昨日总警力
    ResultBean selecttotalforceszr();

    ResultBean selectzaBystation(String station, String conment, String grid);

    //查询其他在线民警
    ResultBean selectStaffByqita();

    //以单位为结构显示信息列表
    ResultBean selectAllbytype(String battalion);


    //根据岗位查询各大队在线民警
    ResultBean selectcountBysection(Xarea xarea);


    //根据岗位查询各大队下各中队在线民警
    ResultBean selectcountBydetachment(Xarea xarea);

    //根据岗位查询各中队在线民警详情
    ResultBean selectAllBysection(Xarea xarea);

    /**
     * 对接市局接口  人员信息列表
     * @return
     */
    ResultBean selectStaffByInfo();
}
