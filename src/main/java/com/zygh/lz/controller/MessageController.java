package com.zygh.lz.controller;

import com.zygh.lz.admin.message;
import com.zygh.lz.service.messageService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MessageController {
    @Autowired
    private messageService messageService;

    //查询所有消息列表
    @GetMapping("slectAllmessage")
    @ViLog(logType = "1",module = "消息列表>查询所有消息列表")
    public ResultBean slectAllmessage(Integer messageState, Integer accpetId, HttpServletRequest request){
        //request.setAttribute("result",messageService.slectAllmessage(messageState,accpetId));
        return messageService.slectAllmessage(messageState,accpetId);
    }

    //新增消息
    @PostMapping("addMessage")
    @ViLog(logType = "2",module = "消息列表>新增消息")
    public ResultBean addMessage(message message, HttpServletRequest request){
        //request.setAttribute("result",messageService.addMessage(message));
        return messageService.addMessage(message);
    }
    //修改消息 (逻辑删除)
    @GetMapping("updaMessage")
    @ViLog(logType = "3",module = "消息列表>修改消息")
    public ResultBean updaMessage(message message, HttpServletRequest request){
        //request.setAttribute("result",messageService.updaMessage(message));
        return messageService.updaMessage(message);
    }
    //删除
    @GetMapping("delMessage")
    @ViLog(logType = "4",module = "消息列表>删除消息")
    public ResultBean delMessage(Integer id, HttpServletRequest request){
        //request.setAttribute("result",messageService.delMessage(id));
        return messageService.delMessage(id);
    }
    //与问题任务关联的消息
    @GetMapping("selectAllByPid")
    @ViLog(logType = "1",module = "消息列表>查询与问题任务关联的消息")
    public ResultBean selectAllByPid(Integer messagePid, Integer messageType, HttpServletRequest request){
        //request.setAttribute("result",messageService.selectAllByPid(messagePid,messageType));
        return messageService.selectAllByPid(messagePid,messageType);
    }

}
