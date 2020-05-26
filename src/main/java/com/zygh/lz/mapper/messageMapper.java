package com.zygh.lz.mapper;

import com.zygh.lz.admin.message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface messageMapper {
    int deleteByPrimaryKey(Integer sysMessageId);

    int insert(message record);

    int insertSelective(message record);

    message selectByPrimaryKey(Integer sysMessageId);

    int updateByPrimaryKeySelective(message record);

    int updateByPrimaryKey(message record);

    //消息列表
    List<message> slectAllmessage(@Param("messageState") Integer messageState,@Param("accpetId") Integer accpetId);

    //消息与问题任务关联
    List<message> selectProblemByPid(Integer messagePid);
    List<message> selectTaskByPid(Integer messagePid);
}