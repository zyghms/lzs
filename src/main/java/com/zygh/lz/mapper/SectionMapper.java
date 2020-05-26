package com.zygh.lz.mapper;

import com.zygh.lz.admin.Section;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionMapper {
    int deleteByPrimaryKey(Integer sysSectionId);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(Integer sysSectionId);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);

    //根据姓名，性别，职位，部门，领导模糊查询所有部门人员
    List<Section> selectAllBySection(@Param("sex") String sex,
                                     @Param("staffPost") String staffPost,
                                     @Param("staffName") String staffName,
                                     @Param("staffPid") String staffPid,
                                     @Param("sectionName") String sectionName);

    //查询所有部门
    List<Section> selectAllSection();

    //部门删除是判断
    int selectByPid(int Pid);
    //部门人员信息查询那里的模糊查询
    List<Section> selectSectionByCt(@Param("sex") String sex,
                                    @Param("staffHierarchy") String staffHierarchy,
                                    @Param("sectionName") String sectionName);

    //根据部门id查询所有部门人员
    List<Object> selectBySectionId(@Param("SectionId") Integer SectionId,
                                   @Param("staffHierarchy") String staffHierarchy);

    //根据大队id查询大队下面的中队
    List<Section> selectDetachmentByid(Integer id);
}