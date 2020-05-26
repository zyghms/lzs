package com.zygh.lz.mapper;

import com.zygh.lz.admin.Patrolrecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PatrolrecordMapper {
    int deleteByPrimaryKey(Integer sysPatrolRecordId);

    int insert(Patrolrecord record);

    int insertSelective(Patrolrecord record);

    Patrolrecord selectByPrimaryKey(Integer sysPatrolRecordId);

    int updateByPrimaryKeySelective(Patrolrecord record);

    int updateByPrimaryKeySelectives(Patrolrecord record);

    int updateByPrimaryKey(Patrolrecord record);

    //根据道路名称、部门查询所有巡查信息
    List<Patrolrecord> selectByRoadtype(@Param("sysSectionId") Integer sysSectionId,
                                        @Param("beginTime") String beginTime,
                                        @Param("endTime") String endTime,
                                        @Param("sectionPid") Integer sectionPid);

    //根据层级显示相对信息（一级路长）
    List<Patrolrecord> selectByhierarchy(String staffHierarchy, Integer sysSectionId, String roadType);

    //根据层级显示相对信息（二级路长）
    List<Patrolrecord> selectBysysStaffId(@Param("staffid") Integer staffid, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据sysStaffId修改已打分
    int updatePatrolrecordBysysStaffId(Patrolrecord patrolrecord);

    //查询未打分的
    List<Patrolrecord> selectBypatrolstate(Integer staffid, String roadType);

    //添加统计巡查
    List<Map> countPatrolrecord(String startTime, String endTime) throws Exception;

    //查询所有巡查记录
    List<Patrolrecord> selectAllPatrolrecord();

    //查询巡查记录的所有视频
    List<Patrolrecord> selectVideo();

    //根据开始时间，结束时间、道路名称查询巡查记录
    List<Object> selectDim(@Param("roadName") String roadName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据时间查询个人的巡查记录
    List<Map<String, Object>> selectByStaff(@Param("SysStaffId") Integer SysStaffId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //查询各个大队的巡查记录个数
    List<Patrolrecord> selectRecordNum(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * @param sectionName 部门
     * @param roadType    道路类型
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    int countPatrolrecord(@Param("sectionName") String sectionName, @Param("roadType") String roadType, @Param("startTime") Date startTime,
                          @Param("endTime") Date endTime);

    //登录用户负责道路列表
    List<Object> selectByStaffId(Integer StaffId);

    List<Object> selectBytwostaffId(Integer twostaffId);

    Patrolrecord selectBylast();

    //批量删除
    int delectPatrolrecordById(int[] array);

    //根据id查询该人的巡查记录
    List<Patrolrecord> selectAllPatrolrecordById(Integer id);

    //根据人员id查询在线区域在线人数
    Patrolrecord selectPatrolrecordByStaffId(Integer staffid);

    //查询巡查轨迹
    Patrolrecord selectrecordByid(Integer id);

    //统计固定岗/高峰岗实到
    List<Integer> countGdorGfSDsum(@Param("station") String station, @Param("battalion") String battalion);

    //统计夜巡实到
    List<Integer> countYxSDsum(String battalion);

    //统计铁骑实到
    List<Integer> countTqSDsum(String battalion);

    //统计网格实到
    List<Integer> countWgSDsum(String battalion);

    //日常实到
    List<Integer> countRcSDsum(String battalion);

    //重点岗实到
    List<Integer> countZdSDsum(String battalion);

    //其他岗实到
    List<Integer> countQtSDsum(String battalion);

    List<Integer> countZDRcSDsum(@Param("battalion") String battalion,
                                 @Param("detachment") String detachment,
                                 @Param("station") String station,
                                 @Param("stafftype") String stafftype);

    List<Integer> countZDYxSDsum(@Param("battalion") String battalion, @Param("detachment") String detachment,@Param("stafftype") String stafftype);

    //统计固定岗/高峰岗实到
    List<Integer> countGsorKsSDsum(@Param("station") String station, @Param("battalion") String battalion, @Param("conment") String conmen);


    //查询在线人ID
    List<Integer> findNowId();

    //根据ID查询Gps点位
    HashMap<String, Object> findNowGps(Integer id);

    //高峰实到在线人ID
    List<Integer> findGFGPS(@Param("time") String time, @Param("station") String station, @Param("battalion") String battalion);

    //日常勤务实到在线人ID
    List<Integer> findRcGPS(@Param("time") String time, @Param("battalion") String battalion);

    //夜巡实到在线人ID
    List<Integer> findYXGPS(@Param("time") String time, @Param("battalion") String battalion);

    //根据ID查询Gps点位
    HashMap<String, Object> findStaffGps(@Param("time") String time, @Param("id") Integer id);

    //根据ID查询Gps点位
    List<HashMap> findSectionId(String sectionName);

    //根据个人id查询改人当天是否签到
    HashMap selectinfoByid(Integer id);

    //查询网格
    List<HashMap> findByType();

    //应部署GPS
    List<HashMap> findTypeGps(String type);






}