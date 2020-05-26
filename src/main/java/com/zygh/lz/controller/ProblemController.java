package com.zygh.lz.controller;

import com.zygh.lz.admin.Problem;
import com.zygh.lz.service.patrolrecordService;
import com.zygh.lz.service.problemService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProblemController {
    @Autowired
    private problemService problemService;

    //新增
    @PostMapping("addProblem")
    @ViLog(logType = "2",module = "问题模块>问题新增")
    public ResultBean addProblem(Problem problem, HttpServletRequest request){
        //request.setAttribute("result", problemService.addProblem(problem));
        return problemService.addProblem(problem);
    }
    //修改
    @GetMapping("updateProblem")
    @ViLog(logType = "3",module = "问题模块>问题修改")
    public ResultBean updateProblem(Problem problem,HttpServletRequest request){
        //request.setAttribute("result", problemService.updateProblem(problem));
        return problemService.updateProblem(problem);
    }
    //删除
    @GetMapping("delProblem")
    @ViLog(logType = "4",module = "问题模块>问题删除")
    public ResultBean delProblem(Integer id,HttpServletRequest request){
        //request.setAttribute("result", problemService.delProblem(id));
        return problemService.delProblem(id);
    }
    //根据人查询全部问题  (所有问题)
    @GetMapping("selectProblemByStaffId")
    @ViLog(logType = "1",module = "问题模块>根据人查询全部问题  (所有问题)")
    public ResultBean selectProblemByStaffId(Integer staffReportId,HttpServletRequest request){
        //request.setAttribute("result", problemService.selectProblemByStaffId(staffReportId));
        return problemService.selectProblemByStaffId(staffReportId);
    }
    //查询问题解决未解决个数
    @GetMapping("selectProblemByRosove")
    @ViLog(logType = "1",module = "问题模块>查询问题解决未解决个数")
    public ResultBean selectProblemByRosove(Integer id,HttpServletRequest request){
        //request.setAttribute("result", problemService.selectProblemByRosove(id));
        return problemService.selectProblemByRosove(id);
    }
    //根据道路类型查询问题
    @GetMapping("selectAllByRoadType")
    @ViLog(logType = "1",module = "问题模块>根据道路类型查询问题")
    public ResultBean selectAllByRoadType(Integer sysStaffId,String roadType,HttpServletRequest request){
        //request.setAttribute("result", problemService.selectAllByRoadType(sysStaffId,roadType));
        return problemService.selectAllByRoadType(sysStaffId,roadType);
    }

    //问题模糊查询分层
    @GetMapping("selectDims")
    @ViLog(logType = "1",module = "问题模块>问题模糊查询分层")
    public ResultBean selectDims(Integer sysStaffId, String roadName, String staffName,
                                 String sectionName, String problemStrat,
                                 String staffHierarchy, String beginTime,
                                 String endTime,String problemCheck,String problemDetail,
                                 HttpServletRequest request){
        //request.setAttribute("result", problemService.selectDims(request,sysStaffId,roadName,staffName,sectionName,problemStrat,staffHierarchy,beginTime,endTime,problemCheck,problemDetail));
        return problemService.selectDims(request,sysStaffId,roadName,staffName,sectionName,problemStrat,staffHierarchy,beginTime,endTime,problemCheck,problemDetail);
    }
    //根据人查询问题
    @GetMapping("selectDimStaff")
    @ViLog(logType = "1",module = "问题模块>问题模糊查询分层")
    public ResultBean selectDimStaff(Integer sysStaffId,String beginTime,String endTime,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectDimStaff(sysStaffId,beginTime,endTime));
        return problemService.selectDimStaff(sysStaffId,beginTime,endTime);
    }

    //领导管理的人所查询问题的模糊查询
    @GetMapping("selectDimStaffOther")
    @ViLog(logType = "1",module = "问题模块>领导管理的人所查询问题的模糊查询")
    public ResultBean selectDimStaffOther(Integer staffId, String beginTime, String endTime,HttpServletRequest request) {
       //request.setAttribute("result",problemService.selectDimStaffOther(staffId,beginTime,endTime));
        return problemService.selectDimStaffOther(staffId,beginTime,endTime);
    }

    //巡查记录个数统计
    @GetMapping("selectDimAll")
    @ViLog(logType = "1",module = "问题模块>巡查记录个数统计")
    public ResultBean selectDimAll(String startTime, String endTime,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectDimAll(startTime,endTime));
        return problemService.selectDimAll(startTime,endTime);
    }
    //巡查记录个数统计
    @GetMapping("selectDimAllSy")
    @ViLog(logType = "1",module = "问题模块>巡查记录个数统计")
    public ResultBean selectDimAllSy(HttpServletRequest request){
        //request.setAttribute("result",problemService.selectDimAllSy());
        return problemService.selectDimAllSy();
    }

    //按条件查询没有核查的问题
    @GetMapping("selectProblem")
    @ViLog(logType = "1",module = "问题模块>按条件查询没有核查的问题")
    public ResultBean selectProblem(Integer staffAcceptId, String roadName,String problemStrat,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectProblem(staffAcceptId, roadName,problemStrat));
        return problemService.selectProblem(staffAcceptId, roadName,problemStrat);
    }

    //查询所有除去已完成以外的多有问题
    @GetMapping("selectProblemByState")
    @ViLog(logType = "1",module = "问题模块>查询所有除去已完成以外的多有问题")
    public ResultBean selectProblemByState(HttpServletRequest request){
        //request.setAttribute("result",problemService.selectProblemByState());
        return problemService.selectProblemByState();
    }

    //问题统计
    @GetMapping("selectProblemByCount")
    @ViLog(logType = "1",module = "问题模块>问题统计")
    public ResultBean selectProblemByCount(String problemState,Integer sectionId, String time,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectProblemByCount(problemState,sectionId,time));
        return problemService.selectProblemByCount(problemState,sectionId,time);
    }

    //根据主键id查询问题详情
    @GetMapping("selectProblemByid")
    @ViLog(logType = "1",module = "问题模块>根据主键id查询问题详情")
    public ResultBean selectProblemByid(Integer id,Integer type,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectProblemByid(id,type));
        return problemService.selectProblemByid(id,type);
    }

    //查询所有不能解决的问题
    @GetMapping("selectProbleByStart")
    @ViLog(logType = "1",module = "问题模块>查询所有不能解决的问题")
    public ResultBean selectProbleByStart(String problemState,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectProbleByStart(problemState));
        return problemService.selectProbleByStart(problemState);
    }

    //模糊查询
    @GetMapping("selectByProblemdetail")
    @ViLog(logType = "1",module = "问题模块>模糊查询")
    public ResultBean selectByProblemdetail(String typeKey, String problemStrat, String beginTime, String endTime,HttpServletRequest request){
        //request.setAttribute("result",problemService.selectByProblemdetail(typeKey,problemStrat,beginTime,endTime));
        return problemService.selectByProblemdetail(typeKey,problemStrat,beginTime,endTime);
    }

    //批量删除
    @GetMapping("deleteByProblemid")
    @ViLog(logType = "1",module = "问题模块>批量删除")
    public ResultBean deleteByProblemid(int[] array,HttpServletRequest request){
        //request.setAttribute("result",problemService.deleteByProblemid(array));
        return problemService.deleteByProblemid(array);
    }

    //统计所有
    @GetMapping("selectAllCount")
    @ViLog(logType = "1",module = "问题模块>统计所有")
    public ResultBean selectAllCount(HttpServletRequest request){
        //request.setAttribute("result",problemService.selectAllCount());
        return problemService.selectAllCount();
    }
}
