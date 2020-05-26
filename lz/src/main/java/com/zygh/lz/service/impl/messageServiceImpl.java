package com.zygh.lz.service.impl;

import com.zygh.lz.admin.message;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.messageMapper;
import com.zygh.lz.service.messageService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class messageServiceImpl implements messageService {
    @Autowired
    private messageMapper messageMapper;

    /**
     * 查询所有消息列表
     * @return
     */
    @Override
    public ResultBean slectAllmessage(Integer messageState,Integer accpetId) {
        List<message> messages = messageMapper.slectAllmessage(messageState,accpetId);
        if(messages.size() > 0||messages.size() == 0){
            return ResultUtil.setOK("success",messages);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 新增
     * @param message
     * @return
     */
    @Override
    public ResultBean addMessage(message message) {
        return ResultUtil.execOp(messageMapper.insertSelective(message),"新增");
    }

    /**
     * 修改   （逻辑删除   0表示删除 1表示存在）(消息状态   0表示未读 1表示已读，默认为0)
     * @param message
     * @return
     */
    @Override
    public ResultBean updaMessage(message message) {
        return ResultUtil.execOp(messageMapper.updateByPrimaryKeySelective(message),"修改");
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public ResultBean delMessage(Integer id) {
        return ResultUtil.execOp(messageMapper.deleteByPrimaryKey(id),"删除");
    }

    /**
     * 与问题任务关联的消息
     * @param messagePid
     * @param messageType
     * @return
     */
    @Override
    public ResultBean selectAllByPid(Integer messagePid, Integer messageType) {
        if(messageType==1){
            List<message> messages = messageMapper.selectProblemByPid(messagePid);
            if(messages.size() >= 0){
                return ResultUtil.setOK("success",messages);
            }
        }else if(messageType==2){
            List<message> messages = messageMapper.selectTaskByPid(messagePid);
            if(messages.size() >=0){
                return ResultUtil.setOK("success",messages);
            }
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
