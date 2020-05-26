package com.zygh.lz.controller;

import com.zygh.lz.admin.Task;
import com.zygh.lz.service.taskService;
import com.zygh.lz.service.urbanService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TaskController {
    @Autowired
    private taskService taskService;

    //新增
    @PostMapping("addTask")
    @ViLog(logType = "2", module = "任务列表>新增")
    public ResultBean addTask(Task task, HttpServletRequest request) {
        return taskService.addTask(task);
    }

    //修改
    @GetMapping("updaTask")
    @ViLog(logType = "3", module = "任务列表>修改")
    public ResultBean updaTask(Task task, HttpServletRequest request) {
        return taskService.updaTask(task);
    }

    //删除
    @GetMapping("delTask")
    @ViLog(logType = "4", module = "任务列表>删除")
    public ResultBean delTask(Integer id, HttpServletRequest request) {
        return taskService.delTask(id);
    }

    //查询所有任务
    @GetMapping("selectAllTask")
    @ViLog(logType = "1", module = "任务列表>查询所有任务")
    public ResultBean selectAllTask(HttpServletRequest request) {
        return taskService.selectAllTask();
    }

    //关联问题查询展示任务列表(待办任务列表)
    @GetMapping("selectAllTP")
    @ViLog(logType = "1", module = "任务列表>关联问题查询展示任务列表(待办任务列表)")
    public ResultBean selectAllTP(Integer acceptStaffId, HttpServletRequest request) {
        return taskService.selectAllTP(acceptStaffId);
    }

    //我的任务
    @GetMapping("selectAllByAccept")
    @ViLog(logType = "1", module = "任务列表>我的任务")
    public ResultBean selectAllByAccept(Integer acceptStaffId, String taskState, String taskTitle, String taskCreatetime, String taskFinishtime, HttpServletRequest request) {
        return taskService.selectAllByAccept(acceptStaffId, taskState, taskTitle, taskCreatetime, taskFinishtime);
    }

    //查询默认维修队的人
    @GetMapping("selectStaffNameByProblem")
    @ViLog(logType = "1", module = "任务列表>查询默认维修队的人")
    public ResultBean selectStaffNameByProblem(String type, HttpServletRequest request) {
        return taskService.selectStaffNameByProblem(type);
    }

    //统计
    @GetMapping("selectTaskByCount")
    @ViLog(logType = "1", module = "任务列表>统计")
    public ResultBean selectTaskByCount(String taskState, Integer sectionId, String time, HttpServletRequest request) {
        //request.setAttribute("result",taskService.selectTaskByCount(taskState, sectionId, time));
        return taskService.selectTaskByCount(taskState, sectionId, time);
    }

    //统计
    @GetMapping("selectTPByNum")
    @ViLog(logType = "1", module = "任务列表>统计")
    public ResultBean selectTPByNum(String taskState, Integer sectionId, String time, String staffHierarchy, String staffName, HttpServletRequest request) {
        //request.setAttribute("result",taskService.selectTPByNum(taskState, sectionId, time, staffHierarchy, staffName));
        return taskService.selectTPByNum(taskState, sectionId, time, staffHierarchy, staffName);
    }

    //批量删除
    @GetMapping("deleteTaskById")
    @ViLog(logType = "4", module = "任务列表>批量删除")
    public ResultBean deleteTaskById(int[] array, HttpServletRequest request) {
        //request.setAttribute("result",taskService.deleteTaskById(array));
        return taskService.deleteTaskById(array);
    }

    //查询本大队的任务
    @GetMapping("selectTaskBySection")
    @ViLog(logType = "1", module = "任务列表>查询本大队的任务")
    public ResultBean selectTaskBySection(Integer sectionId, String taskTitle, String taskCreatetime, String taskFinishtime, HttpServletRequest request) {
        //request.setAttribute("result",taskService.selectTaskBySection(sectionId, taskTitle, taskCreatetime, taskFinishtime));
        return taskService.selectTaskBySection(sectionId, taskTitle, taskCreatetime, taskFinishtime);
    }

    //查询任务细节为空的任务数据
    @GetMapping("selectTaskDescribe")
    @ViLog(logType = "1", module = "任务列表>查询任务细节为空的任务数据")
    public ResultBean selectTaskDescribe(Integer sectionId, String taskTitle, String taskCreatetime, String taskFinishtime, HttpServletRequest request) {
        //request.setAttribute("result", taskService.selectTaskDescribe(sectionId, taskTitle, taskCreatetime, taskFinishtime));
        return taskService.selectTaskDescribe(sectionId, taskTitle, taskCreatetime, taskFinishtime);
    }
}
