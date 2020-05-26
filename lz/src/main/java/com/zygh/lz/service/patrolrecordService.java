package com.zygh.lz.service;

import com.zygh.lz.admin.Patrolrecord;
import com.zygh.lz.admin.Problem;
import com.zygh.lz.vo.ResultBean;

import java.util.HashMap;
import java.util.List;

//巡查
public interface patrolrecordService {
    //新增巡查记录
    ResultBean addPatrolrecord(Patrolrecord patrolrecord);

    //修改巡查记录
    ResultBean updatePatrolrecord(Patrolrecord patrolrecord);

    //删除巡查记录
    ResultBean delPatrolrecord(Integer id);

    //根据道路类型、部门查询所有巡查信息
    ResultBean selectByRoadtype(Integer staffid, String beginTime, String endTime);

    //根据层级显示相对信息（一级路长）
    ResultBean selectByhierarchy(String staffHierarchy, Integer sysSectionId, String roadType);

    //根据层级显示相对信息（二级路长）
    ResultBean selectBysysStaffId(Integer staffid, String roadType);

    //根据sysStaffId修改已打分
    ResultBean updatePatrolrecordBysysStaffId(Patrolrecord patrolrecord);

    //查询未打分的
    ResultBean selectBypatrolstate(Integer staffid, String roadType);

    //统计巡查
    ResultBean countPatrolrecord(String startTime, String endTime) throws Exception;

    //查询所有巡查记录
    ResultBean selectAllPatrolrecord();

    //查询所有巡查记录的视频
    ResultBean selectVideo();

    //根据开始时间，结束时间、道路名称查询巡查记录
    ResultBean selectDim(String roadName, String beginTime, String endTime);

    //查询个人
    ResultBean selectByStaff(Integer SysStaffId, String beginTime, String endTime);

    //手机端
    ResultBean selectByStaffapp(Integer SysStaffId, String beginTime, String endTime);

    //查询各个大队的巡查记录个数
    ResultBean selectRecordNum(String startDate, String endDate);

    //登录用户负责道路列表
    ResultBean selectByStaffId(Integer StaffId);

    ResultBean delectPatrolrecordById(int[] array);

    //根据id查询该人的巡查记录
    ResultBean selectAllPatrolrecordById(Integer id);

    List<HashMap> theDaySum(String battalion) throws Exception;

    List<HashMap> typeSum(String battalion) throws Exception;

    List<HashMap> countZD(String battalion) throws Exception;

    List<HashMap> yXtypeSum(String battalion) throws Exception;

    //按时间段
    List<HashMap> typeSumByTime(String battalion) throws Exception;

    //查询在线人最新的点位
    public List<HashMap> findNowByGps(double[] lon, double[] lat);

    //根据大队名称查询在人GPS
    public List<HashMap> findNowStaffBySection(String time,String battalion,Integer type);

    public HashMap findStaffSum(String time,String battalion,Integer type);

    //判断是否在圆内
    public List<HashMap> findCircleByGps(double circleX, double circleY,double r);

    //根据个人id查询改人当天是否签到
    ResultBean selectinfoByid(Integer id);

    //热力图统计
    List<HashMap> heatMap();

    //应部署GPS
    List<HashMap> heatMapYD();
}
