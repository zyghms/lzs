package com.zygh.lz.service;

import com.zygh.lz.admin.Task;
import com.zygh.lz.vo.ResultBean;

import java.util.List;

public interface taskService {
    //新增
    ResultBean addTask(Task task);

    //修改
    ResultBean updaTask(Task task);

    //删除
    ResultBean delTask(Integer id);

    //查询所有任务
    ResultBean selectAllTask();

    //关联问题查询展示任务列表
    ResultBean selectAllTP(Integer acceptStaffId);

    //我的任务
    ResultBean selectAllByAccept(Integer acceptStaffId,String taskState,String taskTitle,String taskCreatetime,String taskFinishtime);

    ResultBean selectStaffNameByProblem(String probelmType);

    ResultBean selectTaskByCount(String taskState,Integer sectionId,String time);

    ResultBean selectTPByNum(String problemState,Integer sectionId,String time,String staffHierarchy, String staffName);
    //批量删除
    ResultBean deleteTaskById(int[] array);
    //查询本大队的任务
    ResultBean selectTaskBySection(Integer sectionId,String taskTitle,String taskCreatetime,String taskFinishtime);
    //查询任务细节为空的数据
    ResultBean selectTaskDescribe(Integer sectionId,String taskTitle,String taskCreatetime,String taskFinishtime);
}
