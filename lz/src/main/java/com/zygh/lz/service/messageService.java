package com.zygh.lz.service;

import com.zygh.lz.admin.message;
import com.zygh.lz.vo.ResultBean;

public interface messageService {
    //查询所有消息列表
    ResultBean slectAllmessage(Integer messageState,Integer accpetId);
    //新增消息
    ResultBean addMessage(message message);
    //修改消息 (逻辑删除)
    ResultBean updaMessage(message message);
    //删除
    ResultBean delMessage(Integer id);
    //与问题任务表关联消息信息
    ResultBean selectAllByPid(Integer messagePid,Integer messageType);

}
