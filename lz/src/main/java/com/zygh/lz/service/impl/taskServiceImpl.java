package com.zygh.lz.service.impl;

import com.zygh.lz.admin.*;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.*;
import com.zygh.lz.service.taskService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class taskServiceImpl implements taskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProblemdetailMapper problemdetailMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private messageMapper messageMapper;
    @Autowired
    private ProblemMapper problemMapper;


    /**
     * 新增任务
     *
     * @param task
     * @return
     */
    @Override
    public ResultBean addTask(Task task) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String taskmNum = format.format(new Date());
        task.setTaskNum(taskmNum);
        task.setTaskCreatetime(new Date());
        int i = taskMapper.insertSelective(task);
        if (i > 0) {
            Task task1 = taskMapper.selectEnd();
            message message = new message();
            message.setMessageType("2");
            message.setMessagePid(task1.getSysTaskId());
            message.setCreateTime(new Date());
            message.setMessageName("你收到了一条上属给你的任务，请执行！");
            message.setLaunchId(task.getLaunchStaffId());
            message.setAcceptId(task.getAcceptStaffId());
            return ResultUtil.execOp(messageMapper.insertSelective(message), "新增任务消息");
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);

    }

    /**
     * 修改任务
     *
     * @param task
     * @return
     */
    @Override
    public ResultBean updaTask(Task task) {
        if(task.getTaskState().equals("已完成")){
            task.setTaskFinishtime(new Date());
        }
        return ResultUtil.execOp(taskMapper.updateByPrimaryKeySelective(task), "修改");
    }

    /**
     * 根据id删除任务
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean delTask(Integer id) {
        return ResultUtil.execOp(taskMapper.deleteByPrimaryKey(id), "删除");
    }

    /**
     * 查询所有任务
     *
     * @return
     */
    @Override
    public ResultBean selectAllTask() {
        List<Task> tasks = taskMapper.selectAllTask();
        if (tasks.size() > 0) {
            return ResultUtil.setOK("success", tasks);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", tasks);
    }

    /**
     *
     * 关联问题查询展示任务列表
     *
     * @return
     */
    @Override
    public ResultBean selectAllTP(Integer acceptStaffId) {
        List<Task> tasks = taskMapper.selectAllTP(acceptStaffId);
        if (tasks.size() > 0 || tasks.size() == 0) {

            return ResultUtil.setOK("success", tasks);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 我的任务
     *
     * @param acceptStaffId
     * @return
     */
    @Override
    public ResultBean selectAllByAccept(Integer acceptStaffId, String taskState,String taskTitle,String taskCreatetime,String taskFinishtime) {
        List<Object> tasks = taskMapper.selectAllByAccept(acceptStaffId,taskState, taskTitle,taskCreatetime,taskFinishtime);
        if (tasks.size() > 0 || tasks.size() == 0) {
            return ResultUtil.setOK("success", tasks);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }


    /**
     * 查询默认维修队的人
     *
     * @param probelmType
     * @return
     */
    @Override
    public ResultBean selectStaffNameByProblem(String probelmType) {
        Problemdetail selectdetailbytype = problemdetailMapper.selectdetailbytype(probelmType);
        //对应维修队id
        String department = selectdetailbytype.getRepairerid();
        Integer id = Integer.valueOf(department);
        //对应维修队
        Section section = sectionMapper.selectByPrimaryKey(id);
        //维修队负责人
        Integer sectionPersonid = Integer.valueOf(section.getSectionPerson());
        Staff staff = staffMapper.selectByPrimaryKey(sectionPersonid);
        if (staff != null) {
            return ResultUtil.setOK("success", staff);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    @Override
    public ResultBean selectTaskByCount(String taskState, Integer sectionId, String time) {
        List<Task> tasks = taskMapper.selectTaskByCount(taskState, sectionId, time);
        if (tasks.size() > 0) {
            return ResultUtil.setOK("success", tasks);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 统计
     * @param problemState
     * @param sectionId
     * @param time
     * @param staffHierarchy
     * @param staffName
     * @return
     */
    @Override
    public ResultBean selectTPByNum(String problemState, Integer sectionId, String time, String staffHierarchy, String staffName) {
        Map<String, Object> map1 = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Staff> staff = null;

        if (sectionId != null) {
            if (staffName != null && staffName != "" || staffHierarchy != null && staffHierarchy != "") {
                staff = staffMapper.selectStaffByName(staffName, staffHierarchy);
            } else {
                staff = staffMapper.selectAllBySF(sectionId);
            }

        } else if (staffName != null && staffName != "" || staffHierarchy != null && staffHierarchy != "") {
            staff = staffMapper.selectStaffByName(staffName, staffHierarchy);
        } else {
            staff = staffMapper.selectAllStaffPeople();
        }

        for (int j = 0; j < staff.size(); j++) {
            map1 = new HashMap<>();
            List<Task> task1 = taskMapper.selectTaskByNum(problemState, sectionId, time, staff.get(j).getSysStaffId(), staffHierarchy, staffName);
            List<Task> task2 = taskMapper.selectTaskByNum("已完成", sectionId, time, staff.get(j).getSysStaffId(), staffHierarchy, staffName);
            List<Problem> problem1 = problemMapper.selectProblemByNum(problemState, sectionId, time, staff.get(j).getSysStaffId(), staffHierarchy, staffName);
            List<Problem> problem2 = problemMapper.selectProblemByNum("已解决", sectionId, time, staff.get(j).getSysStaffId(), staffHierarchy, staffName);

            if (sectionId != null) {
                Section section = sectionMapper.selectByPrimaryKey(sectionId);
                map1.put("sectionName", section.getSectionName());
            } else {
                Integer sysSectionId = staff.get(j).getSysSectionId();
                Section section1 = sectionMapper.selectByPrimaryKey(sysSectionId);
                map1.put("sectionName", section1.getSectionName());
            }

            map1.put("staffName", staff.get(j).getStaffName());
            map1.put("taskAll", task1);
            map1.put("taskywc", task2);
            map1.put("problemAll", problem1);
            map1.put("problemyjj", problem2);
            list.add(map1);
        }

        if (list.size() > 0) {
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 批量删除
     *
     * @param array
     * @return
     */
    @Override
    public ResultBean deleteTaskById(int[] array) {
        return ResultUtil.execOp(taskMapper.deleteTaskById(array), "批量删除");
    }

    /**
     * 查询本大队的任务
     *
     * @param sectionId
     * @return
     */
    @Override
    public ResultBean selectTaskBySection(Integer sectionId,String taskTitle,String taskCreatetime,String taskFinishtime) {
        List<Task> tasks = taskMapper.selectTaskBySection(sectionId,taskTitle,taskCreatetime,taskFinishtime);
        if (tasks.size() > 0 || tasks.size() == 0) {
            return ResultUtil.setOK("success", tasks);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 查询任务细节为空的任务数据
     *
     * @return
     */
    @Override
    public ResultBean selectTaskDescribe(Integer sectionId,String taskTitle,String taskCreatetime,String taskFinishtime) {
        List<Task> tasks = taskMapper.selectTaskDescribe(sectionId,taskTitle,taskCreatetime,taskFinishtime);
        if (tasks.size() > 0 || tasks.size() == 0) {
            return ResultUtil.setOK("success",tasks);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }


}
