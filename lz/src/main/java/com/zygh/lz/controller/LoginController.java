package com.zygh.lz.controller;

import com.alibaba.fastjson.JSON;
import com.zygh.lz.admin.Register;
import com.zygh.lz.admin.Staff;
import com.zygh.lz.service.staffService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
    @Autowired
    private staffService staffService;

    //登录
    @PostMapping("staffLogin")
    @ViLog(logType = "0", module = "PC登录")
    public ResultBean staffLogin(String staff_tel, String staff_password, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("policeNum",staff_tel);
        ResultBean resultBean = staffService.selectByLogin(staff_tel, staff_password);
        request.setAttribute("result", JSON.toJSONString(resultBean));
        return resultBean;
    }

    //注册
    @PostMapping("register")
    @ViLog(logType = "6", module = "注册")
    public ResultBean register(Staff staff, HttpServletRequest request) {
        return staffService.register(staff);
    }

    //校验
    @PostMapping("usercheck")
    @ViLog(logType = "6", module = "登录>校验")
    public ResultBean usercheck(String name, HttpServletRequest request) {
        return staffService.usercheck(name);
    }

    //修改
    @GetMapping("modifu")
    @ViLog(logType = "3", module = "人员信息>修改")
    public ResultBean modifu(Staff staff, HttpServletRequest request) {
        return staffService.updaStaffInfoById(staff);
    }

    //修改
    @GetMapping("modifuByPass")
    @ViLog(logType = "3", module = "APP修改密码")
    public ResultBean modifuByPass(Staff staff, HttpServletRequest request) {
        return staffService.modifuByPass(staff);
    }


    //删除
    @GetMapping("delUserBySysId")
    @ViLog(logType = "4", module = "删除")
    public ResultBean delUserBySysId(Integer id, HttpServletRequest request) {
        return staffService.delStaffInfoById(id);
    }

    //登录
    @PostMapping("appLogin")
    @ViLog(logType = "0", module = "APP登录")
    public ResultBean appLogin(String user, String password, String IMEI, String sf, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("policeNum",user);
        ResultBean resultBean = staffService.appLogin(user, password, IMEI, sf);
        request.setAttribute("result", JSON.toJSONString(resultBean));
        return resultBean;
    }

    //登出
    @PostMapping("appLoginOut")
    @ViLog(logType = "5", module = "APP登出")
    public ResultBean appLoginOut(String IMEI, String user, HttpServletRequest request, HttpSession session) {
        session.setAttribute("policeNum", null);
        return staffService.appLoginOut(IMEI, user);
    }

    //是否重新登录（顶掉）
    @PostMapping("updateRegiser")
    @ViLog(logType = "6", module = "登录")
    public ResultBean updateRegiser(Register register, HttpServletRequest request) {
        return staffService.updateRegiser(register);
    }

}
