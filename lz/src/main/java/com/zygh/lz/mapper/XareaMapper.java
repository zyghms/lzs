package com.zygh.lz.mapper;

import com.zygh.lz.admin.Staff;
import com.zygh.lz.admin.Xarea;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.swing.event.ListDataEvent;
import java.util.HashMap;
import java.util.List;

public interface XareaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Xarea record);

    int insertSelective(Xarea record);

    Xarea selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Xarea record);

    int updateByPrimaryKeyWithBLOBs(Xarea record);

    int updateByPrimaryKey(Xarea record);

    //根据大队，中队，岗位，中队领导
    List<Xarea> selectXareabycondition(@Param("battalion") String battalion,
                                       @Param("detachment") String detachment,
                                       @Param("station") String station,
                                       @Param("leader") String leader,
                                       @Param("grid") String grid,
                                       @Param("type") String type);

    //根据名字模糊查询
    List<Xarea> selectXareaByName(@Param("Name") String Name,
                                  @Param("battalion") String battalion,@Param("detachment") String detachment);

    //查询所有区域
    List<Xarea> selectXareaAll();

    List<Xarea> selectXareaZgByStaffId(@Param("id") Integer id, @Param("station") String station);

    List<Xarea> selectXareaByid(Integer id);

    List<HashMap> countYDSum(@Param("station") String station, @Param("battalion") String battalion, @Param("detachment") String detachment);

    List<HashMap> countYxSum(@Param("conment") String conment,
                             @Param("battalion") String battalion,
                             @Param("detachment") String detachment);

    List<HashMap> countRcYDsumC2(String battalion);

    List<HashMap> countRcYDsum(String battalion);

    List<Integer> countWgSum(String battalion);

    List<Integer> countZdYDSum(String battalion);

    List<Integer> countQtYDSum(@Param("battalion") String battalion, @Param("conment") String conment);

    List<Integer> countTqZSum(@Param("battalion") String battalion, @Param("detachment") String detachment);

    int countggZSum(@Param("station") String station, @Param("battalion") String battalion, @Param("detachment") String detachment);

    List<String> countWgZSum(@Param("battalion") String battalion, @Param("detachment") String detachment);

    int countZdZSum(@Param("battalion") String battalion, @Param("detachment") String detachment);

    int countQtZSum(@Param("battalion") String battalion,
                    @Param("conment") String conment,
                    @Param("detachment") String detachment);

    List<Integer> countZDRc(@Param("battalion") String battalion, @Param("detachment") String detachment, @Param("station") String station);

    List<Integer> countZDRcC2(@Param("battalion") String battalion, @Param("detachment") String detachment);

    List<HashMap> countZDYxorTq(@Param("conment") String conment, @Param("battalion") String battalion, @Param("detachment") String detachment);

    List<String> findDd();

    List<String> findZd(@Param("battalion") String battaliont, @Param("conment") String conmen);

    List<Integer> countGsOrKsZ(@Param("station") String station, @Param("battalion") String battalion, @Param("detachment") String detachment);
    List<Integer> countKsZ(@Param("station") String station, @Param("battalion") String battalion, @Param("detachment") String detachment);

    List<Integer> countTQZ(String battalion);

    List<Integer> countGsOrKsYDSum(@Param("station") String station, @Param("battalion") String battalion, @Param("conment") String conmen, @Param("changeShifts") String changeShifts);

    //查询夜间快速大队警组，警员
    List<Xarea> selectks();

    //夜巡组数
    List<Integer> countYxZ(@Param("battalion") String battalion, @Param("detachment") String detachment);

    //夜巡应到
    List<Integer> countYxYDsum(String battalion);

    //查询其他
    List<Xarea> selectqt(String conment);


    /**
     * 日间警力部署
     */
    //日间固定岗部署人员按大队细分
    List<HashMap> selectfixationRJ(String station);
    //细分到中队
    List<HashMap> selectfixationzdRJ(@Param("station") String station,
                                     @Param("battalion") String battalion);

    //日间重点单位岗
    List<HashMap> selectemphasisRJ();
    //日间重点单位岗人员详情
    List<HashMap> selectemphasisPope(@Param("battalion") String battalion,
                                     @Param("detachment") String detachment);
    //细分到中队
    List<HashMap> selectemphasisZd();

    //日间铁骑
    List<HashMap> selectcavalryRJ();
    //细分到中队
    List<HashMap> selectcavalryzdRJ();

    //日间网格警组
    List<HashMap> selectgriddingRJ();
    //日间网格警力详情
    List<HashMap> selectgriddingPope(@Param("battalion") String battalion,
                                     @Param("detachment") String detachment);
    //日间网格警力详情细分到中队
    List<HashMap> selectgriddingZD();

    //日间高速/快速
    List<HashMap> selectexpresswayRJ(String station);
    //日间高速岗组数
    List<HashMap> countGsOrKsZSum(@Param("station") String station,
                                  @Param("battalion") String battalion,
                                  @Param("detachment") String detachment);
    //细分到中队
    List<HashMap> selectexpresswayZD();
    List<HashMap> selectexpresswayPope(@Param("station") String station,
                                       @Param("battalion") String battalion,
                                       @Param("conment")String conment,
                                       @Param("detachment")String detachment);

    //日间其他警力部署
    List<HashMap> selectqtRJ(String stafftype);
    //日间其他警力部署细分到中队
    List<HashMap> selectqtZD();

    //日间其他警员详情
    List<HashMap> selectqtPope(@Param("battalion") String battalion,
                               @Param("detachment") String detachment,
                               @Param("stafftype") String stafftype);

    //在线人警力部署
    List<HashMap> selectInformant();
    //在线人警力部署中队
    List<HashMap> selectInformantZD();
    //在线人详情
    List<HashMap> selectInformantPope(@Param("battalion") String battalion,
                                      @Param("detachment") String detachment);

    //夜间夜巡警力部署
    List<HashMap> selectNightTour();
    //夜间夜巡警力部署细分到中队
    List<HashMap> selectNightTourZD();

    //夜间夜巡警力部署详情
    List<HashMap> selectNightTourPope(@Param("battalion") String battalion,
                                      @Param("detachment") String detachment);

    //夜间快速警力部署
    List<HashMap> selectcelerity(String station);

    //夜间快速警力部署详情
    List<HashMap> selectcelerityPope(@Param("station") String station,@Param("battalion") String battalion,
                                     @Param("detachment") String detachment);

    //夜间快速警力部署细分到中队
    List<HashMap> selectcelerityPopeZD();

    //夜间高速警力部署
    List<HashMap> selectceleritygs(String station);

    //夜间高速警力部署详情
    List<HashMap> selectceleritygsPope(@Param("station") String station,@Param("battalion") String battalion,
                                       @Param("detachment") String detachment);

    //夜间高速警力部署细分到中队
    List<HashMap> selectceleritygsPopeZD();

    //夜间其他
    List<HashMap> selectqita();

    //夜间其他详情
    List<HashMap> selectqitaPope(@Param("battalion") String battalion,
                                 @Param("detachment") String detachment,
                                 @Param("stafftype") String stafftype);

    //九主六块十六示范区
    List<Xarea> selectDemonstrationPlot(String station);

    //根据区域名字模糊匹配部署警力
    List<HashMap> selctStrength(@Param("name") String name,@Param("battalion") String battalion);

    //查询区域最后一条
    Xarea selectXareaEnd();

    //高峰岗部署警力
    List<Xarea> selectXareapolic(@Param("station") String station,
                                 @Param("battalion") String battalion,@Param("detachment") String detachment);

    /*日常勤务*/
    Integer countGFYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countGDYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countZDYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countTQYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countWGYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countGSYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countKSYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    Integer countYXYD(@Param("battalion") String battalion, @Param("detachment") String detachment);
    List<HashMap> countJDYD(@Param("battalion") String battalion, @Param("detachment") String detachment);

    //统计辅警部署人
    List<HashMap> selectAssistPolice(@Param("station") String station,
                                     @Param("battalion") String battalion,
                                     @Param("detachment") String detachment,
                                     @Param("stafftype") String stafftype,
                                     @Param("gridding") String gridding,
                                     @Param("conment") String conment);

    /**
     * 对接市局接口
     * 根据不同条件查询标注信息
     */
    List<Xarea> selectXareaByInfo(Xarea xarea);

    List<HashMap> selectPoliceNumber(Xarea xarea);

    //根据区域名字模糊匹配部署警力
    List<HashMap> selctStrengthById(Xarea xarea);

    //任务组信息
    List<Xarea> selectTaskSetInfo();
    //任务信息
    List<Xarea> selectTaskInfo();
}