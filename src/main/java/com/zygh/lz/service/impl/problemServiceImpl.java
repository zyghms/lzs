package com.zygh.lz.service.impl;

import com.zygh.lz.admin.*;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.*;
import com.zygh.lz.service.problemService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zygh.lz.util.GPSTransformMars;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class problemServiceImpl implements problemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private messageMapper messageMapper;

    /**
     * 新增问题
     *
     * @param problem
     * @return
     */
    @Override
    public ResultBean addProblem(Problem problem) {
        System.out.println("=============================新增问题===========");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String problemNum = format.format(new Date());
        problem.setProblemNum(problemNum);
        //坐标纠偏转换
        GPSTransformMars gpsTransformMars = new GPSTransformMars();
        if (problem.getProblemGpsX() != null) {
            double x = Double.valueOf(problem.getProblemGpsX());
            double y = Double.valueOf(problem.getProblemGpsY());
            System.out.println("转换前x：" + x);
            System.out.println("转换前y：" + y);
            double[] m = gpsTransformMars.transLatLng(x, y);
            problem.setProblemGpsX(String.valueOf(m[0]));
            problem.setProblemGpsY(String.valueOf(m[1]));

        }

        int i = problemMapper.insertSelective(problem);
        Problem problem1 = problemMapper.selectByProblemEnd();
        if (i > 0 && problem1 != null) {
            message message = new message();
            message.setMessageType("1");
            message.setMessagePid(problem1.getProblemId());
            message.setCreateTime(new Date());
            message.setMessageName("你收到了一条道路问题消息，请执行！");
            message.setLaunchId(problem.getSysStaffId());
            message.setAcceptId(problem.getAcceptId());
            return ResultUtil.execOp(messageMapper.insertSelective(message), "新增问题消息");
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);

    }

    /**
     * 修改
     *
     * @param problem
     * @return
     */
    @Override
    public ResultBean updateProblem(Problem problem) {
        String problemGpsX = problem.getProblemGpsX();
        String problemGpsY = problem.getProblemGpsY();
        if (problemGpsX != null && problemGpsY != null) {
            /*坐标纠偏转换*/
            GPSTransformMars gpsTransformMars = new GPSTransformMars();
            double x = Double.valueOf(problem.getProblemGpsX());
            double y = Double.valueOf(problem.getProblemGpsY());
            double[] m = gpsTransformMars.transLatLng(x, y);
            problem.setProblemGpsX(String.valueOf(m[0]));
            problem.setProblemGpsY(String.valueOf(m[1]));
        }

        return ResultUtil.execOp(problemMapper.updateByPrimaryKeySelective(problem), "修改");
    }

    /**
     * 根据主键id删除
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean delProblem(Integer id) {
        return ResultUtil.execOp(problemMapper.deleteByPrimaryKey(id), "删除");
    }

    /**
     * 根据人查询全部问题  (所有问题)
     *
     * @param staffReportId
     * @return
     */
    @Override
    public ResultBean selectProblemByStaffId(Integer staffReportId) {
        List<Problem> problems = problemMapper.selectProblemByStaffId(staffReportId);
        if (problems.size() > 0) {
            return ResultUtil.setOK("success", problems);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 查询问题解决未解决个数
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean selectProblemByRosove(Integer id) {
        Staff staff = staffMapper.selectInfoByid(id);
        String sectionName = staff.getSectionName();
        String staffHierarchy1 = staff.getStaffHierarchy();
        if (staff != null) {

            if (staffHierarchy1.equals("分管路长") || staffHierarchy1.equals("支队科研所") || staffHierarchy1.equals("总路长")) {
                //查询所有问题
                List<Problem> problemList = problemMapper.selectProblemByStaffId(null);
                int problemnumn = problemList.size();
                List problemtype = problemMapper.selectProblemType();
                List sectiontype = problemMapper.selectSectionType();
                List<Section> sectiontotal = problemMapper.selectSetionTotal();
                List<Section> problemtotal = problemMapper.selectProblemTotal();
                List<Problem> problemList1 = problemMapper.selectProblemByRosove("不能解决");
                List<Problem> problemList2 = problemMapper.selectProblemByRosove("已解决");
                List<Problem> problemList3 = problemMapper.selectProblemByRosove("走任务解决");
                List<Problem> problemList4 = problemMapper.selectProblemByRosove("协调解决");
                int problemnumn1 = problemList1.size();
                int problemnumn2 = problemList2.size();
                int problemnumn3 = problemList3.size();
                int problemnumn4 = problemList4.size();
                LinkedHashMap<String, Object> json = new LinkedHashMap<>();
                json.put("大队", sectiontype);
                json.put("问题", problemtype);
                json.put("统计", sectiontotal);
                json.put("类型", problemtotal);
                json.put("problemnumn", problemnumn);
                json.put("不能解决个数", problemnumn1);
                json.put("已解决个数", problemnumn2);
                json.put("走任务解决个数", problemnumn3);
                json.put("协调解决个数", problemnumn4);
                json.put("problemList", problemList);
                return ResultUtil.setOK("success", json);
            } else {
                List<Problem> problems = problemMapper.selectProblemBysectionName(sectionName);
                LinkedHashMap<String, Object> json1 = new LinkedHashMap<>();
                List<Problem> listy = new ArrayList<>();
                List<Problem> listw = new ArrayList<>();
                List<Problem> listz = new ArrayList<>();
                List<Problem> listb = new ArrayList<>();

                Problem problem = null;
                for (int i = 0; i < problems.size(); i++) {
                    problem = new Problem();
                    problem = problems.get(i);
                    if (problem.getProblemState().equals("已解决")) {
                        listy.add(problems.get(i));

                    }
                    if (problem.getProblemState().equals("走任务解决")) {
                        listz.add(problems.get(i));
                    }
                    if (problem.getProblemState().equals("协调解决")) {
                        listw.add(problems.get(i));
                    }
                    if (problem.getProblemState().equals("不能解决")) {
                        listb.add(problems.get(i));
                    }
                }
                List problemtype = problemMapper.selectProblemType();
                //统计
                List<Section> stringList = problemMapper.selectProblemBytype(sectionName);
                //类型
                List<Section> problems1 = problemMapper.selectProblemTotaBysectionName(sectionName);

                json1.put("大队", sectionName);
                json1.put("问题", problemtype);
                json1.put("统计", stringList);
                json1.put("类型", problems1);
                json1.put("problemnumn", problems.size());
                json1.put("已解决个数", listy.size());
                json1.put("协调解决个数", listw.size());
                json1.put("走任务解决个数", listz.size());
                json1.put("不能解决个数", listb.size());

                json1.put("problemList", problems);
                return ResultUtil.setOK("success", json1);

            }

        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);

    }


    /**
     * 根据道路名字查询问题
     *
     * @param sysStaffId
     * @param roadType
     * @return
     */
    @Override
    public ResultBean selectAllByRoadType(Integer sysStaffId, String roadType) {
        //登陆人信息
        Staff staff = staffMapper.selectByPrimaryKey(sysStaffId);
        String Hierarchy = staff.getStaffHierarchy();
        String sectionName = staff.getSectionName();

        List<Problem> problemS = new ArrayList<>();
        //根据类型查询问题巡查记录
        problemS = this.problemMapper.selectAllByRoadType(roadType);
        List<Problem> problemList = new ArrayList<Problem>();

        if (Hierarchy.equals("二级路长") || Hierarchy.equals("一级路长")) {
            for (Problem problems : problemS) {
                if ((problems.getStaff().getSysStaffId()).equals(sysStaffId)) {
                    problemList.add(problems);
                }
            }
            return ResultUtil.setOK("success", problemList);
        } else if (Hierarchy.equals("总路长") || Hierarchy.equals("分管路长")) {
            for (Problem problems : problemS) {
                if ((problems.getStaff().getSectionName()).equals(sectionName)) {
                    problemList.add(problems);
                }
            }
            return ResultUtil.setOK("success", problemList);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }


    /**
     * 问题模糊查询
     *
     * @param roadType
     * @param staffName
     * @param sectionName
     * @param problemStrat
     * @param staffHierarchy
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public ResultBean selectDims(HttpServletRequest request, Integer sysStaffId, String roadType, String staffName, String sectionName, String problemStrat, String staffHierarchy, String beginTime, String endTime, String problemCheck, String problemDetail) {
        Staff staff = staffMapper.selectByPrimaryKey(sysStaffId);
        List<Object> problemList = null;
        //提取个人信息中得所属大队
        Section section = sectionMapper.selectByPrimaryKey(staff.getSysSectionId());
        sectionName = section.getSectionName();
        String Hierarchy = staff.getStaffHierarchy();
        if (Hierarchy.equals("二级路长") || Hierarchy.equals("一级路长")) {
            staffName = staff.getStaffName();
            problemList = this.problemMapper.selectDim(roadType, staffName, sectionName, problemStrat, staffHierarchy, beginTime, endTime, problemCheck, problemDetail);
            if (problemList.size() > 0 || problemList.size() == 0) {
                return ResultUtil.setOK("success", problemList);
            }
        } else if (Hierarchy.equals("总路长") || Hierarchy.equals("分管路长")) {
            staffName = null;
            problemList = this.problemMapper.selectDim(roadType, staffName, sectionName, problemStrat, staffHierarchy, beginTime, endTime, problemCheck, problemDetail);
            if (problemList.size() > 0 || problemList.size() == 0) {
                return ResultUtil.setOK("success", problemList);
            }
        }else if (staff.getStaffNum().equals("2245") || staff.getStaffNum().equals("007828")) {
            problemList = this.problemMapper.selectDim(roadType, staffName, sectionName, problemStrat, staffHierarchy, beginTime, endTime, problemCheck, problemDetail);
            return ResultUtil.setOK("success", problemList);
        } else {
            staffName = null;
            sectionName = null;
            problemList = this.problemMapper.selectDim(roadType, staffName, sectionName, problemStrat, staffHierarchy, beginTime, endTime, problemCheck, problemDetail);
            if (problemList.size() > 0 || problemList.size() == 0) {
                return ResultUtil.setOK("success", problemList);
            }
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);

    }

    /**
     * 根据人查询问题
     *
     * @param SysStaffId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public ResultBean selectDimStaff(Integer SysStaffId, String beginTime, String endTime) {
        List<Problem> problems = problemMapper.selectDimStaff(SysStaffId, beginTime, endTime);
        if (problems.size() > 0) {
            return ResultUtil.setOK("success", problems);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 领导管理的人所查询问题的模糊查询
     *
     * @param SysStaffId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public ResultBean selectDimStaffOther(Integer SysStaffId, String beginTime, String endTime) {
        List<Staff> staffList = staffMapper.selectAllStaff();
        List<Staff> staffList1 = getChild(SysStaffId, staffList);
        List problemList = new ArrayList();
        for (int i = 0; i < staffList1.size(); i++) {
            List<Problem> problemList1 = this.problemMapper.selectDimStaff(staffList1.get(i).getSysStaffId(), beginTime, endTime);
            problemList.add(problemList1);
        }

        return ResultUtil.setOK("success", problemList);
    }

    /**
     * 巡查记录个数统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public ResultBean selectDimAll(String startTime, String endTime) {
        List<ProblemDemo> problemDemoList11 = problemMapper.selectDimAll(startTime, endTime, "未解决", "一大队");
        List<ProblemDemo> problemDemoList12 = problemMapper.selectDimAll(startTime, endTime, "已完成", "一大队");
        List<ProblemDemo> problemDemoList13 = problemMapper.selectDimAll1(startTime, endTime, "一大队");
        Map<String, Object> map1 = new HashMap<String, Object>();
        JSONArray json11 = JSONArray.fromObject(problemDemoList11);
        JSONArray json12 = JSONArray.fromObject(problemDemoList12);
        JSONArray json13 = JSONArray.fromObject(problemDemoList13);
        map1.put("sectionName", "一大队");
        map1.put("notresolve", json11);
        map1.put("resolve", json12);
        map1.put("sum", json13);
        List<ProblemDemo> problemDemoList21 = problemMapper.selectDimAll(startTime, endTime, "未解决", "二大队");
        List<ProblemDemo> problemDemoList22 = problemMapper.selectDimAll(startTime, endTime, "已完成", "二大队");
        List<ProblemDemo> problemDemoList23 = problemMapper.selectDimAll1(startTime, endTime, "二大队");
        Map<String, Object> map2 = new HashMap<String, Object>();
        JSONArray json21 = JSONArray.fromObject(problemDemoList21);
        JSONArray json22 = JSONArray.fromObject(problemDemoList22);
        JSONArray json23 = JSONArray.fromObject(problemDemoList23);
        map2.put("sectionName", "二大队");
        map2.put("notresolve", json21);
        map2.put("resolve", json22);
        map2.put("sum", json23);
        List<ProblemDemo> problemDemoList31 = problemMapper.selectDimAll(startTime, endTime, "未解决", "三大队");
        List<ProblemDemo> problemDemoList32 = problemMapper.selectDimAll(startTime, endTime, "已完成", "三大队");
        List<ProblemDemo> problemDemoList33 = problemMapper.selectDimAll1(startTime, endTime, "三大队");
        Map<String, Object> map3 = new HashMap<String, Object>();
        JSONArray json31 = JSONArray.fromObject(problemDemoList31);
        JSONArray json32 = JSONArray.fromObject(problemDemoList32);
        JSONArray json33 = JSONArray.fromObject(problemDemoList33);
        map3.put("sectionName", "三大队");
        map3.put("notresolve", json31);
        map3.put("resolve", json32);
        map3.put("sum", json33);
        List<ProblemDemo> problemDemoList41 = problemMapper.selectDimAll(startTime, endTime, "未解决", "四大队");
        List<ProblemDemo> problemDemoList42 = problemMapper.selectDimAll(startTime, endTime, "已完成", "四大队");
        List<ProblemDemo> problemDemoList43 = problemMapper.selectDimAll1(startTime, endTime, "四大队");
        Map<String, Object> map4 = new HashMap<String, Object>();
        JSONArray json41 = JSONArray.fromObject(problemDemoList41);
        JSONArray json42 = JSONArray.fromObject(problemDemoList42);
        JSONArray json43 = JSONArray.fromObject(problemDemoList43);
        map4.put("sectionName", "四大队");
        map4.put("notresolve", json41);
        map4.put("resolve", json42);
        map4.put("sum", json43);
        List<ProblemDemo> problemDemoList51 = problemMapper.selectDimAll(startTime, endTime, "未解决", "五大队");
        List<ProblemDemo> problemDemoList52 = problemMapper.selectDimAll(startTime, endTime, "已完成", "五大队");
        List<ProblemDemo> problemDemoList53 = problemMapper.selectDimAll1(startTime, endTime, "五大队");
        Map<String, Object> map5 = new HashMap<String, Object>();
        JSONArray json51 = JSONArray.fromObject(problemDemoList51);
        JSONArray json52 = JSONArray.fromObject(problemDemoList52);
        JSONArray json53 = JSONArray.fromObject(problemDemoList53);
        map5.put("sectionName", "五大队");
        map5.put("notresolve", json51);
        map5.put("resolve", json52);
        map5.put("sum", json53);
        List<ProblemDemo> problemDemoList61 = problemMapper.selectDimAll(startTime, endTime, "未解决", "六大队");
        List<ProblemDemo> problemDemoList62 = problemMapper.selectDimAll(startTime, endTime, "已完成", "六大队");
        List<ProblemDemo> problemDemoList63 = problemMapper.selectDimAll1(startTime, endTime, "六大队");
        Map<String, Object> map6 = new HashMap<String, Object>();
        JSONArray json61 = JSONArray.fromObject(problemDemoList61);
        JSONArray json62 = JSONArray.fromObject(problemDemoList62);
        JSONArray json63 = JSONArray.fromObject(problemDemoList63);
        map6.put("sectionName", "六大队");
        map6.put("notresolve", json61);
        map6.put("resolve", json62);
        map6.put("sum", json63);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        if (list.size() > 0) {
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * @return
     */
    @Override
    public ResultBean selectDimAllSy() {
        List<ProblemDemo> problemDemoList11 = problemMapper.selectDimAllSy("未解决", "一大队");
        List<ProblemDemo> problemDemoList12 = problemMapper.selectDimAllSy("已完成", "一大队");
        List<ProblemDemo> problemDemoList13 = problemMapper.selectDimAllSy("解决中", "一大队");
        List<ProblemDemo> problemDemoList14 = problemMapper.selectDimAllSy("不能解决", "一大队");
        Map<String, Object> map1 = new HashMap<String, Object>();
        JSONArray json11 = JSONArray.fromObject(problemDemoList11);
        JSONArray json12 = JSONArray.fromObject(problemDemoList12);
        JSONArray json13 = JSONArray.fromObject(problemDemoList13);
        JSONArray json14 = JSONArray.fromObject(problemDemoList14);
        map1.put("sectionName", "一大队");
        map1.put("notresolve", json11);
        map1.put("resolve", json12);
        map1.put("sum", json13);
        map1.put("insolubility", json14);
        List<ProblemDemo> problemDemoList21 = problemMapper.selectDimAllSy("未解决", "二大队");
        List<ProblemDemo> problemDemoList22 = problemMapper.selectDimAllSy("已完成", "二大队");
        List<ProblemDemo> problemDemoList23 = problemMapper.selectDimAllSy("解决中", "二大队");
        List<ProblemDemo> problemDemoList24 = problemMapper.selectDimAllSy("不能解决", "二大队");
        Map<String, Object> map2 = new HashMap<String, Object>();
        JSONArray json21 = JSONArray.fromObject(problemDemoList21);
        JSONArray json22 = JSONArray.fromObject(problemDemoList22);
        JSONArray json23 = JSONArray.fromObject(problemDemoList23);
        JSONArray json24 = JSONArray.fromObject(problemDemoList24);
        map2.put("sectionName", "二大队");
        map2.put("notresolve", json21);
        map2.put("resolve", json22);
        map2.put("sum", json23);
        map2.put("insolubility", json24);
        List<ProblemDemo> problemDemoList31 = problemMapper.selectDimAllSy("未解决", "三大队");
        List<ProblemDemo> problemDemoList32 = problemMapper.selectDimAllSy("已完成", "三大队");
        List<ProblemDemo> problemDemoList33 = problemMapper.selectDimAllSy("解决中", "三大队");
        List<ProblemDemo> problemDemoList34 = problemMapper.selectDimAllSy("不能解决", "三大队");
        Map<String, Object> map3 = new HashMap<String, Object>();
        JSONArray json31 = JSONArray.fromObject(problemDemoList31);
        JSONArray json32 = JSONArray.fromObject(problemDemoList32);
        JSONArray json33 = JSONArray.fromObject(problemDemoList33);
        JSONArray json34 = JSONArray.fromObject(problemDemoList34);
        map3.put("sectionName", "三大队");
        map3.put("notresolve", json31);
        map3.put("resolve", json32);
        map3.put("sum", json33);
        map3.put("insolubility", json34);
        List<ProblemDemo> problemDemoList41 = problemMapper.selectDimAllSy("未解决", "四大队");
        List<ProblemDemo> problemDemoList42 = problemMapper.selectDimAllSy("已完成", "四大队");
        List<ProblemDemo> problemDemoList43 = problemMapper.selectDimAllSy("解决中", "四大队");
        List<ProblemDemo> problemDemoList44 = problemMapper.selectDimAllSy("不能解决", "四大队");
        Map<String, Object> map4 = new HashMap<String, Object>();
        JSONArray json41 = JSONArray.fromObject(problemDemoList41);
        JSONArray json42 = JSONArray.fromObject(problemDemoList42);
        JSONArray json43 = JSONArray.fromObject(problemDemoList43);
        JSONArray json44 = JSONArray.fromObject(problemDemoList44);
        map4.put("sectionName", "四大队");
        map4.put("notresolve", json41);
        map4.put("resolve", json42);
        map4.put("sum", json43);
        map4.put("insolubility", json44);
        List<ProblemDemo> problemDemoList51 = problemMapper.selectDimAllSy("未解决", "五大队");
        List<ProblemDemo> problemDemoList52 = problemMapper.selectDimAllSy("已完成", "五大队");
        List<ProblemDemo> problemDemoList53 = problemMapper.selectDimAllSy("解决中", "五大队");
        List<ProblemDemo> problemDemoList54 = problemMapper.selectDimAllSy("不能解决", "五大队");
        Map<String, Object> map5 = new HashMap<String, Object>();
        JSONArray json51 = JSONArray.fromObject(problemDemoList51);
        JSONArray json52 = JSONArray.fromObject(problemDemoList52);
        JSONArray json53 = JSONArray.fromObject(problemDemoList53);
        JSONArray json54 = JSONArray.fromObject(problemDemoList54);
        map5.put("sectionName", "五大队");
        map5.put("notresolve", json51);
        map5.put("resolve", json52);
        map5.put("sum", json53);
        map5.put("insolubility", json54);
        List<ProblemDemo> problemDemoList61 = problemMapper.selectDimAllSy("未解决", "六大队");
        List<ProblemDemo> problemDemoList62 = problemMapper.selectDimAllSy("已完成", "六大队");
        List<ProblemDemo> problemDemoList63 = problemMapper.selectDimAllSy("解决中", "六大队");
        List<ProblemDemo> problemDemoList64 = problemMapper.selectDimAllSy("不能解决", "六大队");
        Map<String, Object> map6 = new HashMap<String, Object>();
        JSONArray json61 = JSONArray.fromObject(problemDemoList61);
        JSONArray json62 = JSONArray.fromObject(problemDemoList62);
        JSONArray json63 = JSONArray.fromObject(problemDemoList63);
        JSONArray json64 = JSONArray.fromObject(problemDemoList64);
        map6.put("sectionName", "六大队");
        map6.put("notresolve", json61);
        map6.put("resolve", json62);
        map6.put("sum", json63);
        map6.put("insolubility", json64);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        if (list.size() > 0) {
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 按条件查询没有核查的问题
     *
     * @param staffAcceptId
     * @param roadName
     * @return
     */
    @Override
    public ResultBean selectProblem(Integer staffAcceptId, String roadName, String problemStrat) {
        List<Problem> problems = problemMapper.selectProblem(staffAcceptId, roadName, problemStrat);
        if (problems.size() > 0 || problems.size() == 0) {
            return ResultUtil.setOK("success", problems);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 查询所有除去已完成以外的多有问题
     *
     * @return
     */
    @Override
    public ResultBean selectProblemByState() {
        List<Problem> problems = problemMapper.selectProblemByState();
        if (problems.size() > 0) {
            return ResultUtil.setOK("success", problems);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 统计
     *
     * @param sectionId
     * @param time
     * @return
     */
    @Override
    public ResultBean selectProblemByCount(String problemState, Integer sectionId, String time) {
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        Map<String, Object> map3 = new HashMap<String, Object>();
        Map<String, Object> map4 = new HashMap<String, Object>();
        Map<String, Object> map5 = new HashMap<String, Object>();
        Map<String, Object> map6 = new HashMap<String, Object>();

        List<Task> task1 = taskMapper.selectTaskByCount(problemState, 25, time);
        List<Task> task2 = taskMapper.selectTaskByCount("已完成", 25, time);
        List<Problem> problem1 = problemMapper.selectProblemByCount(problemState, 25, time);
        List<Problem> problem2 = problemMapper.selectProblemByCount("已解决", 25, time);
        map1.put("sectionName", "一大队");
        map1.put("taskAll", task1);
        map1.put("taskywc", task2);
        map1.put("problemAll", problem1);
        map1.put("problemyjj", problem2);

        List<Task> task3 = taskMapper.selectTaskByCount(problemState, 26, time);
        List<Task> task4 = taskMapper.selectTaskByCount("已完成", 26, time);
        List<Problem> problem3 = problemMapper.selectProblemByCount(problemState, 26, time);
        List<Problem> problem4 = problemMapper.selectProblemByCount("已解决", 26, time);
        map2.put("sectionName", "二大队");
        map2.put("taskAll", task3);
        map2.put("taskywc", task4);
        map2.put("problemAll", problem3);
        map2.put("problemyjj", problem4);

        List<Task> task5 = taskMapper.selectTaskByCount(problemState, 27, time);
        List<Task> task6 = taskMapper.selectTaskByCount("已完成", 27, time);
        List<Problem> problem5 = problemMapper.selectProblemByCount(problemState, 27, time);
        List<Problem> problem6 = problemMapper.selectProblemByCount("已解决", 27, time);
        map3.put("sectionName", "三大队");
        map3.put("taskAll", task5);
        map3.put("taskywc", task6);
        map3.put("problemAll", problem5);
        map3.put("problemyjj", problem6);

        List<Task> task7 = taskMapper.selectTaskByCount(problemState, 28, time);
        List<Task> task8 = taskMapper.selectTaskByCount("已完成", 28, time);
        List<Problem> problem7 = problemMapper.selectProblemByCount(problemState, 28, time);
        List<Problem> problem8 = problemMapper.selectProblemByCount("已解决", 28, time);
        map4.put("sectionName", "四大队");
        map4.put("taskAll", task7);
        map4.put("taskywc", task8);
        map4.put("problemAll", problem7);
        map4.put("problemyjj", problem8);

        List<Task> task9 = taskMapper.selectTaskByCount(problemState, 29, time);
        List<Task> task10 = taskMapper.selectTaskByCount("已完成", 29, time);
        List<Problem> problem9 = problemMapper.selectProblemByCount(problemState, 29, time);
        List<Problem> problem10 = problemMapper.selectProblemByCount("已解决", 29, time);
        map5.put("sectionName", "五大队");
        map5.put("taskAll", task9);
        map5.put("taskywc", task10);
        map5.put("problemAll", problem9);
        map5.put("problemyjj", problem10);

        List<Task> task11 = taskMapper.selectTaskByCount(problemState, 30, time);
        List<Task> task12 = taskMapper.selectTaskByCount("已完成", 30, time);
        List<Problem> problem11 = problemMapper.selectProblemByCount(problemState, 30, time);
        List<Problem> problem12 = problemMapper.selectProblemByCount("已解决", 30, time);
        map6.put("sectionName", "六大队");
        map6.put("taskAll", task11);
        map6.put("taskywc", task12);
        map6.put("problemAll", problem11);
        map6.put("problemyjj", problem12);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);

        if (list.size() > 0) {
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 根据问题id查详情
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean selectProblemByid(Integer id, Integer type) {
        if (type == 1) {
            List<Problem> problem = problemMapper.selectProblemByid(id);
            if (problem != null) {
                return ResultUtil.setOK("success", problem);
            }
        } else if (type == 2) {
            List<Task> tasks = taskMapper.selectTaskByid(id);
            if (tasks != null) {
                return ResultUtil.setOK("success", tasks);
            }
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 查询所有不能解决的问题
     *
     * @return
     */
    @Override
    public ResultBean selectProbleByStart(String problemState) {
        List<Object> problems = problemMapper.selectProbleByStart(problemState);
        if (problems.size() > 0) {
            return ResultUtil.setOK("success", problems);
        }

        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 模糊查询
     *
     * @param typeKey
     * @param problemStrat
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public ResultBean selectByProblemdetail(String typeKey, String problemStrat, String beginTime, String endTime) {
        List<Object> list = problemMapper.selectByProblemdetail(typeKey, problemStrat, beginTime, endTime);
        if (list.size() > 0) {
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 批量删除
     *
     * @param array
     * @return
     */
    @Override
    public ResultBean deleteByProblemid(int[] array) {
        return ResultUtil.execOp(problemMapper.deleteByProblemid(array), "批量删除");
    }

    /**
     * 首页统计所有
     *
     * @return
     */
    @Override
    public ResultBean selectAllCount() {
        //查询所有问题
        List<Problem> problemList = problemMapper.selectProblemByStaffId(null);
        int problemnumn = problemList.size();//统计出来全部问题个数
        List problemtype = problemMapper.selectProblemType(); //问题类型
        List sectiontype = problemMapper.selectSectionType();  //部门类型
        List<Section> sectiontotal = problemMapper.selectSetionTotal(); //大队发现的问题总个数
        List<Section> problemtotal = problemMapper.selectProblemTotal1(); //根据大队根据类型统计问题数
        List<Problem> problemList1 = problemMapper.selectProblemByRosove("不能解决");
        List<Problem> problemList2 = problemMapper.selectProblemByRosove("已解决");
        List<Problem> problemList3 = problemMapper.selectProblemByRosove("走任务解决");
        List<Problem> problemList4 = problemMapper.selectProblemByRosove("协调解决");
        Map<String, Object> map = null;
        List<Object> list = new ArrayList<>();
        List<Section> sections = sectionMapper.selectAllSection();
        //int[] str = new int[]{25, 26, 27, 28, 29, 30};
        int[] str = new int[255];
        for (int p = 0; p < sections.size(); p++) {
            str[p] = sections.get(p).getSysSectionId();

        }
        List<Problem> problemtotal2 = null;
        for (int u = 0; u < str.length; u++) {
            map = new HashMap<>();
            problemtotal2 = problemMapper.selectProblemTotalfu(str[u]);
            if (problemtotal2.size() > 0) {
                for (int p = 0; p < problemtotal2.size(); p++) {
                    map.put("sectionName", problemtotal2.get(p).getProblemDescribe());
                    map.put(problemtotal2.get(p).getProblemState(), problemtotal2.get(p).getProblemId());

                }
                list.add(map);
            }

        }
        int problemnumn1 = problemList1.size();
        int problemnumn2 = problemList2.size();
        int problemnumn3 = problemList3.size();
        int problemnumn4 = problemList4.size();
        LinkedHashMap<String, Object> json = new LinkedHashMap<>();

        json.put("大队", sectiontype);  //大队类型
        json.put("问题", problemtype);  //问题类型
        json.put("类型", problemtotal);  //各大队各类型问题统计
        json.put("problemnumn", problemnumn);
        json.put("不能解决个数", problemnumn1);
        json.put("已解决个数", problemnumn2);
        json.put("走任务解决个数", problemnumn3);
        json.put("协调解决个数", problemnumn4);
        json.put("统计", sectiontotal);
        json.put("count", list);
        return ResultUtil.setOK("success", json);
    }


    private List<Staff> getChild(int id, List<Staff> rootMenu) {//int id  id 是指当前菜单id，rootMenu是指要查找的列表
        List<Staff> childList = new ArrayList<>();
        Iterator iterList = rootMenu.iterator();
        while (iterList.hasNext()) {
            Staff treemenu = (Staff) iterList.next();
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (treemenu.getStafffPid() != 0) {
                if (treemenu.getStafffPid() == id) {
                    childList.add(treemenu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        /*for (Treemenu treemenu : childList) {*/
        Iterator iterList2 = childList.iterator();
        while (iterList2.hasNext()) {
            Staff treemenu = (Staff) iterList2.next();
            treemenu.setStaffList(getChild(treemenu.getSysStaffId(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
