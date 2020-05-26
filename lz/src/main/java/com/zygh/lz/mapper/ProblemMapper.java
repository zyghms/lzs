package com.zygh.lz.mapper;

import com.zygh.lz.admin.Problem;
import com.zygh.lz.admin.ProblemDemo;
import com.zygh.lz.admin.Section;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;

public interface ProblemMapper {
    int deleteByPrimaryKey(Integer problemId);

    int insert(Problem record);

    int insertSelective(Problem record);

    Problem selectByPrimaryKey(Integer problemId);

    int updateByPrimaryKeySelective(Problem record);

    int updateByPrimaryKey(Problem record);

    //查询跟巡查记录相关的问题
    List<Problem> selectProblemByRecord(Integer sysPatrolRecordId);

    //根据上报人查询问题
    List<Problem> selectProblemByStaffId(Integer staffId);

    //查询问题解决未解决个数
    List<Problem> selectProblemByRosove(String problemState);

    //查询所有问题状态
    List<String> selectProblemType();

    //查询问题表里所有问题关联的大队
    List<String> selectSectionType();

    //部门个数
    List<Section> selectSetionTotal();

    //问题个数
    List<Section> selectProblemTotal();

    //根据大队查询出巡查问题
    List<Problem> selectProblemBysectionName(String sectionName);

    List<Section> selectProblemBytype(String sectionName);

    List<Section> selectProblemTotaBysectionName(String sectionName);

    //根据道路类型查询问题
    List<Problem> selectAllByRoadType(String roadType);

    //问题模糊查询
    List<Object> selectDim(@Param("roadName") String roadName, @Param("staffName") String staffName, @Param("sectionName") String sectionName,
                           @Param("problemStrat") String problemStrat, @Param("staffHierarchy") String staffHierarchy,
                           @Param("beginTime") String beginTime, @Param("endTime") String endTime,
                           @Param("problemCheck") String problemCheck,@Param("problemDetail") String problemDetail);

    //根据人模糊查询问题
    List<Problem> selectDimStaff(@Param("SysStaffId") Integer SysStaffId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //根据条件模糊查询不同部门的已解决和未解决的问题数
    List<ProblemDemo> selectDimAll(@Param("startTime") String startTime,
                                   @Param("endTime") String endTime,
                                   @Param("problem_resove") String problem_resove,
                                   @Param("section_name") String section_name);

    //根据条件模糊查询不同部门发现的问题数
    List<ProblemDemo> selectDimAll1(@Param("startTime") String startTime,
                                    @Param("endTime") String endTime, @Param("section_name") String section_name);

    List<ProblemDemo> selectDimAllSy(@Param("problem_state") String problem_state, @Param("section_name") String section_name);

    //按条件查询没有核查的问题
    List<Problem> selectProblem(@Param("staffAcceptId") Integer staffAcceptId,
                                @Param("roadName") String roadName,
                                @Param("problemStrat") String problemStrat);

    //查询所有除去已完成以外的多有问题
    List<Problem> selectProblemByState();

    //查询最后一条问题
    Problem selectByProblemEnd();

    //问题按年按月统计统计
    List<Problem> selectProblemByCount(@Param("problemState") String problemState,
                                       @Param("sectionId") Integer sectionId,
                                       @Param("time") String time);

    //根据id查询问题
    List<Problem> selectProblemByid(Integer id);

    //问题详情
    List<Problem> selectProblemByNum(@Param("problemState") String problemState,
                                     @Param("sectionId") Integer sectionId,
                                     @Param("time") String time,
                                     @Param("sysStaffId") Integer sysStaffId,
                                     @Param("staffHierarchy") String staffHierarchy,
                                     @Param("staffName") String staffName);

    //查询所有不能解决的问题
    List<Object> selectProbleByStart(String problemState);

    //模糊查询
    List<Object> selectByProblemdetail(@Param("typeKey") String typeKey,
                                       @Param("problemStrat") String problemStrat,
                                       @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    //批量删除
    int deleteByProblemid(int[] array);

    List<Problem> selectProblemTotalfu(Integer id);
    List<Section> selectProblemTotal1();
}