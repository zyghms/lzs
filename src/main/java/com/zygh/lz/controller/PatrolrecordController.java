package com.zygh.lz.controller;

import com.zygh.lz.admin.Patrolrecord;
import com.zygh.lz.mapper.PatrolrecordMapper;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * 巡查记录
 */
@RestController
public class PatrolrecordController {
    @Autowired
    private com.zygh.lz.service.patrolrecordService patrolrecordService;
    @Autowired
    private PatrolrecordMapper patrolrecordMapper;

    //查询所有巡查记录
    @GetMapping("selectAllPatrolrecord")
    @ViLog(logType = "1", module = "查询所有巡查记录")
    public ResultBean selectAllPatrolrecord() {
        return patrolrecordService.selectAllPatrolrecord();
    }

    //查询所有巡查记录视频
    @GetMapping("selectVideo")
    @ViLog(logType = "1", module = "查询所有巡查记录视频")
    public ResultBean selectVideo() {
        return patrolrecordService.selectVideo();
    }

    //新增巡查记录
    @PostMapping("addPatrolrecord")
    @ViLog(logType = "1", module = "新增巡查记录")
    public ResultBean addPatrolrecord(Patrolrecord patrolrecord) {
        return patrolrecordService.addPatrolrecord(patrolrecord);
    }

    //根据开始时间，结束时间、道路名称查询巡查记录
    @GetMapping("selectDim")
    @ViLog(logType = "1", module = "根据开始时间，结束时间、道路名称查询巡查记录")
    public ResultBean selectDim(String roadName, String beginTime, String endTime) {
        return patrolrecordService.selectDim(roadName, beginTime, endTime);
    }

    //根据道路类型、部门查询所有巡查信息(分层级查询)
    @GetMapping("selectByRoadtype")
    @ViLog(logType = "1", module = "根据道路类型、部门查询所有巡查信息(分层级查询)")
    public ResultBean selectByRoadtype(Integer staffid, String beginTime, String endTime) {
        return patrolrecordService.selectByRoadtype( staffid, beginTime, endTime);
    }

    //根据时间查询个人巡查记录
    @GetMapping("selectByStaffs")
    @ViLog(logType = "1", module = "根据时间查询个人巡查记录")
    public ResultBean selectByStaffs(Integer SysStaffId, String beginTime, String endTime) {
        return patrolrecordService.selectByStaff(SysStaffId, beginTime, endTime);
    }
    //根据时间查询个人巡查记录app
    @GetMapping("selectByStaff")
    @ViLog(logType = "1", module = "根据时间查询个人巡查记录app")
    public ResultBean selectByStaff(Integer SysStaffId, String beginTime, String endTime) {
        return patrolrecordService.selectByStaffapp(SysStaffId, beginTime, endTime);
    }

    //查询各个大队的巡查记录个数
    @GetMapping("selectRecordNum")
    @ViLog(logType = "1", module = "查询各个大队的巡查记录个数")
    public ResultBean selectRecordNum(String startDate, String endDate) {
        return patrolrecordService.selectRecordNum(startDate, endDate);
    }

    //巡查统计
    @GetMapping("countPatrolrecord")
    @ViLog(logType = "1", module = "巡查统计")
    public ResultBean countPatrolrecord(String startTime, String endTime) throws Exception {
        return patrolrecordService.countPatrolrecord(startTime, endTime);
    }

    //根据登录用户负责道路列表
    @GetMapping("selectByStaffId")
    @ViLog(logType = "1", module = "根据登录用户负责道路列表")
    public ResultBean selectByStaffId(Integer staffId) {
        return  patrolrecordService.selectByStaffId(staffId);
    }

    //查询最后一条巡查记录
    @GetMapping("selectBylast")
    @ViLog(logType = "1", module = "查询最后一条巡查记录")
    public Patrolrecord selectBylast(){
        return patrolrecordMapper.selectBylast();
    }

    //修改巡查记录
    @GetMapping("updatePatrolrecord")
    @ViLog(logType = "1", module = "修改巡查记录")
    public ResultBean updatePatrolrecord(Patrolrecord patrolrecord){
        return patrolrecordService.updatePatrolrecord(patrolrecord);
    }

