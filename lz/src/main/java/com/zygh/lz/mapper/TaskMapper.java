package com.zygh.lz.mapper;

import com.zygh.lz.admin.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer sysTaskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer sysTaskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    //查询所有任务
    List<Task> selectAllTask();

    //关联问题查询展示任务列表
    List<Task> selectAllTP(Integer acceptStaffId);

    //我的任务
    List<Object> selectAllByAccept(@Param("acceptStaffId") Integer acceptStaffId, @Param("taskState") String taskState,
                                   @Param("taskTitle") String taskTitle,
                                   @Param("taskCreatetime") String taskCreatetime,
                                   @Param("taskFinishtime") String taskFinishtime);

    //查询最后一条任务
    Task selectEnd();

    //任务统计
    List<Task> selectTaskByCount(@Param("taskState") String taskState,
                                 @Param("sectionId") Integer sectionId,
                                 @Param("time") String time);

    List<Task> selectTaskByid(Integer id);

    //查详细查人员
    List<Task> selectTaskByNum(@Param("taskState") String taskState,
                               @Param("sectionId") Integer sectionId,
                               @Param("time") String time ,
                               @Param("sysStaffId") Integer sysStaffId,
                               @Param("staffHierarchy") String staffHierarchy,
                               @Param("staffName") String staffName);

    //批量删除
    int deleteTaskById(int[] array);

    //查询本大队的
    List<Task> selectTaskBySection(@Param("sectionId") Integer sectionId,@Param("taskTitle") String taskTitle,
                                   @Param("taskCreatetime") String taskCreatetime,
                                   @Param("taskFinishtime") String taskFinishtime);

    //查询所有任务细节为空的数据
    List<Task> selectTaskDescribe(@Param("sectionId") Integer sectionId,@Param("taskTitle") String taskTitle,
                                  @Param("taskCreatetime") String taskCreatetime,
                                  @Param("taskFinishtime") String taskFinishtime);

}