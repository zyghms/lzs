package com.zygh.lz.mapper;

import com.zygh.lz.admin.Patrolrecord;
import com.zygh.lz.admin.Staff;
import com.zygh.lz.admin.Xarea;
import com.zygh.lz.vo.ResultBean;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface StaffMapper {
    int deleteByPrimaryKey(Integer sysStaffId);

    int insert(Staff record);

    int insertSelective(Staff record);

    Staff selectByPrimaryKey(Integer sysStaffId);

    int updateByPrimaryKeySelective(Staff record);

    int updateByPrimaryKey(Staff record);

    //登录
    Staff selectByLogin(@Param("user") String user, @Param("password") String password);

    //校验
    Staff selectByName(String name);

    //根据id查询该人的部门、路长等级和姓名
    Staff selectInfoByid(Integer staffId);

    Staff selectByPrimaryKeyBig(String sysStaffId);

    //关联查询所有人员
    List<Staff> selectAllStaff();

    //查询所有人员
    List<Staff> selectAllStaffPeople();

    int selectBySectionId(int id);

    List<Object> selectStaffByDim(String staffSex, String staffPost, String staffName, String staffPid);

    List<Staff> selectdefault(String probleDetail);

    //查出每个大队下的人员
    List<Staff> selectAllBySF(Integer sysSectionId);

    List<Staff> selectStaffByName(@Param("Name") String Name, @Param("staffHierarchy") String staffHierarchy);

    //返回各大队指挥室
    Staff selectStaffBySectionName(Integer staffid);

    //根据警号查询人员信息
    Staff selectStaffByNum(String staffNum);

    //根据条件查询该中队的所有民警
    List<Staff> selectStaffByXarea(@Param("battalion") String battalion,
                                   @Param("detachment") String detachment,
                                   @Param("station") String station,
                                   @Param("leader") String leader);

    //查询区域里对应的警员
    List<Staff> selectStaffInfo();

    //根据gpsid查询区域民警
    List<Staff> selectStaffByGpsid(Integer id);

    //查询在岗人
    List<Staff> selectStaffByzg(@Param("sectionId") Integer sectionId,
                                @Param("sectionPid") Integer sectionPid,
                                @Param("sectionName") String sectionName);


    List<Staff> selectStaffByxarea(Integer id);

    //所有应上岗人
    List<Staff> selectStaffYdByAll(@Param("changeShifts") String changeShifts,
                                   @Param("SectionId") Integer SectionId,
                                   @Param("SectionZid") Integer SectionZid);

    List<Staff> selectStaffSdByAll(String changeShifts);

    //查询所有在线民警，并把该民警岗位带出
    List<Staff> selectpoliceZx();

    //查询直系领导
    Staff selectStaffBypid(Integer id);

    //查询昨日总警力
    int selecttotalforces();

    //按大队查询昨日总警力
    List<HashMap> selecttotalforceszr();

    //根据岗位查询在岗人
    List<Staff> selectzaBystation(@Param("station") String station,
                                  @Param("conment") String conment,
                                  @Param("grid") String grid);

    //查询其他在线民警
    List<Staff> selectStaffByqita();


    /**
     * 从下查询人数，首页右侧信息展示
     *
     * @param station
     * @param battalion
     * @return
     */
    //统计固定岗/高峰岗实到
    List<Staff> countGdorGfSDsum(@Param("station") String station, @Param("battalion") String battalion);
    List<Staff> countGdorGfSDsumtj();

    //统计夜巡实到
    List<Staff> countYxSDsum(String battalion);

    //统计铁骑实到
    List<Staff> countTqSDsum(String battalion);

    //统计网格实到
    List<Staff> countWgSDsum(String battalion);

    //日常实到
    List<Staff> countRcSDsum(String battalion);

    //重点岗实到
    List<Staff> countZdSDsum(String battalion);

    //其他岗实到
    List<Staff> countQtSDsum(String battalion);

    List<Staff> countZDRcSDsum(@Param("battalion") String battalion, @Param("detachment") String detachment, @Param("station") String station);

    List<Staff> countZDYxSDsum(@Param("battalion") String battalion, @Param("detachment") String detachment);

    //统计固定岗/高峰岗实到
    public List<Staff> countGsorKsSDsum(@Param("station") String station, @Param("battalion") String battalion, @Param("conment") String conmen);


    //根据岗位查询各大队在线民警
    List<HashMap> selectcountBysection(Xarea xarea);


    //根据岗位查询各大队下各中队在线民警
    List<HashMap> selectcountBydetachment(Xarea xarea);

    //根据岗位查询各中队在线民警详情
    List<HashMap> selectAllBysection(Xarea xarea);

    //民警辅警各大队分布详情
    List<HashMap> selecttotalforcfb(String stafftype);

    /**
     * 对接市局接口  人员信息列表
     * @return
     */
    ResultBean selectStaffByInfo();
}