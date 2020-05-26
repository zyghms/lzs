package com.zygh.lz.controller;

import com.zygh.lz.admin.Notice;
import com.zygh.lz.service.noticeService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class NoticeController {
    @Autowired
    private noticeService noticeService;

    //新增公告
    @PostMapping("addNotice")
    @ViLog(logType = "2",module = "公告列表>新增公告")
    public ResultBean addNotice(Notice notice, HttpServletRequest request){
        //request.setAttribute("result",noticeService.addNotice(notice));
        return noticeService.addNotice(notice);
    }
    //修改公告
    @GetMapping("updaNotice")
    @ViLog(logType = "3",module = "公告列表>修改公告")
    public ResultBean updaNotice(Notice notice,HttpServletRequest request){
        //request.setAttribute("result",noticeService.updaNotice(notice));
        return noticeService.updaNotice(notice);
    }
    //删除公告
    @GetMapping("delNotice")
    @ViLog(logType = "4",module = "公告列表>删除公告")
    public ResultBean delNotice(Integer id,HttpServletRequest request){
        //request.setAttribute("result",noticeService.delNotice(id));
        return noticeService.delNotice(id);
    }
    //根据接受部门查询所有公告
    @GetMapping("selectAllNotice")
    @ViLog(logType = "1",module = "公告列表>根据接受部门查询所有公告")
    public ResultBean selectAllNotice(Integer sectionId,HttpServletRequest request){
        //request.setAttribute("result",noticeService.selectAllNotice(sectionId));
        return noticeService.selectAllNotice(sectionId);
    }

    //查询所有公告
    @GetMapping("selectNotice")
    @ViLog(logType = "1",module = "公告列表>查询所有公告")
    public ResultBean selectNotice(HttpServletRequest request) {
        //request.setAttribute("result",noticeService.selectNotice());
        return noticeService.selectNotice();
    }
}