    //删除巡查记录
    @GetMapping("delPatrolrecord")
    @ViLog(logType = "1", module = "删除巡查记录")
    public ResultBean delPatrolrecord(Integer id){
        return patrolrecordService.delPatrolrecord(id);
    }

    //批量删除
    @GetMapping("delectPatrolrecordById")
    @ViLog(logType = "1", module = "批量删除")
    public ResultBean delectPatrolrecordById(int[] array){
        return patrolrecordService.delectPatrolrecordById(array);
    }

    //根据id查询该人的巡查记录
    @GetMapping("selectAllPatrolrecordById")
    @ViLog(logType = "1", module = "根据id查询该人的巡查记录")
    public ResultBean selectAllPatrolrecordById(Integer id){
        return patrolrecordService.selectAllPatrolrecordById(id);
    }

    //查询当天日常勤务各时间段 实到、应到人数
    @GetMapping("theDaySum")
    @ViLog(logType = "1", module = "查询当天日常勤务各时间段 实到、应到人数")
    public List<HashMap> theDaySum(String battalion)throws Exception{
        return patrolrecordService.theDaySum(battalion);
    }
    //统计各个岗位应到实到人
    @GetMapping("typeSum")
    @ViLog(logType = "1", module = "统计各个岗位应到实到人")
    public List<HashMap> typeSum(String battalion)throws Exception{
        return patrolrecordService.typeSum(battalion);
    }

    //查询当天各大队各中队日常勤务各时间段 实到、应到人数
    @GetMapping("countZD")
    @ViLog(logType = "1", module = "查询当天各大队各中队日常勤务各时间段 实到、应到人数")
    public List<HashMap> countZD(String battalion)throws Exception{
        return patrolrecordService.countZD(battalion);
    }

    //统计夜间各个岗位应到实到人
    @GetMapping("yXtypeSum")
    @ViLog(logType = "1", module = "统计夜间各个岗位应到实到人")
    public List<HashMap> yXtypeSum(String battalion)throws Exception{
        return patrolrecordService.yXtypeSum(battalion);
    }

    //按时间段统计日间各个岗位应到实到人
    @GetMapping("typeSumByTime")
    @ViLog(logType = "1", module = "按时间段统计日间各个岗位应到实到人")
    public List<HashMap> typeSumByTime(String battalion)throws Exception{
        return patrolrecordService.typeSumByTime(battalion);
    }

    //查询区域内在线人的点位
    @GetMapping("findNowByGps")
    @ViLog(logType = "1", module = "查询区域内在线人的点位")
    public List<HashMap> findNowByGps(double[] lon, double[] lat) {

        return patrolrecordService.findNowByGps(lon,lat);
    }

    //根据大队名称查询在人GPS /热力图
    @GetMapping("findNowStaffBySection")
    @ViLog(logType = "1", module = "根据大队名称查询在人GPS /热力图")
    public List<HashMap> findNowStaffBySection(String time,String battalion,Integer type){
        return patrolrecordService.findNowStaffBySection(time,battalion,type);
    }

    //根据大队名称统计在线、应到人数
    @GetMapping("findStaffSum")
    @ViLog(logType = "1", module = "根据大队名称统计在线、应到人数")
    public HashMap findStaffSum(String time,String battalion,Integer type){
        return patrolrecordService.findStaffSum(time,battalion,type);
    }

    //判断是否在圆内
    @GetMapping("findCircleByGps")
    @ViLog(logType = "1", module = "判断是否在圆内")
    public List<HashMap> findCircleByGps(double circleX, double circleY,double r){
        return patrolrecordService.findCircleByGps(circleX,circleY,r);
    }

    //根据个人id查询改人当天是否签到
    @GetMapping("selectinfoByid")
    @ViLog(logType = "1", module = "根据个人id查询改人当天是否签到")
    public ResultBean selectinfoByid(Integer id){
        return patrolrecordService.selectinfoByid(id);
    }

    //热力图
    @GetMapping("heatMap")
    @ViLog(logType = "1", module = "热力图")
    public List<HashMap> heatMap(){
        return patrolrecordService.heatMap();
    }

    //热力图应到部署警力
    @GetMapping("heatMapYD")
    @ViLog(logType = "1", module = "热力图应到部署警力")
    public List<HashMap> heatMapYD(){
        return patrolrecordService.heatMapYD();
    }


}
