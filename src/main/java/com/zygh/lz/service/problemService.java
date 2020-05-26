package com.zygh.lz.service;

import com.zygh.lz.admin.Problem;
import com.zygh.lz.vo.ResultBean;

import javax.servlet.http.HttpServletRequest;


public interface problemService {
    //新增
    ResultBean addProblem(Problem problem);

    //修改
    ResultBean updateProblem(Problem problem);

    //删除
    ResultBean delProblem(Integer id);

    //根据人查询全部问题  (所有问题)
    ResultBean selectProblemByStaffId(Integer staffReportId);

    //查询问题解决未解决个数
    ResultBean selectProblemByRosove(Integer id);

    //根据道路类型查询问题
    ResultBean selectAllByRoadType(Integer sysStaffId, String roadType);

    //问题模糊查询
    ResultBean selectDims(HttpServletRequest request,Integer sysStaffId, String RoadType, String staffName, String sectionName,
                          String ProblemStrat, String staffHierarchy,
                          String beginTime, String endTime, String problemCheck, String problemDetail);

    //根据人查询问题
    ResultBean selectDimStaff(Integer SysStaffId, String beginTime, String endTime);

    //领导管理的人所查询问题的模糊查询
    ResultBean selectDimStaffOther(Integer SysStaffId, String beginTime, String endTime);

    //巡查记录个数统计
    ResultBean selectDimAll(String startTime, String endTime);

    ResultBean selectDimAllSy();

    //按条件查询没有核查的问题
    ResultBean selectProblem(Integer staffAcceptId, String roadName, String problemStrat);

    //查询所有除去已完成以外的多有问题
    ResultBean selectProblemByState();

    ResultBean selectProblemByCount(String problemState, Integer sectionId, String time);

    ResultBean selectProblemByid(Integer id, Integer type);

    //所有不能解决的问题
    ResultBean selectProbleByStart(String problemState);

    ResultBean selectByProblemdetail(String typeKey,
                                     String problemStrat,
                                     String beginTime, String endTime);

    ResultBean deleteByProblemid(int[] array);

    //首页统计所有
    ResultBean selectAllCount();

}
