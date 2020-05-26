package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Section;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.SectionMapper;
import com.zygh.lz.mapper.StaffMapper;
import com.zygh.lz.service.sectionService;
import com.zygh.lz.service.staffService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class sectionServiceImpl implements sectionService {
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private StaffMapper staffMapper;

    /**
     * 新增人员
     * @param section
     * @return
     */
    @Override
    public ResultBean addSection(Section section) {
        return ResultUtil.execOp(sectionMapper.insertSelective(section),"添加");
    }

    /**
     * 根据id修改部门
     * @param section
     * @return
     */
    @Override
    public ResultBean modefiSectionById(Section section) {
        return ResultUtil.execOp(sectionMapper.updateByPrimaryKeySelective(section),"修改");
    }

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    @Override
    public ResultBean delSectionById(Integer id) {
        int num = sectionMapper.selectByPid(id);
        int count = staffMapper.selectBySectionId(id);
        if (num > 0 || count > 0) {
            return ResultUtil.setError(SystemCon.RERROR1,"该部门下有子部门，不能删除！",null);
        }
        return ResultUtil.execOp(sectionMapper.deleteByPrimaryKey(id),"删除");
    }

    /**
     * 根据性别,路长，部门查询
     * @return
     */
    @Override
    public ResultBean selectAllBySection(String sex,String staffPost,String staffName,String staffPid,String sectionName) {
        List<Section> sections = sectionMapper.selectAllBySection(sex,staffPost,staffName,staffPid,sectionName);
        if(sections.size() > 0){
            return ResultUtil.setOK("success",sections);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 按层级推送
     * @return
     */
    @Override
    public ResultBean selectAllSection() {
        List<Section> sections = sectionMapper.selectAllSection();
        List<Section> sectionList = new ArrayList<Section>();
        for (int i = 0; i < sections.size(); i++) {
            // 一级菜单没有parentId
            if (sections.get(i).getSectionPid() == 0) {
                sectionList.add(sections.get(i));
            }
        }
        Iterator iterList = sectionList.iterator();
        while (iterList.hasNext()) {
            Section treemenu = (Section) iterList.next();
            treemenu.setSectionList(getChild(treemenu.getSysSectionId(), sections));
        }

       return ResultUtil.setOK("success",sectionList);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public ResultBean selectAllSectionInfo() {
        List<Section> sections = sectionMapper.selectAllSection();
        if(sections.size() > 0){
            return ResultUtil.setOK("success",sections);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 部门人员信息查询那里的模糊查询
     * @param sex
     * @param staffHierarchy
     * @param sectionName
     * @return
     */
    @Override
    public ResultBean selectSectionByCt(String sex, String staffHierarchy, String sectionName) {
        List<Section> sections = sectionMapper.selectSectionByCt(sex, staffHierarchy, sectionName);
        if(sections.size() > 0){
            return ResultUtil.setOK("success",sections);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     *
     * @param sectionId
     * @return
     */
    @Override
    public ResultBean selectBySectionId(Integer sectionId,String staffHierarchy) {
        List<Object> list = sectionMapper.selectBySectionId(sectionId,staffHierarchy);
        if(list.size() > 0){
            return ResultUtil.setOK("success",list);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }


    /**
     * 根据大队id查询大队下面的中队
     * @param id
     * @return
     */
    @Override
    public ResultBean selectDetachmentByid(Integer id) {
        List<Section> sections = sectionMapper.selectDetachmentByid(id);
        if(sections.size()>0){
            return ResultUtil.setOK("success",sections);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }


    private List<Section> getChild(int id, List<Section> sections) {//int id  id 是指当前菜单id，rootMenu是指要查找的列表
        List<Section> childList = new ArrayList<>();
        Iterator iterList = sections.iterator();
        while (iterList.hasNext()) {
            Section treemenu = (Section) iterList.next();
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (treemenu.getSectionPid() != 0) {
                if (treemenu.getSectionPid() == id) {
                    childList.add(treemenu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        /*for (Treemenu treemenu : childList) {*/
        Iterator iterList2 = childList.iterator();
        while (iterList2.hasNext()) {
            Section treemenu = (Section) iterList2.next();
            treemenu.setSectionList(getChild(treemenu.getSysSectionId(), sections));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
