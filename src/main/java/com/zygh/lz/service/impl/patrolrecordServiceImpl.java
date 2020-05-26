package com.zygh.lz.service.impl;

import com.zygh.lz.admin.*;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.*;
import com.zygh.lz.service.patrolrecordService;
import com.zygh.lz.util.GPSTransformMars;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.util.StringUtil;
import com.zygh.lz.vo.ResultBean;
import com.zygh.lz.vo.pgs;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.geom.Point2D;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class patrolrecordServiceImpl implements patrolrecordService {

    @Autowired
    private PatrolrecordMapper patrolrecordMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private GpsMapper gpsMapper;
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private XareaMapper xareaMapper;
    @Autowired
    private XlevelserviceMapper xlevelserviceMapper;
    @Autowired
    private SectionMapper sectionMapper;

    /**
     * 新增巡查记录
     *
     * @param patrolrecord
     * @return
     */
    @Override
    public ResultBean addPatrolrecord(Patrolrecord patrolrecord) {
      /*  if (patrolrecord.getPatrolRecordGps() != null) {
            String patrolRecordGps = patrolrecord.getPatrolRecordGps();
            patrolRecordGps = pgs.replace(patrolRecordGps);
            String s = GPSTransformMars.GCj2TOWGS(patrolRecordGps);
            patrolrecord.setPatrolRecordGps(s);
        }
*/
        int i = patrolrecordMapper.insertSelective(patrolrecord);
        if (i > 0) {
            //上线
            Staff staff = new Staff();
            staff.setSysStaffId(patrolrecord.getSysStaffId());
            staff.setStaffOnline("1");
            staffMapper.updateByPrimaryKeySelective(staff);

            Patrolrecord patrolrecord1 = patrolrecordMapper.selectBylast();
            //返回巡查记录id
            return ResultUtil.setOK("success", patrolrecord1.getSysPatrolRecordId());
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 修改巡查记录
     *
     * @param patrolrecord
     * @return
     */
    @Override
    public ResultBean updatePatrolrecord(Patrolrecord patrolrecord) {
        //获取修改id
        Integer sysPatrolRecordId = patrolrecord.getSysPatrolRecordId();
        Patrolrecord patrolrecord1 = patrolrecordMapper.selectByPrimaryKey(sysPatrolRecordId);

        //下线修改人员表在线不在线状态
        if (patrolrecord.getPatrolRecordEndtime()!=null) {
            Staff staff = new Staff();
            staff.setSysStaffId(patrolrecord1.getSysStaffId());
            staff.setStaffOnline("0");
            int i = staffMapper.updateByPrimaryKeySelective(staff);
        }

        if (patrolrecord.getPatrolRecordGps() != null) {
            //修改app穿过来的gps坐标格式
            String patrolRecordGps = pgs.replace(patrolrecord.getPatrolRecordGps());
            if (patrolrecord1.getPatrolRecordGps() != null && !patrolrecord1.getPatrolRecordGps().equals("")) {
                patrolrecord.setPatrolRecordGps(patrolrecord1.getPatrolRecordGps() + "," + patrolRecordGps);
            } else {
                patrolrecord.setPatrolRecordGps(patrolRecordGps);
            }
        }

        return ResultUtil.execOp(patrolrecordMapper.updateByPrimaryKeySelective(patrolrecord), "修改");
    }

    /**
     * 根据id删除巡查记录
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean delPatrolrecord(Integer id) {
        return ResultUtil.execOp(patrolrecordMapper.deleteByPrimaryKey(id), "删除");
    }

    /**
     * 根据道路类型、部门查询所有巡查信息(分层查询)
     * 管理员看所有,大队长看本大队，中队长看中队，警员看个人
     * @param staffid
     * @return
     */
    @Override
    public ResultBean selectByRoadtype(Integer staffid, String beginTime, String endTime) {
        //登陆人信息
        Staff staff = staffMapper.selectByPrimaryKey(staffid);
        if (staff == null) {
            return ResultUtil.setError(SystemCon.RERROR1, "请传入登录人主键", null);
        }
        //提取个人信息中得所属大队
        Integer sysSectionId = staff.getSysSectionId();
        //提取个人信息中的职务大队长/中队长
        String staffpost = staff.getStaffPost();
        Section section = sectionMapper.selectByPrimaryKey(staff.getSysSectionId());

        if (staffpost != null) {
            if (staffpost.equals("大队长") || staffpost.equals("副大队长") || staffpost.equals("指导员") ) {
                //本大队
                List<Patrolrecord> patrolrecordList = patrolrecordMapper.selectByRoadtype(null, beginTime, endTime, section.getSectionPid());
                if (patrolrecordList.size() > 0) {
                    for (Patrolrecord patrolrecord : patrolrecordList) {
                        Integer staffID = patrolrecord.getSysStaffId();
                        int sysPatrolRecordId = patrolrecord.getSysPatrolRecordId();
                        Integer problemid = patrolrecord.getSysPatrolRecordId();
                        List<Gps> gpsList = gpsMapper.selectGpsByRecord(staffID, sysPatrolRecordId);
                        List<Problem> problemList = problemMapper.selectProblemByRecord(problemid);
                        patrolrecord.setGpsList(gpsList);
                        patrolrecord.setProblemList(problemList);
                    }
                    return ResultUtil.setOK("success", patrolrecordList);
                }
                return ResultUtil.setError(SystemCon.RERROR1, "error", null);
            } else if (staffpost.equals("中队长")|| staff.getStaffNum().equals("2245") || staff.getStaffNum().equals("007828")) {
                //System.out.println("中队");
                //本中队巡查记录
                List<Patrolrecord> patrolrecords2 = patrolrecordMapper.selectByRoadtype(sysSectionId, beginTime, endTime, null);
                if (patrolrecords2.size() > 0) {
                    for (Patrolrecord patrolrecord : patrolrecords2) {
                        Integer staffID = patrolrecord.getSysStaffId();
                        int sysPatrolRecordId = patrolrecord.getSysPatrolRecordId();
                        Integer problemid = patrolrecord.getSysPatrolRecordId();
                        List<Gps> gpsList = gpsMapper.selectGpsByRecord(staffID, sysPatrolRecordId);
                        List<Problem> problemList = problemMapper.selectProblemByRecord(problemid);
                        patrolrecord.setGpsList(gpsList);
                        patrolrecord.setProblemList(problemList);
                    }
                    return ResultUtil.setOK("success", patrolrecords2);
                }

                return ResultUtil.setError(SystemCon.RERROR1, "error", null);
            } else if (staffpost.equals("管理员")) {
                //System.out.println("管理员");
                List<Patrolrecord> patrolrecords2 = patrolrecordMapper.selectByRoadtype(null, beginTime, endTime, null);
                if (patrolrecords2.size() > 0) {
                    for (Patrolrecord patrolrecord : patrolrecords2) {
                        Integer staffID = patrolrecord.getSysStaffId();
                        int sysPatrolRecordId = patrolrecord.getSysPatrolRecordId();
                        Integer problemid = patrolrecord.getSysPatrolRecordId();
                        List<Gps> gpsList = gpsMapper.selectGpsByRecord(staffID, sysPatrolRecordId);
                        List<Problem> problemList = problemMapper.selectProblemByRecord(problemid);
                        patrolrecord.setGpsList(gpsList);
                        patrolrecord.setProblemList(problemList);
                    }
                    return ResultUtil.setOK("success", patrolrecords2);
                }

                return ResultUtil.setError(SystemCon.RERROR1, "error", null);
            } else {
                return ResultUtil.setError(SystemCon.RERROR1, "error", null);

            }

        } else { //如果为普通民警只能看自己的巡查轨迹
            //System.out.println("个人");
            List<Patrolrecord> patrolrecords2 = patrolrecordMapper.selectBysysStaffId(staffid, beginTime, endTime);
            if (patrolrecords2.size() > 0) {
                for (Patrolrecord patrolrecord : patrolrecords2) {
                    Integer staffID = patrolrecord.getSysStaffId();
                    int sysPatrolRecordId = patrolrecord.getSysPatrolRecordId();
                    Integer problemid = patrolrecord.getSysPatrolRecordId();
                    List<Gps> gpsList = gpsMapper.selectGpsByRecord(staffID, sysPatrolRecordId);
                    List<Problem> problemList = problemMapper.selectProblemByRecord(problemid);
                    patrolrecord.setGpsList(gpsList);
                    patrolrecord.setProblemList(problemList);
                }
                return ResultUtil.setOK("success", patrolrecords2);
            }

            return ResultUtil.setError(SystemCon.RERROR1, "error", null);
        }


    }

    @Override
    public ResultBean selectByhierarchy(String staffHierarchy, Integer sysSectionId, String roadType) {
        return null;
    }

    @Override
    public ResultBean selectBysysStaffId(Integer staffid, String roadType) {
        return null;
    }

    @Override
    public ResultBean updatePatrolrecordBysysStaffId(Patrolrecord patrolrecord) {
        return null;
    }

    @Override
    public ResultBean selectBypatrolstate(Integer staffid, String roadType) {
        return null;
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ResultBean countPatrolrecord(String startTime, String endTime) throws Exception {
        //处理时间
        Date startDate = simpleDateFormat.parse(startTime);
        Date endDate = simpleDateFormat.parse(endTime);

        List<Map> list = new ArrayList<>();

        int one1 = patrolrecordMapper.countPatrolrecord("一大队", "主干道", startDate, endDate);
        int one2 = patrolrecordMapper.countPatrolrecord("一大队", "拥堵路段", startDate, endDate);
        int one3 = patrolrecordMapper.countPatrolrecord("一大队", "施工路段", startDate, endDate);
        int one4 = one1 + one2 + one3;
        Map<String, Object> map1 = new HashMap<>();
        map1.put("zgd", one1);
        map1.put("ydld", one2);
        map1.put("sgld", one3);
        map1.put("sum", one4);
        Map<String, Object> map11 = new HashMap<>();
        map11.put("sectionName", "一大队");
        map11.put("staffHierarchay", map1);
        list.add(map11);

        int two1 = patrolrecordMapper.countPatrolrecord("二大队", "主干道", startDate, endDate);
        int two2 = patrolrecordMapper.countPatrolrecord("二大队", "拥堵路段", startDate, endDate);
        int two3 = patrolrecordMapper.countPatrolrecord("二大队", "施工路段", startDate, endDate);
        int two4 = two1 + two2 + two3;
        Map<String, Object> map2 = new HashMap<>();
        map2.put("zgd", two1);
        map2.put("ydld", two2);
        map2.put("sgld", two3);
        map2.put("sum", two4);
        Map<String, Object> map22 = new HashMap<>();
        map22.put("sectionName", "二大队");
        map22.put("staffHierarchay", map2);
        list.add(map22);

        int three1 = patrolrecordMapper.countPatrolrecord("三大队", "主干道", startDate, endDate);
        int three2 = patrolrecordMapper.countPatrolrecord("三大队", "拥堵路段", startDate, endDate);
        int three3 = patrolrecordMapper.countPatrolrecord("三大队", "施工路段", startDate, endDate);
        int three4 = three1 + three2 + three3;
        Map<String, Object> map3 = new HashMap<>();
        map3.put("zgd", three1);
        map3.put("ydld", three2);
        map3.put("sgld", three3);
        map3.put("sum", three4);
        Map<String, Object> map33 = new HashMap<>();
        map33.put("sectionName", "三大队");
        map33.put("staffHierarchay", map3);
        list.add(map33);

        int four1 = patrolrecordMapper.countPatrolrecord("四大队", "主干道", startDate, endDate);
        int four2 = patrolrecordMapper.countPatrolrecord("四大队", "拥堵路段", startDate, endDate);
        int four3 = patrolrecordMapper.countPatrolrecord("四大队", "施工路段", startDate, endDate);
        int four4 = four1 + four2 + four3;
        Map<String, Object> map4 = new HashMap<>();
        map4.put("zgd", four1);
        map4.put("ydld", four2);
        map4.put("sgld", four3);
        map4.put("sum", four4);
        Map<String, Object> map44 = new HashMap<>();
        map44.put("sectionName", "四大队");
        map44.put("staffHierarchay", map4);
        list.add(map44);

        int five1 = patrolrecordMapper.countPatrolrecord("五大队", "主干道", startDate, endDate);
        int five2 = patrolrecordMapper.countPatrolrecord("五大队", "拥堵路段", startDate, endDate);
        int five3 = patrolrecordMapper.countPatrolrecord("五大队", "施工路段", startDate, endDate);
        int five4 = five1 + five2 + five3;
        Map<String, Object> map5 = new HashMap<>();
        map5.put("zgd", five1);
        map5.put("ydld", five2);
        map5.put("sgld", five3);
        map5.put("sum", five4);
        Map<String, Object> map55 = new HashMap<>();
        map55.put("sectionName", "五大队");
        map55.put("staffHierarchay", map5);
        list.add(map55);

        int sex1 = patrolrecordMapper.countPatrolrecord("六大队", "主干道", startDate, endDate);
        int sex2 = patrolrecordMapper.countPatrolrecord("六大队", "拥堵路段", startDate, endDate);
        int sex3 = patrolrecordMapper.countPatrolrecord("六大队", "施工路段", startDate, endDate);
        int sex4 = sex1 + sex2 + sex3;
        Map<String, Object> map6 = new HashMap<>();
        map6.put("zgd", sex1);
        map6.put("ydld", sex2);
        map6.put("sgld", sex3);
        map6.put("sum", sex4);
        Map<String, Object> map66 = new HashMap<>();
        map66.put("sectionName", "六大队");
        map66.put("staffHierarchay", map6);
        list.add(map66);

        return ResultUtil.setOK("success", list);
    }

    /**
     * 查询所有巡查记录
     *
     * @return
     */
    @Override
    public ResultBean selectAllPatrolrecord() {
        List<Patrolrecord> patrolrecords = patrolrecordMapper.selectAllPatrolrecord();
        if (patrolrecords.size() > 0) {
            return ResultUtil.setOK("success", patrolrecords);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 查询所有巡查记录的视频
     *
     * @return
     */
    @Override
    public ResultBean selectVideo() {
        List<Patrolrecord> patrolrecords = patrolrecordMapper.selectVideo();
        if (patrolrecords.size() > 0) {
            List<Patrolrecord> list = new ArrayList<>();
            for (int i = 0; i < patrolrecords.size(); i++) {
                String[] strArray = null;
                String patrolRecordVideo = patrolrecords.get(i).getPatrolRecordVideo();
                //切割
                strArray = patrolRecordVideo.split("\\,");
                if (strArray.length > 0) {
                    for (int j = 0; j < strArray.length; j++) {
                        Patrolrecord patrolrecord = new Patrolrecord();
                        //拷贝
                        BeanUtils.copyProperties(patrolrecords.get(i), patrolrecord);
                        patrolrecord.setPatrolRecordVideo(strArray[j]);
                        list.add(patrolrecord);
                    }

                }
            }
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 根据开始时间，结束时间、道路名称查询巡查记录
     *
     * @return
     */
    @Override
    public ResultBean selectDim(String roadName, String beginTime, String endTime) {
        List<Object> patrolrecords = patrolrecordMapper.selectDim(roadName, beginTime, endTime);
        if (patrolrecords.size() > 0) {
            return ResultUtil.setOK("success", patrolrecords);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 根据时间查询个人巡查记录
     *
     * @param SysStaffId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public ResultBean selectByStaff(Integer SysStaffId, String beginTime, String endTime) {
        List<Map<String, Object>> patrolrecordList = patrolrecordMapper.selectByStaff(SysStaffId, beginTime, endTime);
        Map<String, Object> mape = new HashMap<>();
        List<Gps> gpsList1 = null;
        List<Problem> problemList = null;
        if (patrolrecordList.size() > 0) {
            for (int i = 0; i < patrolrecordList.size(); i++) {
                Integer problemid = (Integer) patrolrecordList.get(i).get("sys_patrol_record_id");
                if (SysStaffId != null && problemid != null) {
                    gpsList1 = gpsMapper.selectGpsByRecord(SysStaffId, problemid);
                }
                if (gpsList1.size() != 0) {
                    patrolrecordList.get(i).put("gpsList", gpsList1);
                    if (problemMapper.selectProblemByRecord(problemid) != null) {
                        problemList = problemMapper.selectProblemByRecord(problemid);
                        for (int j = 0; j < problemList.size(); j++) {
                            mape.put("problemList", problemList);
                        }
                    }
                } else {
                    continue;
                }
                patrolrecordList.get(i).put("problemList", problemList);

            }
            if (patrolrecordList.size() > 0 || patrolrecordList.size() == 0) {
                return ResultUtil.setOK("success", patrolrecordList);
            }

        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    @Override
    public ResultBean selectByStaffapp(Integer SysStaffId, String beginTime, String endTime) {
        List<Map<String, Object>> patrolrecordList = patrolrecordMapper.selectByStaff(SysStaffId, beginTime, endTime);
        String arr[] = new String[20];
        if (patrolrecordList.size() > 0) {
            for (int i = 0; i < patrolrecordList.size(); i++) {
                Map<String, Object> map = patrolrecordList.get(i);
                Integer problemid = (Integer) map.get("sys_patrol_record_id");
                if (problemMapper.selectProblemByRecord(problemid) != null) {
                    List<Problem> problemList = problemMapper.selectProblemByRecord(problemid);
                    Map<String, Object> maps = new HashMap<>();
                    for (int c = 0; c < problemList.size(); c++) {
                        maps.put("problemList", problemList);
                    }
                }
            }
            if (patrolrecordList.size() > 0 || patrolrecordList.size() == 0) {
                return ResultUtil.setOK("success", patrolrecordList);
            }
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 查询各个大队的巡查记录个数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ResultBean selectRecordNum(String startDate, String endDate) {
        List<Patrolrecord> patrolrecords = patrolrecordMapper.selectRecordNum(startDate, endDate);
        if (patrolrecords.size() > 0) {
            return ResultUtil.setOK("success", patrolrecords);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 根据登录用户负责道路列表
     *
     * @param staffId
     * @return
     */
    @Override
    public ResultBean selectByStaffId(Integer staffId) {
        Staff staff = staffMapper.selectInfoByid(staffId);
        List<Object> list = null;
        if (staff.getStaffHierarchy() != null) {
            if (staff.getStaffHierarchy().equals("一级路长")) {
                list = patrolrecordMapper.selectByStaffId(staffId);
            } else if (staff.getStaffHierarchy().equals("二级路长")) {
                list = patrolrecordMapper.selectBytwostaffId(staffId);
            } else {
                return ResultUtil.setError(SystemCon.RERROR1, "error", null);
            }
        }
        if (list != null) {
            if (list.size() > 0 || list.size() == 0) {
                return ResultUtil.setOK("success", list);
            }
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
    public ResultBean delectPatrolrecordById(int[] array) {
        return ResultUtil.execOp(patrolrecordMapper.delectPatrolrecordById(array), "批量删除");
    }

    /**
     * 根据id查询该人的巡查记录
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean selectAllPatrolrecordById(Integer id) {
        List<Patrolrecord> patrolrecords = patrolrecordMapper.selectAllPatrolrecordById(id);
        if (patrolrecords.size() > 0 || patrolrecords.size() == 0) {
            return ResultUtil.setOK("success", patrolrecords);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }


    private List<Staff> getChild(Integer id, List<Staff> rootMenu) throws NullPointerException {//int id  id 是指当前菜单id，rootMenu是指要查找的列表
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

    @Override
    public List<HashMap> theDaySum(String battalion) throws Exception {
        List<HashMap> daySumList = new ArrayList<>();


        //日常。。。。。。。
        HashMap<String, Object> gdMap = new HashMap<>();
        //夜巡。。。。。。。
        HashMap<String, Object> yxMap = new HashMap<>();
        //特殊
        HashMap<String, Object> tsMap = new HashMap<>();

        List<Integer> yxList = patrolrecordMapper.countYxSDsum(battalion);
        int yxSDsum = yxList.size();

        List<Integer> gfList = patrolrecordMapper.countGdorGfSDsum("高峰岗", battalion);
        int gfSDsum = gfList.size();

        List<Integer> rcList = patrolrecordMapper.countRcSDsum(battalion);
        int rcSDsum = rcList.size();


        //夜巡
        Integer yxYDsum = xareaMapper.countYXYD(battalion, null) / 2;
        Integer ksYDsum = xareaMapper.selectcelerityPope("快速岗", battalion, null).size() / 3;
        yxYDsum = yxYDsum + ksYDsum;
        /**
         * 辅警夜巡
         */



        //高峰岗=有民警的高峰岗岗位数+有民警的固定岗位数+机动岗所有民警数的一半+铁骑所有民警数的一半+有民警的重点机关岗的岗位数
        //284	    166			            26		    62		            23	        	7
        //高峰应到数
        Integer gfYDsum = xareaMapper.countGFYD(battalion, null);

        //日常勤务平峰期=（【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
        //202		                7         4						                    62   85      22
        //固定
        Integer gdYDsum = xareaMapper.countGDYD(battalion, null);
        //固定岗组数
        Integer gdYDzusum = xareaMapper.countggZSum("固定岗", null, null);
        //重点
        Integer zdYDsum = xareaMapper.countZDYD(battalion, null);
        //重点岗位数
        Integer zdYDjgsum = xareaMapper.countZdZSum(battalion, null);
        //铁骑
        Integer tqYDsum = xareaMapper.countTQYD(battalion, null);
        //网格
        Integer wgYDsum = xareaMapper.countWGYD(battalion, null);
        //高速
        Integer gsYDsum = xareaMapper.countGSYD(battalion, null);
        //机动
        Integer jdYDsum = xareaMapper.countJDYD(battalion, null).size() / 2;
        //其他
        Integer qtYDsum = xareaMapper.countQtYDSum(battalion, null).size() / 2;

        /**
         * 辅警
         * 日常勤务平峰期=（【高速大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
         */
        //郑少高速大队
        Integer zsgsnum = xareaMapper.selectAssistPolice("高速岗", "郑少高速大队", null, "辅警", null, null).size() / 3;
        //西南绕城高速大队
        Integer xngsnum = xareaMapper.selectAssistPolice("高速岗", "西南绕城高速大队", null, "辅警", null, null).size() / 3;
        //网格
        Integer wgnum = xareaMapper.selectAssistPolice("区域", null, null, "辅警", "网格", null).size() / 2;
        //固定
        Integer gdnum = xareaMapper.selectAssistPolice("固定岗", null, null, "辅警", null, null).size() / 2;
        //铁骑
        Integer tqnum = xareaMapper.selectAssistPolice(null, null, null, "辅警", "铁骑", "2").size() / 2;
        //机动
        Integer jdnum = xareaMapper.selectAssistPolice("机动岗", null, null, "辅警", null, null).size() / 2;
        //辅警日常勤务人数
        Integer policenum = zsgsnum + xngsnum + wgnum + gdnum + tqnum+jdnum;

        //int tqnumber=tqYDsum/2;
        //日常勤务 平峰期
        int rcYDsum = gsYDsum + jdYDsum + wgYDsum + gdYDsum + tqYDsum;
        //特殊勤务
        Integer TQYDsum = xlevelserviceMapper.selectorderlyAll(battalion);
       /* if (TQYDsum == null) {
            TQYDsum = 0;
        }*/
        TQYDsum = 0;

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dayTime = df.format(new Date());// new Date()为获取当前系统时间

        Date startTime = ft.parse(dayTime + " 07:30:00");
        Date endTime = ft.parse(dayTime + " 09:00:00");
        Date startTime2 = ft.parse(dayTime + " 17:30:00");
        Date endTime2 = ft.parse(dayTime + " 18:30:00");
        Date startTime3 = ft.parse(dayTime + " 07:00:00");
        Date endTime3 = ft.parse(dayTime + " 20:00:00");
        Date startTime4 = ft.parse(dayTime + " 18:30:00");
        Date endTime4 = ft.parse(dayTime + " 20:00:00");
        Date nowTime = new Date();
        //早高峰
        boolean effectiveDate = isEffectiveDate(nowTime, startTime, endTime);
        //晚高峰
        boolean effectiveDate2 = isEffectiveDate(nowTime, startTime2, endTime2);
        //固定岗
        boolean effectiveDate3 = isEffectiveDate(nowTime, startTime3, endTime3);
        boolean effectiveDate4 = isEffectiveDate(nowTime, startTime4, endTime4);

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        if (effectiveDate3) {
            //如果在早高峰
            if (effectiveDate) {
                //高峰岗=有民警的高峰岗岗位数+有民警的固定岗位数+机动岗所有民警数的一半+铁骑所有民警数的一半+有民警的重点机关岗的岗位数
                //284	    166			            26		    62		            23	        	7
                int numtq = tqYDsum / 2;
                int zgfYDsum = gfYDsum + gdYDzusum + jdYDsum + numtq + zdYDjgsum;
                /**
                 * 辅警
                 *高峰岗=有民警的高峰岗岗位数+有民警的固定岗位数+机动岗所有民警数的一半+铁骑所有民警数的一半+有民警的重点机关岗的岗位数
                 *536	        243			26		        202		            58		            7
                 */
                //高峰岗
                Integer gfgsnum = xareaMapper.selectAssistPolice("高峰岗", null, null, "辅警", null, null).size();

                Integer fjYDsum = gfgsnum+gdYDzusum+jdnum+tqnum+zdYDjgsum;
                int zgfSDsum = rcSDsum + gfSDsum;

                gdMap.put("type", "日常勤务");
                gdMap.put("time", "早高峰7:30-8:30");
                gdMap.put("gfYDsum", zgfYDsum);
                gdMap.put("fjYDsum", fjYDsum);
                gdMap.put("gfSDsum", zgfSDsum);
                if (zgfYDsum == 0) {
                    gdMap.put("gfZXL", 0);
                } else {
                    gdMap.put("gfZXL", numberFormat.format((float) zgfSDsum / (float) zgfYDsum * 100));
                }


                tsMap.put("type", "特殊勤务");
                tsMap.put("time", "08:00-20:00");
                tsMap.put("gfYDsum", TQYDsum);
                tsMap.put("gfSDsum", 0);
                tsMap.put("gfZXL", 0);


                daySumList.add(gdMap);
                daySumList.add(tsMap);


            } else if (effectiveDate2) {

                int numtq = tqYDsum / 2;
                int wgfYDsum = gfYDsum + gdYDzusum + jdYDsum + numtq + zdYDjgsum;
                int wgfSDsum = rcSDsum + gfSDsum;

                /**
                 * 辅警
                 *高峰岗=有民警的高峰岗岗位数+有民警的固定岗位数+机动岗所有民警数的一半+铁骑所有民警数的一半+有民警的重点机关岗的岗位数
                 *536	        243			26		        202		            58		            7
                 */
                //高峰岗
                Integer gfgsnum = xareaMapper.selectAssistPolice("高速岗", null, null, "辅警", null, null).size();

                Integer fjYDsum = gfgsnum+gdYDzusum+jdnum+tqnum+zdYDjgsum;
                //晚高峰
                gdMap.put("type", "日常勤务");
                gdMap.put("time", "晚高峰17:30-18:30");
                gdMap.put("gfYDsum", wgfYDsum);
                gdMap.put("fjYDsum", fjYDsum);
                gdMap.put("gfSDsum", wgfSDsum);
                if (wgfYDsum == 0) {
                    gdMap.put("gfZXL", 0);
                } else {
                    gdMap.put("gfZXL", numberFormat.format((float) wgfSDsum / (float) wgfYDsum * 100));
                }

                tsMap.put("type", "特殊勤务");
                tsMap.put("time", "08:00-20:00");
                tsMap.put("gfYDsum", TQYDsum);
                tsMap.put("gfSDsum", 0);
                tsMap.put("gfZXL", 0);

                //daySumList.add(wgfMap);
                daySumList.add(gdMap);
                daySumList.add(tsMap);

            } else {


                gdMap.put("type", "日常勤务");
                if (effectiveDate4) {
                    gdMap.put("time", "平峰期18:30-20:00");
                } else {
                    gdMap.put("time", "平峰期8:30-17:30");
                }
                gdMap.put("gfYDsum", rcYDsum);
                gdMap.put("gfSDsum", rcSDsum);
                gdMap.put("pfYDsum", policenum);
                if (rcYDsum == 0) {
                    gdMap.put("gfZXL", 0);
                } else {
                    gdMap.put("gfZXL", numberFormat.format((float) rcSDsum / (float) rcYDsum * 100));
                }


                tsMap.put("type", "特殊勤务");
                tsMap.put("time", "08:00-20:00");
                tsMap.put("gfYDsum", TQYDsum);
                tsMap.put("gfSDsum", 0);
                tsMap.put("gfZXL", 0);

                daySumList.add(gdMap);
                daySumList.add(tsMap);

            }
        } else {

            yxMap.put("type", "夜巡");
            yxMap.put("time", "20:00-次日07:00");
            yxMap.put("gfYDsum", yxYDsum);
            yxMap.put("gfSDsum", yxSDsum);
            if (yxYDsum == 0) {
                gdMap.put("gfZXL", 0);
            } else {
                gdMap.put("gfZXL", numberFormat.format((float) yxSDsum / (float) yxYDsum * 100));
            }

            tsMap.put("type", "特殊勤务");
            tsMap.put("time", "20:00-次日07:00");
            tsMap.put("gfYDsum", 0);
            tsMap.put("gfSDsum", 0);
            tsMap.put("gfZXL", 0);


            daySumList.add(yxMap);
            daySumList.add(tsMap);

        }

        return daySumList;
    }


    @Override
    public List<HashMap> typeSum(String battalion) throws Exception {
        List<HashMap> typeSumList = new ArrayList<>();
        //固定组数、应到数
        HashMap<String, Object> gdMap = new HashMap<>();
        //高峰岗组数、应到数
        HashMap<String, Object> gfMap = new HashMap<>();
        //铁骑实组数、应到数
        HashMap<String, Object> tqMap = new HashMap<>();
        //网格实组数、应到数
        HashMap<String, Object> wgMap = new HashMap<>();
        //重点组、应到实到
        HashMap<String, Object> zdMap = new HashMap<>();
        //其他组、应到实到
        HashMap<String, Object> qtMap = new HashMap<>();
        //高速
        HashMap<String, Object> gsMap = new HashMap<>();
        //快速
        HashMap<String, Object> ksMap = new HashMap<>();
        //特勤
        HashMap<String, Object> TQMap = new HashMap<>();
        //夜巡
        HashMap<String, Object> YXMap = new HashMap<>();

        //应到

        //夜巡
        Integer yxYDsum = xareaMapper.countYXYD(battalion, null) / 2;
        Integer ksYDsum = xareaMapper.selectcelerityPope("快速岗", battalion, null).size() / 3;
        yxYDsum = yxYDsum + ksYDsum;

        //高峰应到数
        Integer gfYDsum = xareaMapper.countGFYD(battalion, null);

        //日常勤务 固定+重点+铁骑+网格+高速+其他
        Integer gdYDsum = xareaMapper.countGDYD(battalion, null);
        //重点
        Integer zdYDsum = xareaMapper.countZDYD(battalion, null);
        //铁骑
        Integer tqYDsum = xareaMapper.countTQYD(battalion, null);
        //网格
        Integer wgYDsum = xareaMapper.countWGYD(battalion, null);
        //高速
        Integer gsYDsum = xareaMapper.countGSYD(battalion, null);
        //其他
        Integer qtYDsum = xareaMapper.countQtYDSum(battalion, null).size() / 2;

        int rcYDsum = gdYDsum + zdYDsum + tqYDsum + wgYDsum + gsYDsum + qtYDsum;

        //特殊勤务
        Integer TQYDsum = xlevelserviceMapper.selectorderlyAll(battalion);
        /*if (TQYDsum == null) {
            TQYDsum = 0;
        }*/
        TQYDsum = 0;

        int tqZnum = xareaMapper.countTqZSum(battalion, null).size();
        int gdZnum = xareaMapper.countggZSum("固定岗", battalion, null);
        int gfZnum = xareaMapper.countggZSum("高峰岗", battalion, null);
        int wgZnum = xareaMapper.countWgZSum(battalion, null).size();
        int zdZnum = xareaMapper.countZdZSum(battalion, null);//重点岗
        int qtZnum = xareaMapper.countQtZSum(battalion, "", null);//其他岗
        int gsZnum = xareaMapper.countGsOrKsZ("高速岗", battalion, null).size();
        int ksZnum = xareaMapper.countGsOrKsZ("快速岗", battalion, null).size();
        int TQZnum = xareaMapper.countTQZ(battalion).size();


        int gdSD = patrolrecordMapper.countGdorGfSDsum("固定岗", battalion).size();
        int gfSD = patrolrecordMapper.countGdorGfSDsum("高峰岗", battalion).size();
        int tqSD = patrolrecordMapper.countTqSDsum(battalion).size();
        int wgSD = patrolrecordMapper.countWgSDsum(battalion).size();
        int zdSD = patrolrecordMapper.countZdSDsum(battalion).size();//重点
        int qtSD = patrolrecordMapper.countQtSDsum(battalion).size();
        int gsSD = patrolrecordMapper.countGsorKsSDsum("高速岗", battalion, null).size();
        int ksSD = patrolrecordMapper.countGsorKsSDsum("快速岗", battalion, null).size();


        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dayTime = df.format(new Date());// new Date()为获取当前系统时间

        Date startTime = ft.parse(dayTime + " 07:30:00");
        Date endTime = ft.parse(dayTime + " 09:00:00");
        Date startTime2 = ft.parse(dayTime + " 17:30:00");
        Date endTime2 = ft.parse(dayTime + " 18:30:00");
        Date startTime3 = ft.parse(dayTime + " 07:00:00");
        Date endTime3 = ft.parse(dayTime + " 20:00:00");
        Date startTime4 = ft.parse(dayTime + " 18:30:00");
        Date endTime4 = ft.parse(dayTime + " 20:00:00");
        Date nowTime = new Date();
        //早高峰
        boolean effectiveDate = isEffectiveDate(nowTime, startTime, endTime);
        //晚高峰
        boolean effectiveDate2 = isEffectiveDate(nowTime, startTime2, endTime2);
        //固定岗
        boolean effectiveDate3 = isEffectiveDate(nowTime, startTime3, endTime3);
        boolean effectiveDate4 = isEffectiveDate(nowTime, startTime4, endTime4);

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        if (effectiveDate3) {
            //如果在早高峰
            if (effectiveDate) {

                gdMap.put("name", "固定岗");
                gdMap.put("YDnum", gdYDsum);
                gdMap.put("Znum", gdZnum);
                gdMap.put("SDnum", gdSD);

                gfMap.put("name", "高峰岗");
                gfMap.put("YDnum", gfYDsum);
                gfMap.put("Znum", gfZnum);
                gfMap.put("SDnum", gfSD);

                tqMap.put("name", "铁骑");
                tqMap.put("YDnum", tqYDsum);
                tqMap.put("Znum", tqZnum);
                tqMap.put("SDnum", tqSD);

                wgMap.put("name", "网格");
                wgMap.put("YDnum", wgYDsum);
                wgMap.put("Znum", wgZnum);
                wgMap.put("SDnum", wgSD);

                zdMap.put("name", "重点");
                zdMap.put("YDnum", zdYDsum);
                zdMap.put("Znum", zdZnum);
                zdMap.put("SDnum", zdSD);

                qtMap.put("name", "其他");
                qtMap.put("YDnum", qtYDsum);
                qtMap.put("Znum", qtZnum);
                qtMap.put("SDnum", qtSD);

                gsMap.put("name", "高速");
                gsMap.put("YDnum", gsYDsum);
                gsMap.put("Znum", gsZnum);
                gsMap.put("SDnum", gsSD);

                ksMap.put("name", "快速");
                ksMap.put("YDnum", 0);
                ksMap.put("Znum", ksZnum);
                ksMap.put("SDnum", ksSD);

                if (TQZnum == 0) {
                    TQYDsum = 0;

                }
                TQMap.put("name", "特勤");
                TQMap.put("YDnum", TQYDsum);
                TQMap.put("Znum", TQZnum);
                TQMap.put("SDnum", 0);

                YXMap.put("name", "夜巡");
                YXMap.put("YDnum", 0);
                YXMap.put("Znum", 38);
                YXMap.put("SDnum", 0);


                //早高峰
                typeSumList.add(gdMap);
                typeSumList.add(gfMap);
                typeSumList.add(tqMap);
                typeSumList.add(wgMap);
                typeSumList.add(zdMap);
                typeSumList.add(qtMap);
                typeSumList.add(gsMap);
                typeSumList.add(ksMap);
                typeSumList.add(TQMap);
                typeSumList.add(YXMap);

            } else if (effectiveDate2) {

                gdMap.put("name", "固定岗");
                gdMap.put("YDnum", gdYDsum);
                gdMap.put("Znum", gdZnum);
                gdMap.put("SDnum", gdSD);

                gfMap.put("name", "高峰岗");
                gfMap.put("YDnum", gfYDsum);
                gfMap.put("Znum", gfZnum);
                gfMap.put("SDnum", gfSD);

                tqMap.put("name", "铁骑");
                tqMap.put("YDnum", tqYDsum);
                tqMap.put("Znum", tqZnum);
                tqMap.put("SDnum", tqSD);

                wgMap.put("name", "网格");
                wgMap.put("YDnum", wgYDsum);
                wgMap.put("Znum", wgZnum);
                wgMap.put("SDnum", wgSD);

                zdMap.put("name", "重点");
                zdMap.put("YDnum", zdYDsum);
                zdMap.put("Znum", zdZnum);
                zdMap.put("SDnum", zdSD);

                qtMap.put("name", "其他");
                qtMap.put("YDnum", qtYDsum);
                qtMap.put("Znum", qtZnum);
                qtMap.put("SDnum", qtSD);

                gsMap.put("name", "高速");
                gsMap.put("YDnum", gsYDsum);
                gsMap.put("Znum", gsZnum);
                gsMap.put("SDnum", gsSD);

                ksMap.put("name", "快速");
                ksMap.put("YDnum", 0);
                ksMap.put("Znum", ksZnum);
                ksMap.put("SDnum", ksSD);

                if (TQZnum == 0) {
                    TQYDsum = 0;

                }
                TQMap.put("name", "特勤");
                TQMap.put("YDnum", TQYDsum);
                TQMap.put("Znum", TQZnum);
                TQMap.put("SDnum", 0);

                YXMap.put("name", "夜巡");
                YXMap.put("YDnum", 0);
                YXMap.put("Znum", 38);
                YXMap.put("SDnum", 0);

                //晚高峰
                typeSumList.add(gdMap);
                typeSumList.add(gfMap);
                typeSumList.add(tqMap);
                typeSumList.add(wgMap);
                typeSumList.add(zdMap);
                typeSumList.add(qtMap);
                typeSumList.add(gsMap);
                typeSumList.add(ksMap);
                typeSumList.add(TQMap);
                typeSumList.add(YXMap);

            } else {
                //平峰
                gdMap.put("name", "固定岗");
                gdMap.put("YDnum", gdYDsum);
                gdMap.put("Znum", gdZnum);
                gdMap.put("SDnum", gdSD);

                gfMap.put("name", "高峰岗");
                gfMap.put("YDnum", 0);
                gfMap.put("Znum", gfZnum);
                gfMap.put("SDnum", gfSD);

                tqMap.put("name", "铁骑");
                tqMap.put("YDnum", tqYDsum);
                tqMap.put("Znum", tqZnum);
                tqMap.put("SDnum", tqSD);

                wgMap.put("name", "网格");
                wgMap.put("YDnum", wgYDsum);
                wgMap.put("Znum", wgZnum);
                wgMap.put("SDnum", wgSD);

                zdMap.put("name", "重点");
                zdMap.put("YDnum", zdYDsum);
                zdMap.put("Znum", zdZnum);
                zdMap.put("SDnum", zdSD);

                qtMap.put("name", "其他");
                qtMap.put("YDnum", qtYDsum);
                qtMap.put("Znum", qtZnum);
                qtMap.put("SDnum", qtSD);

                gsMap.put("name", "高速");
                gsMap.put("YDnum", gsYDsum);
                gsMap.put("Znum", gsZnum);
                gsMap.put("SDnum", gsSD);

                ksMap.put("name", "快速");
                ksMap.put("YDnum", 0);
                ksMap.put("Znum", ksZnum);
                ksMap.put("SDnum", ksSD);

                if (TQZnum == 0) {
                    TQYDsum = 0;

                }
                TQMap.put("name", "特勤");
                TQMap.put("YDnum", TQYDsum);
                TQMap.put("Znum", TQZnum);
                TQMap.put("SDnum", 0);

                YXMap.put("name", "夜巡");
                YXMap.put("YDnum", 0);
                YXMap.put("Znum", 38);
                YXMap.put("SDnum", 0);


                typeSumList.add(gdMap);
                typeSumList.add(gfMap);
                typeSumList.add(tqMap);
                typeSumList.add(wgMap);
                typeSumList.add(zdMap);
                typeSumList.add(qtMap);
                typeSumList.add(gsMap);
                typeSumList.add(ksMap);
                typeSumList.add(TQMap);
                typeSumList.add(YXMap);
            }
        } else {

            gdMap.put("name", "固定岗");
            gdMap.put("YDnum", 0);
            gdMap.put("Znum", gdZnum);
            gdMap.put("SDnum", 0);

            gfMap.put("name", "高峰岗");
            gfMap.put("YDnum", 0);
            gfMap.put("Znum", gfZnum);
            gfMap.put("SDnum", 0);

            tqMap.put("name", "铁骑");
            tqMap.put("YDnum", 0);
            tqMap.put("Znum", tqZnum);
            tqMap.put("SDnum", 0);

            wgMap.put("name", "网格");
            wgMap.put("YDnum", 0);
            wgMap.put("Znum", wgZnum);
            wgMap.put("SDnum", 0);

            zdMap.put("name", "重点");
            zdMap.put("YDnum", 0);
            zdMap.put("Znum", zdZnum);
            zdMap.put("SDnum", 0);

            qtMap.put("name", "其他");
            qtMap.put("YDnum", 0);
            qtMap.put("Znum", qtZnum);
            qtMap.put("SDnum", 0);

            gsMap.put("name", "高速");
            gsMap.put("YDnum", 0);
            gsMap.put("Znum", gsZnum);
            gsMap.put("SDnum", 0);

            ksMap.put("name", "快速");
            ksMap.put("YDnum", ksYDsum);
            ksMap.put("Znum", ksZnum);
            ksMap.put("SDnum", ksSD);

            if (TQZnum == 0) {
                TQYDsum = 0;

            }
            TQMap.put("name", "特勤");
            TQMap.put("YDnum", TQYDsum);
            TQMap.put("Znum", TQZnum);
            TQMap.put("SDnum", 0);
            //夜巡
            YXMap.put("name", "夜巡");
            YXMap.put("YDnum", yxYDsum);
            YXMap.put("Znum", 38);
            YXMap.put("SDnum", 0);


            typeSumList.add(gdMap);
            typeSumList.add(gfMap);
            typeSumList.add(tqMap);
            typeSumList.add(wgMap);
            typeSumList.add(zdMap);
            typeSumList.add(qtMap);
            typeSumList.add(gsMap);
            typeSumList.add(ksMap);
            typeSumList.add(TQMap);
            typeSumList.add(YXMap);

        }


        return typeSumList;
    }


    @Override
    public List<HashMap> typeSumByTime(String battalion) throws Exception {

        List<HashMap> typeSumList = new ArrayList<>();
        //固定组数、应到数
        HashMap<String, Object> gdMap = new HashMap<>();
        //高峰岗组数、应到数
        HashMap<String, Object> gfMap = new HashMap<>();
        //铁骑实组数、应到数
        HashMap<String, Object> tqMap = new HashMap<>();
        //网格实组数、应到数
        HashMap<String, Object> wgMap = new HashMap<>();
        //重点组、应到实到
        HashMap<String, Object> zdMap = new HashMap<>();
        //其他组、应到实到
        HashMap<String, Object> qtMap = new HashMap<>();
        //高速
        HashMap<String, Object> gsMap = new HashMap<>();

        //应到
        //固定岗 +4为六大队应到
        int gdYDsum = xareaMapper.countYDSum("固定岗", battalion, null).size();
        //高峰岗
        int gfYDsum = xareaMapper.countYDSum("高峰岗", battalion, null).size();
        //重点
        int zdYDsum = xareaMapper.countZdYDSum(battalion).size();

        //铁骑2
        int tqYDsum = xareaMapper.countYxSum("2", battalion, null).size();
        //网格
        int wgYDsum = xareaMapper.countWgSum(battalion).size();
        //其他
        int qtYDsum = xareaMapper.countQtYDSum(battalion, null).size();
        //日间高速应到
        int gsYDsum = xareaMapper.countGsOrKsYDSum("高速岗", battalion, null, null).size();


        int tqZnum = xareaMapper.countTqZSum(battalion, null).size();
        int gdZnum = xareaMapper.countggZSum("固定岗", battalion, null);
        int gfZnum = xareaMapper.countggZSum("高峰岗", battalion, null);
        int wgZnum = xareaMapper.countWgZSum(battalion, null).size();
        int zdZnum = xareaMapper.countZdZSum(battalion, null);//重点岗
        int qtZnum = xareaMapper.countQtZSum(battalion, null, null);//其他岗
        int gsZnum = xareaMapper.countGsOrKsZ("高速岗", battalion, null).size();


        int gdSD = patrolrecordMapper.countGdorGfSDsum("固定岗", battalion).size();
        int gfSD = patrolrecordMapper.countGdorGfSDsum("高峰岗", battalion).size();
        int tqSD = patrolrecordMapper.countTqSDsum(battalion).size();
        int wgSD = patrolrecordMapper.countWgSDsum(battalion).size();
        int zdSD = patrolrecordMapper.countZdSDsum(battalion).size();//重点
        int qtSD = patrolrecordMapper.countQtSDsum(battalion).size();
        int gsSD = patrolrecordMapper.countGsorKsSDsum("高速岗", battalion, null).size();


        gdMap.put("name", "固定岗");
        gdMap.put("YDnum", gdYDsum);
        gdMap.put("Znum", gdZnum);
        gdMap.put("SDnum", gdSD);

        gfMap.put("name", "高峰岗");
        gfMap.put("YDnum", gfYDsum);
        gfMap.put("Znum", gfZnum);
        gfMap.put("SDnum", gfSD);

        tqMap.put("name", "铁骑");
        tqMap.put("YDnum", tqYDsum);
        tqMap.put("Znum", tqZnum);
        tqMap.put("SDnum", tqSD);

        wgMap.put("name", "网格");
        wgMap.put("YDnum", wgYDsum);
        wgMap.put("Znum", wgZnum);
        wgMap.put("SDnum", wgSD);

        zdMap.put("name", "重点");
        zdMap.put("YDnum", zdYDsum);
        zdMap.put("Znum", zdZnum);
        zdMap.put("SDnum", zdSD);

        qtMap.put("name", "其他");
        qtMap.put("YDnum", qtYDsum);
        qtMap.put("Znum", qtZnum);
        qtMap.put("SDnum", qtSD);

        gsMap.put("name", "高速");
        gsMap.put("YDnum", gsYDsum);
        gsMap.put("Znum", gsZnum);
        gsMap.put("SDnum", gsSD);

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        //早高峰
        typeSumList.add(gdMap);
        typeSumList.add(gfMap);
        typeSumList.add(tqMap);
        typeSumList.add(wgMap);
        typeSumList.add(zdMap);
        typeSumList.add(qtMap);
        typeSumList.add(gsMap);


        return typeSumList;
    }

    /**
     * 日常勤务统计
     *
     * @param battalion
     * @return
     * @throws Exception
     */
    @Override
    public List<HashMap> countZD(String battalion) throws Exception {
        List<HashMap> brigadeList = new ArrayList<>();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dayTime = df.format(new Date());// new Date()为获取当前系统时间

        Date startTime = ft.parse(dayTime + " 07:30:00");
        Date endTime = ft.parse(dayTime + " 09:00:00");
        Date startTime2 = ft.parse(dayTime + " 17:30:00");
        Date endTime2 = ft.parse(dayTime + " 18:30:00");
        Date startTime3 = ft.parse(dayTime + " 07:00:00");
        Date endTime3 = ft.parse(dayTime + " 20:00:00");
        Date nowTime = new Date();
        //早高峰
        boolean effectiveDate = isEffectiveDate(nowTime, startTime, endTime);
        //晚高峰
        boolean effectiveDate2 = isEffectiveDate(nowTime, startTime2, endTime2);
        //平峰期
        boolean effectiveDate3 = isEffectiveDate(nowTime, startTime3, endTime3);

        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        //白天
        if (effectiveDate3) {
            //如果在早高峰
            if (effectiveDate || effectiveDate2) {
                //没有大队名称
                if (StringUtils.isEmpty(battalion)) {
                    List<String> ddNames = xareaMapper.findDd();

                    for (String ddName : ddNames) {
                        if (ddName.equals("高架大队")) {
                            continue;
                        }

                        List<String> zdNames = xareaMapper.findZd(ddName, null);

                        HashMap<String, Object> ddMap = new HashMap<>();

                        List<HashMap> zDList = new ArrayList<>();

                        int ddYDSum = 0;
                        int ddSDSum = 0;
                        int fjddYDSum = 0;
                        int fjddSDSum = 0;
                        //中队名字
                        for (String zdName : zdNames) {
                            HashMap<String, Object> zdMap = new HashMap<>();

                            //高峰应到数
                            Integer gfYDsum = xareaMapper.countGFYD(ddName, zdName);

                            //日常勤务平峰期=（【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                            //202		                7         4						                    62   85      22
                            //固定
                            Integer gdYDsum = xareaMapper.countGDYD(ddName, zdName);
                            //固定岗组数
                            Integer gdYDzusum = xareaMapper.countggZSum("固定岗", ddName, zdName);
                            //重点
                            Integer zdYDsum = xareaMapper.countZDYD(ddName, zdName);
                            //重点岗位数
                            Integer zdYDjgsum = xareaMapper.countZdZSum(ddName, zdName);
                            //铁骑
                            Integer tqYDsum = xareaMapper.countTQYD(ddName, zdName);
                            //网格
                            Integer wgYDsum = xareaMapper.countWGYD(ddName, zdName);
                            //高速
                            Integer gsYDsum = xareaMapper.countGSYD(ddName, zdName);
                            //机动
                            Integer jdYDsum = xareaMapper.countJDYD(ddName, zdName).size() / 2;
                            //其他
                            Integer qtYDsum = xareaMapper.countQtYDSum(ddName, zdName).size() / 2;

                            //高峰岗=有民警的高峰岗岗位数+有民警的固定岗位数+机动岗所有民警数的一半+铁骑所有民警数的一半+有民警的重点机关岗的岗位数
                            //284	    166			            26		    62		            23	        	7
                            //int zgfYDsum = rcYDsum + gfYDsum;
                            int numtq = tqYDsum / 2;
                            int zgfYDsum = gfYDsum + gdYDzusum + jdYDsum + numtq + zdYDjgsum;

                            /**
                             * 辅警
                             * 日常勤务平峰期=（【高速大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                             */
                            //郑少高速大队
                            Integer zsgsnum = xareaMapper.selectAssistPolice("高速岗", ddName, zdName, "辅警", null, null).size() / 3;
                            //西南绕城高速大队
                            Integer xngsnum = xareaMapper.selectAssistPolice("高速岗", ddName, zdName, "辅警", null, null).size() / 3;
                            //网格
                            Integer wgnum = xareaMapper.selectAssistPolice("区域", ddName, zdName, "辅警", "网格", null).size() / 2;
                            //固定
                            Integer gdnum = xareaMapper.selectAssistPolice("固定岗", ddName, zdName, "辅警", null, null).size() / 2;
                            //铁骑
                            Integer tqnum = xareaMapper.selectAssistPolice(null, ddName, zdName, "辅警", "铁骑", "2").size() / 2;
                            //机动
                            Integer jdnum = xareaMapper.selectAssistPolice("机动岗", ddName, zdName, "辅警", null, null).size() / 2;
                            //辅警高峰岗
                            Integer fjgsnum = xareaMapper.selectAssistPolice("高峰岗", ddName, zdName, "辅警", null, null).size();

                            int fjYDsum = fjgsnum + gdYDzusum + jdnum + tqnum + zdYDjgsum;
                            //实到警力
                            int gfSDSum = patrolrecordMapper.countZDRcSDsum(ddName, zdName, "高峰岗","民警").size();
                            int fjSDSum = patrolrecordMapper.countZDRcSDsum(ddName, zdName, "高峰岗","辅警").size();

                            int zdYDSum = zgfYDsum;
                            int zdSDSum = gfSDSum;


                            zdMap.put("name", zdName);
                            zdMap.put("YDnum", zdYDSum);
                            zdMap.put("fjYDnum", fjYDsum);
                            zdMap.put("SDnum", zdSDSum);
                            zdMap.put("fjSDSum", fjSDSum);
                            if (zdYDSum == 0) {
                                zdMap.put("gfZXL", 0);
                            } else {
                                zdMap.put("gfZXL", numberFormat.format((float) zdSDSum / (float) zdYDSum * 100));
                            }

                            ddYDSum += zdYDSum;
                            ddSDSum += zdSDSum;
                            fjddYDSum += fjYDsum;
                            fjddSDSum += fjSDSum;

                            zDList.add(zdMap);
                        }

                        ddMap.put("ddName", ddName);
                        ddMap.put("ddYDnum", ddYDSum);
                        ddMap.put("fjddYDSum", fjddYDSum);
                        ddMap.put("ddSDnum", ddSDSum);
                        ddMap.put("fjddSDSum", fjddSDSum);
                        if ((ddYDSum) == 0) {
                            ddMap.put("gfZXL", 0);
                        } else {
                            ddMap.put("gfZXL", numberFormat.format((float) ddSDSum / (float) ddYDSum * 100));
                        }
                        ddMap.put("zdCount", zDList);
                        brigadeList.add(ddMap);
                    }


                } else {
                    //高峰有大队名字
                    List<String> zdNames = xareaMapper.findZd(battalion, null);

                    HashMap<String, Object> ddMap = new HashMap<>();

                    int ddYDSum = 0;
                    int ddSDSum = 0;
                    int fjddYDSum = 0;
                    int fjddSDSum = 0;

                    List<HashMap> zDList = new ArrayList<>();
                    //中队名字
                    for (String zdName : zdNames) {
                        HashMap<String, Object> zdMap = new HashMap<>();
                        //高峰应到数
                        Integer gfYDsum = xareaMapper.countGFYD(battalion, zdName);

                        //日常勤务平峰期=（【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                        //202		                7         4						                    62   85      22
                        //固定
                        //Integer gdYDsum = xareaMapper.countGDYD(battalion, zdName);
                        //固定岗组数
                        Integer gdYDzusum = xareaMapper.countggZSum("固定岗", battalion, zdName);
                        //重点
                        Integer zdYDsum = xareaMapper.countZDYD(battalion, zdName);
                        //重点岗位数
                        Integer zdYDjgsum = xareaMapper.countZdZSum(battalion, zdName);
                        //铁骑
                        Integer tqYDsum = xareaMapper.countTQYD(battalion, zdName);
                        //网格
                        Integer wgYDsum = xareaMapper.countWGYD(battalion, zdName);
                        //高速
                        Integer gsYDsum = xareaMapper.countGSYD(battalion, zdName);
                        //机动
                        Integer jdYDsum = xareaMapper.countJDYD(battalion, zdName).size() / 2;
                        //其他
                        Integer qtYDsum = xareaMapper.countQtYDSum(battalion, zdName).size() / 2;

                        //高峰岗=有民警的高峰岗岗位数+有民警的固定岗位数+机动岗所有民警数的一半+铁骑所有民警数的一半+有民警的重点机关岗的岗位数
                        //284	    166			            26		    62		            23	        	7
                        int numtq = tqYDsum / 2;
                        int rcYDSum = gfYDsum + gdYDzusum + jdYDsum + numtq + zdYDjgsum;
                        /**
                         * 辅警
                         * 日常勤务平峰期=（【高速大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                         */
                        //郑少高速大队
                        Integer zsgsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //西南绕城高速大队
                        Integer xngsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //网格
                        Integer wgnum = xareaMapper.selectAssistPolice("区域", battalion, zdName, "辅警", "网格", null).size() / 2;
                        //固定
                        Integer gdnum = xareaMapper.selectAssistPolice("固定岗", battalion, zdName, "辅警", null, null).size() / 2;
                        //铁骑
                        Integer tqnum = xareaMapper.selectAssistPolice(null, battalion, zdName, "辅警", "铁骑", "2").size() / 2;
                        //机动
                        Integer jdnum = xareaMapper.selectAssistPolice("机动岗", battalion, zdName, "辅警", null, null).size() / 2;
                        //辅警高峰岗
                        Integer fjgsnum = xareaMapper.selectAssistPolice("高峰岗", battalion, zdName, "辅警", null, null).size();

                        int fjYDsum = fjgsnum + gdYDzusum + jdnum + tqnum + zdYDjgsum;
                        //民警实到
                        int gfSDSum = patrolrecordMapper.countZDRcSDsum(battalion, zdName, "高峰岗","民警").size();
                        //辅警实到
                        int fjSDSum = patrolrecordMapper.countZDRcSDsum(battalion, zdName, "高峰岗","辅警").size();

                        int zdYDSum = rcYDSum;
                        int zdSDSum = gfSDSum;

                        //中队民警辅警数
                        zdMap.put("name", zdName);
                        zdMap.put("YDnum", zdYDSum);
                        zdMap.put("fjYDsum", fjYDsum);
                        zdMap.put("SDnum", zdSDSum);
                        zdMap.put("fjSDSum", fjSDSum);
                        if (zdYDSum == 0) {
                            zdMap.put("gfZXL", 0);
                        } else {
                            zdMap.put("gfZXL", numberFormat.format((float) zdSDSum / (float) zdYDSum * 100));
                        }

                        ddYDSum += zdYDSum;
                        ddSDSum += zdSDSum;
                        fjddYDSum += fjYDsum;
                        fjddSDSum += fjYDsum;
                        zDList.add(zdMap);
                    }

                    //大队辅警
                    ddMap.put("ddName", battalion);
                    ddMap.put("ddYDnum", ddYDSum);
                    ddMap.put("fjddYDSum", fjddYDSum);
                    ddMap.put("ddSDnum", ddSDSum);
                    ddMap.put("fjddSDSum", fjddSDSum);
                    if ((ddYDSum) == 0) {
                        ddMap.put("gfZXL", 0);
                    } else {
                        ddMap.put("gfZXL", numberFormat.format((float) ddSDSum / (float) ddYDSum * 100));
                    }
                    ddMap.put("zdCount", zDList);
                    brigadeList.add(ddMap);

                }
            } else {
                //平峰
                if (StringUtils.isEmpty(battalion)) {
                    List<String> ddNames = xareaMapper.findDd();
                    //大队名字
                    for (String ddName : ddNames) {
                        if (ddName.equals("高架大队")) {
                            continue;
                        }
                        List<String> zdNames = xareaMapper.findZd(ddName, null);

                        HashMap<String, Object> ddMap = new HashMap<>();

                        int ddYDSum = 0;
                        int ddSDSum = 0;
                        int fjddYDSum = 0;
                        int fjddSDSum = 0;

                        List<HashMap> zDList = new ArrayList<>();
                        //中队名字
                        for (String zdName : zdNames) {
                            HashMap<String, Object> zdMap = new HashMap<>();

                            //日常勤务平峰期=（【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                            //202		                7         4						                    62   85      22
                            //固定
                            Integer gdYDsum = xareaMapper.countGDYD(ddName, zdName);
                            //固定岗组数
                            Integer gdYDzusum = xareaMapper.countggZSum("固定岗", ddName, zdName);
                            //重点
                            Integer zdYDsum = xareaMapper.countZDYD(ddName, zdName);
                            //重点岗位数
                            Integer zdYDjgsum = xareaMapper.countZdZSum(ddName, zdName);
                            //铁骑
                            Integer tqYDsum = xareaMapper.countTQYD(ddName, zdName);
                            //网格
                            Integer wgYDsum = xareaMapper.countWGYD(ddName, zdName);
                            //高速
                            Integer gsYDsum = xareaMapper.countGSYD(ddName, zdName);
                            //机动
                            Integer jdYDsum = xareaMapper.countJDYD(ddName, zdName).size() / 2;
                            //其他
                            Integer qtYDsum = xareaMapper.countQtYDSum(ddName, zdName).size() / 2;

                            /**
                             * 辅警
                             * 日常勤务平峰期=（【高速大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                             */
                            //郑少高速大队
                            Integer zsgsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                            //西南绕城高速大队
                            Integer xngsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                            //网格
                            Integer wgnum = xareaMapper.selectAssistPolice("区域", battalion, zdName, "辅警", "网格", null).size() / 2;
                            //固定
                            Integer gdnum = xareaMapper.selectAssistPolice("固定岗", battalion, zdName, "辅警", null, null).size() / 2;
                            //铁骑
                            Integer tqnum = xareaMapper.selectAssistPolice(null, battalion, zdName, "辅警", "铁骑", "2").size() / 2;
                            //机动
                            Integer jdnum = xareaMapper.selectAssistPolice("机动岗", battalion, zdName, "辅警", null, null).size() / 2;
                            //辅警高峰岗
                            Integer fjgsnum = xareaMapper.selectAssistPolice("高峰岗", battalion, zdName, "辅警", null, null).size();
                            //辅警平峰
                            int fjYDsum = zsgsnum + xngsnum + jdnum + wgnum + gdnum+tqnum;

                            //日常勤务 平峰期
                            int rcYDSum = gsYDsum + jdYDsum + wgYDsum + gdYDsum + tqYDsum;
                            //民警实到
                            int rcSDSum = patrolrecordMapper.countZDRcSDsum(ddName, zdName, null,"民警").size();
                            //辅警实到
                            int rcfjSDSum = patrolrecordMapper.countZDRcSDsum(ddName, zdName, null,"辅警").size();

                            zdMap.put("name", zdName);
                            zdMap.put("YDnum", rcYDSum);
                            zdMap.put("fjYDsum", fjYDsum);
                            zdMap.put("SDnum", rcSDSum);
                            zdMap.put("rcfjSDSum", rcfjSDSum);
                            if (rcYDSum == 0) {
                                zdMap.put("gfZXL", 0);
                            } else {
                                zdMap.put("gfZXL", numberFormat.format((float) rcSDSum / (float) rcYDSum * 100));
                            }

                            ddYDSum += rcYDSum;
                            ddSDSum += rcSDSum;
                            fjddYDSum += fjYDsum;
                            fjddSDSum += rcfjSDSum;
                            zDList.add(zdMap);
                        }

                        ddMap.put("ddName", ddName);
                        ddMap.put("ddYDnum", ddYDSum);
                        ddMap.put("ddYDnum", ddYDSum);
                        ddMap.put("fjddYDSum", fjddYDSum);
                        ddMap.put("fjddSDSum", fjddSDSum);
                        if ((ddYDSum) == 0) {
                            ddMap.put("gfZXL", 0);
                        } else {
                            ddMap.put("gfZXL", numberFormat.format((float) ddSDSum / (float) ddYDSum * 100));
                        }
                        ddMap.put("zdCount", zDList);
                        brigadeList.add(ddMap);
                    }

                } else {
                    //大队名字

                    List<String> zdNames = xareaMapper.findZd(battalion, null);

                    HashMap<String, Object> ddMap = new HashMap<>();

                    int ddYDSum = 0;
                    int ddSDSum = 0;
                    int fjddYDSum = 0;
                    int fjddSDSum = 0;

                    List<HashMap> zDList = new ArrayList<>();
                    //中队名字
                    for (String zdName : zdNames) {
                        HashMap<String, Object> zdMap = new HashMap<>();

                        //日常勤务平峰期=（【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                        //202		                7         4						                    62   85      22
                        //固定
                        Integer gdYDsum = xareaMapper.countGDYD(battalion, zdName);
                        //固定岗组数
                        Integer gdYDzusum = xareaMapper.countggZSum("固定岗", battalion, zdName);
                        //重点
                        //Integer zdYDsum = xareaMapper.countZDYD(battalion, zdName);
                        //重点岗位数
                        Integer zdYDjgsum = xareaMapper.countZdZSum(battalion, zdName);
                        //铁骑
                        Integer tqYDsum = xareaMapper.countTQYD(battalion, zdName);
                        //网格
                        Integer wgYDsum = xareaMapper.countWGYD(battalion, zdName);
                        //高速
                        Integer gsYDsum = xareaMapper.countGSYD(battalion, zdName);
                        //机动
                        Integer jdYDsum = xareaMapper.countJDYD(battalion, zdName).size() / 2;
                        //其他
                        Integer qtYDsum = xareaMapper.countQtYDSum(battalion, zdName).size() / 2;

                        /**
                         * 辅警
                         * 日常勤务平峰期=（【高速大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】   +【（机动+网格+固定+铁骑）的民警人数一半】
                         */
                        //郑少高速大队
                        Integer zsgsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //西南绕城高速大队
                        Integer xngsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //网格
                        Integer wgnum = xareaMapper.selectAssistPolice("区域", battalion, zdName, "辅警", "网格", null).size() / 2;
                        //固定
                        Integer gdnum = xareaMapper.selectAssistPolice("固定岗", battalion, zdName, "辅警", null, null).size() / 2;
                        //铁骑
                        Integer tqnum = xareaMapper.selectAssistPolice(null, battalion, zdName, "辅警", "铁骑", "2").size() / 2;
                        //机动
                        Integer jdnum = xareaMapper.selectAssistPolice("机动岗", battalion, zdName, "辅警", null, null).size() / 2;
                        //辅警高峰岗
                        Integer fjgsnum = xareaMapper.selectAssistPolice("高峰岗", battalion, zdName, "辅警", null, null).size();

                        int fjYDsum = zsgsnum + xngsnum + jdnum + wgnum + gdnum+tqnum;

                        //民警日常勤务 平峰期
                        int rcYDSum = gsYDsum + jdYDsum + wgYDsum + gdYDsum + tqYDsum;

                        int rcSDSum = patrolrecordMapper.countZDRcSDsum(battalion, zdName, null,"民警").size();
                        //辅警实到
                        int fjrcSDSum = patrolrecordMapper.countZDRcSDsum(battalion, zdName, null,"辅警").size();

                        zdMap.put("name", zdName);
                        zdMap.put("YDnum", rcYDSum);
                        zdMap.put("fjYDsum", fjYDsum);
                        zdMap.put("SDnum", rcSDSum);
                        zdMap.put("fjrcSDSum", fjrcSDSum);
                        if (rcYDSum == 0) {
                            zdMap.put("gfZXL", 0);
                        } else {
                            zdMap.put("gfZXL", numberFormat.format((float) rcSDSum / (float) rcYDSum * 100));
                        }

                        ddYDSum += rcYDSum;
                        ddSDSum += rcSDSum;
                        fjddYDSum += fjYDsum;
                        fjddSDSum += fjrcSDSum;
                        zDList.add(zdMap);
                    }

                    ddMap.put("ddName", battalion);
                    ddMap.put("ddYDnum", ddYDSum);
                    ddMap.put("fjddYDSum", fjddYDSum);
                    ddMap.put("ddSDnum", ddSDSum);
                    ddMap.put("fjddSDSum", fjddSDSum);
                    if ((ddYDSum) == 0) {
                        ddMap.put("gfZXL", 0);
                    } else {
                        ddMap.put("gfZXL", numberFormat.format((float) ddSDSum / (float) ddYDSum * 100));
                    }
                    ddMap.put("zdCount", zDList);
                    brigadeList.add(ddMap);

                }

            }
        } else {
            //夜巡
            if (StringUtils.isEmpty(battalion)) {
                List<String> ddNames = xareaMapper.findDd();
                //大队名字
                for (String ddName : ddNames) {
                    if (ddName.equals("九大队")) {
                        continue;
                    }
                    List<String> zdNames = xareaMapper.findZd(ddName, "3");

                    HashMap<String, Object> ddMap = new HashMap<>();

                    int ddYDSum = 0;
                    int ddSDSum = 0;
                    int fjddYDSum = 0;
                    int fjddSDSum = 0;

                    List<HashMap> zDList = new ArrayList<>();
                    //中队名字
                    for (String zdName : zdNames) {
                        HashMap<String, Object> zdMap = new HashMap<>();

                        //夜巡=所有夜巡里面民警人数除去领导后人数的一半+【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】
                        //41		30				                        7        4
                        Integer yxYDsum = xareaMapper.countYXYD(ddName, zdName);
                        //高速
                        Integer gsYDsum = xareaMapper.countGSYD(battalion, zdName);
                        Integer zsgsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //郑少高速大队
                        Integer fjzsgsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //西南绕城高速大队
                        Integer fjxngsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                        //夜巡辅警
                        Integer yxnum = xareaMapper.selectAssistPolice("区域", battalion, zdName, "辅警", "夜巡", "3").size() / 2;

                        Integer yxYDSum = yxYDsum + gsYDsum;
                        Integer fjyxYDSum = fjzsgsnum + fjxngsnum+yxnum;

                        int yxSDSum = patrolrecordMapper.countZDYxSDsum(ddName, zdName,"民警").size();
                        int fjyxSDSum = patrolrecordMapper.countZDYxSDsum(ddName, zdName,"辅警").size();

                        zdMap.put("name", zdName);
                        zdMap.put("YDnum", yxYDSum);
                        zdMap.put("fjyxYDSum", fjyxYDSum);
                        zdMap.put("SDnum", yxSDSum);
                        zdMap.put("fjyxSDSum", fjyxSDSum);
                        if (yxYDSum == 0) {
                            zdMap.put("gfZXL", 0);
                        } else {
                            zdMap.put("gfZXL", numberFormat.format((float) yxSDSum / (float) yxYDSum * 100));
                        }

                        ddYDSum += yxYDSum;
                        ddSDSum += yxSDSum;
                        fjddYDSum += fjyxYDSum;
                        fjddSDSum += fjyxSDSum;
                        zDList.add(zdMap);
                    }
                    ddMap.put("ddName", ddName);
                    ddMap.put("ddYDnum", ddYDSum);
                    ddMap.put("fjddYDSum", fjddYDSum);
                    ddMap.put("ddSDnum", ddSDSum);
                    ddMap.put("fjddSDSum", fjddSDSum);
                    if ((ddYDSum) == 0) {
                        ddMap.put("gfZXL", 0);
                    } else {
                        ddMap.put("gfZXL", numberFormat.format((float) ddSDSum / (float) ddYDSum * 100));
                    }
                    ddMap.put("zdCount", zDList);
                    brigadeList.add(ddMap);
                }


            } else {
                //大队名字

                List<String> zdNames = xareaMapper.findZd(battalion, "3");

                HashMap<String, Object> ddMap = new HashMap<>();

                int ddYDSum = 0;
                int ddSDSum = 0;
                int fjddYDSum = 0;
                int fjddSDSum = 0;

                List<HashMap> zDList = new ArrayList<>();
                //中队名字
                for (String zdName : zdNames) {
                    HashMap<String, Object> zdMap = new HashMap<>();

                    //夜巡=所有夜巡里面民警人数除去领导后人数的一半+【高架大队（郑少+西南）除去事故中队和领导外全部民警人数的三分之一】
                    //41		30				                        7        4
                    Integer yxYDsum = xareaMapper.countYXYD(battalion, zdName);
                    //高速
                    Integer gsYDsum = xareaMapper.countGSYD(battalion, zdName);
                    Integer yxYDSum = yxYDsum + gsYDsum;
                    //郑少高速大队
                    Integer fjzsgsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                    //西南绕城高速大队
                    Integer fjxngsnum = xareaMapper.selectAssistPolice("高速岗", battalion, zdName, "辅警", null, null).size() / 3;
                    //夜巡辅警
                    Integer yxnum = xareaMapper.selectAssistPolice("区域", battalion, zdName, "辅警", "夜巡", "3").size() / 2;

                    Integer fjyxYDSum = fjzsgsnum + fjxngsnum+yxnum;

                    int yxSDSum = patrolrecordMapper.countZDYxSDsum(battalion, zdName,"民警").size();
                    //辅警
                    int fjyxSDSum = patrolrecordMapper.countZDYxSDsum(battalion, zdName,"辅警").size();

                    zdMap.put("name", zdName);
                    zdMap.put("YDnum", yxYDSum);
                    zdMap.put("fjyxYDSum", fjyxYDSum);
                    zdMap.put("SDnum", yxSDSum);
                    zdMap.put("fjyxSDSum", fjyxSDSum);
                    if (yxYDSum == 0) {
                        zdMap.put("gfZXL", 0);
                    } else {
                        zdMap.put("gfZXL", numberFormat.format((float) yxYDSum / (float) yxSDSum * 100));
                    }

                    ddYDSum += yxYDSum;
                    ddSDSum += yxSDSum;
                    fjddYDSum += fjyxYDSum;
                    fjddSDSum += fjyxSDSum;
                    zDList.add(zdMap);
                }
                ddMap.put("ddName", battalion);
                ddMap.put("ddYDnum", ddYDSum);
                ddMap.put("fjddYDSum", fjddYDSum);
                ddMap.put("ddSDnum", ddSDSum);
                ddMap.put("fjddSDSum", fjddSDSum);
                if ((ddYDSum) == 0) {
                    ddMap.put("gfZXL", 0);
                } else {
                    ddMap.put("gfZXL", numberFormat.format((float) ddSDSum / (float) ddYDSum * 100));
                }
                ddMap.put("zdCount", zDList);
                brigadeList.add(ddMap);

            }

        }

        return brigadeList;
    }

    @Override
    public List<HashMap> yXtypeSum(String battalion) throws Exception {
        List<HashMap> yXtypeSumList = new ArrayList<>();

        //夜巡
        HashMap<String, Object> yxMap = new HashMap<>();
        //高速
        HashMap<String, Object> yxGsMap = new HashMap<>();
        //快速
        HashMap<String, Object> yxKsMap = new HashMap<>();
        //其他组、应到实到
        HashMap<String, Object> yxQtMap = new HashMap<>();

        //夜间其他
        int qtYDsum = xareaMapper.countQtYDSum(battalion, "3").size();
        //夜间间高速应到
        int gsYDsum = xareaMapper.countGsOrKsYDSum("高速岗", battalion, null, "4").size();
        //夜间快速应到
        int ksYDsum = xareaMapper.countGsOrKsYDSum("快速岗", battalion, "3", null).size();
        //夜巡应到
        int yxYDsum = xareaMapper.countYxYDsum(battalion).size();


        int qtZnum = xareaMapper.countQtZSum(battalion, "3", null);//其他岗
        int gsZnum = xareaMapper.countGsOrKsZ("高速岗", battalion, null).size();
        int ksZnum = xareaMapper.countKsZ("快速岗", battalion, null).size();
        int yxZnum = xareaMapper.countYxZ(battalion, null).size();


        int qtSD = patrolrecordMapper.countQtSDsum(battalion).size();
        int gsSD = patrolrecordMapper.countGsorKsSDsum("高速岗", battalion, "3").size();
        int ksSD = patrolrecordMapper.countGsorKsSDsum("快速岗", battalion, "3").size();
        int yxSD = patrolrecordMapper.countYxSDsum(battalion).size();


        yxMap.put("name", "夜巡");
        yxMap.put("YDnum", yxYDsum);
        yxMap.put("Znum", yxZnum);
        yxMap.put("SDnum", yxSD);

        yxGsMap.put("name", "高速");
        yxGsMap.put("YDnum", gsYDsum);
        yxGsMap.put("Znum", gsZnum);
        yxGsMap.put("SDnum", gsSD);

        yxKsMap.put("name", "快速");
        yxKsMap.put("YDnum", ksYDsum);
        yxKsMap.put("Znum", ksZnum);
        yxKsMap.put("SDnum", ksSD);

        yxQtMap.put("name", "其他");
        yxQtMap.put("YDnum", qtYDsum);
        yxQtMap.put("Znum", qtZnum);
        yxQtMap.put("SDnum", qtSD);


        yXtypeSumList.add(yxMap);
        yXtypeSumList.add(yxGsMap);
        yXtypeSumList.add(yxKsMap);
        yXtypeSumList.add(yxQtMap);
        //}
        return yXtypeSumList;

    }


    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<HashMap> findNowByGps(double[] lon, double[] lat) {

        List<HashMap> inNowByGps = new ArrayList<>();

        //固定岗
        HashMap<String, Object> gdMap = new HashMap<>();
        gdMap.put("gangwei", "固定岗");
        //高峰岗
        HashMap<String, Object> gfMap = new HashMap<>();
        gfMap.put("gangwei", "高峰岗");
        //重点
        HashMap<String, Object> zdMap = new HashMap<>();
        zdMap.put("gangwei", "重点岗");
        //铁骑
        HashMap<String, Object> tqMap = new HashMap<>();
        tqMap.put("gangwei", "铁骑");
        //网格
        HashMap<String, Object> wgMap = new HashMap<>();
        wgMap.put("gangwei", "网格岗");
        //夜巡
        HashMap<String, Object> yxMap = new HashMap<>();
        yxMap.put("gangwei", "夜巡");
        //高速
        HashMap<String, Object> gsMap = new HashMap<>();
        gsMap.put("gangwei", "高速岗");
        //快速
        HashMap<String, Object> ksMap = new HashMap<>();
        ksMap.put("gangwei", "快速岗");
        //特勤
        HashMap<String, Object> TQMap = new HashMap<>();
        TQMap.put("gangwei", "特勤岗");
        //其他
        HashMap<String, Object> qtMap = new HashMap<>();
        qtMap.put("gangwei", "其他岗");

        //固定
        List<HashMap> gdLsit = new ArrayList<>();
        //高峰
        List<HashMap> gfLsit = new ArrayList<>();
        //重点
        List<HashMap> zdLsit = new ArrayList<>();
        //铁骑
        List<HashMap> tqLsit = new ArrayList<>();
        //网格
        List<HashMap> wgLsit = new ArrayList<>();
        //夜巡
        List<HashMap> yxLsit = new ArrayList<>();
        //高速
        List<HashMap> gsLsit = new ArrayList<>();
        //快速
        List<HashMap> ksLsit = new ArrayList<>();
        //特勤
        List<HashMap> TQLsit = new ArrayList<>();
        //其他
        List<HashMap> qtLsit = new ArrayList<>();


        if (lon != null && lat != null) {
            List<Integer> nowIds = patrolrecordMapper.findNowId();
            if (nowIds.size() > 0) {
                for (Integer nowId : nowIds) {
                    HashMap<String, Object> nowGps = patrolrecordMapper.findNowGps(nowId);
                    if (nowGps != null) {
                        String gps_x = (String) nowGps.get("gps_x");
                        String gps_y = (String) nowGps.get("gps_y");

                        Double x = Double.parseDouble(gps_x);
                        Double y = Double.parseDouble(gps_y);

                        boolean result = isInPolygon(x, y, lon, lat);
                        if (result) {
                            if (nowGps.get("station").toString().equals("固定岗")) {
                                gdLsit.add(nowGps);
                            } else if (nowGps.get("station").toString().equals("高峰岗")) {
                                gfLsit.add(nowGps);
                            } else if (nowGps.get("conment").toString().equals("3")) {
                                yxLsit.add(nowGps);
                            } else if (nowGps.get("conment").toString().equals("2")) {
                                tqLsit.add(nowGps);
                            } else if (nowGps.get("gridding") != null) {
                                if (nowGps.get("gridding").toString().contains("网格")) {
                                    wgLsit.add(nowGps);
                                }
                            } else if (nowGps.get("conment").toString().equals("4")) {
                                zdLsit.add(nowGps);
                            } else if (nowGps.get("station").toString().equals("高速岗")) {
                                gsLsit.add(nowGps);
                            } else if (nowGps.get("station").toString().equals("快速岗")) {
                                ksLsit.add(nowGps);
                            } else if (nowGps.get("station").toString().equals("特勤岗")) {
                                TQLsit.add(nowGps);
                            } else {
                                qtLsit.add(nowGps);
                            }

                        }
                    }

                }
            }
        }

        gdMap.put("data", gdLsit);
        gdMap.put("num", gdLsit.size());
        gfMap.put("data", gfLsit);
        gfMap.put("num", gfLsit.size());
        yxMap.put("data", yxLsit);
        yxMap.put("num", yxLsit.size());
        tqMap.put("data", tqLsit);
        tqMap.put("num", tqLsit.size());
        wgMap.put("data", wgLsit);
        wgMap.put("num", wgLsit.size());
        zdMap.put("data", zdLsit);
        zdMap.put("num", zdLsit.size());
        gsMap.put("data", gsLsit);
        gsMap.put("num", gsLsit.size());
        ksMap.put("data", ksLsit);
        ksMap.put("num", ksLsit.size());
        TQMap.put("data", TQLsit);
        TQMap.put("num", TQLsit.size());
        qtMap.put("data", qtLsit);
        qtMap.put("num", qtLsit.size());

        inNowByGps.add(gdMap);
        inNowByGps.add(gfMap);
        inNowByGps.add(yxMap);
        inNowByGps.add(tqMap);
        inNowByGps.add(wgMap);
        inNowByGps.add(zdMap);
        inNowByGps.add(gsMap);
        inNowByGps.add(ksMap);
        inNowByGps.add(TQMap);
        inNowByGps.add(qtMap);

        return inNowByGps;
    }


    //根据大队名称查询在人GPS
    @Override
    public List<HashMap> findNowStaffBySection(String time, String battalion, Integer type) {
        List<HashMap> staffBySectionList = new ArrayList<>();

        if (type == 1) {
            //高峰在线人ID
            List<Integer> gfList = patrolrecordMapper.findGFGPS(time, "高峰岗", battalion);
            for (Integer gfId : gfList) {
                HashMap<String, Object> gfStaffGps = patrolrecordMapper.findStaffGps(time, gfId);
                staffBySectionList.add(gfStaffGps);
            }

            //日常在线人ID
            List<Integer> rcList = patrolrecordMapper.findRcGPS(time, battalion);
            for (Integer rcId : rcList) {
                HashMap<String, Object> rcStaffGps = patrolrecordMapper.findStaffGps(time, rcId);
                staffBySectionList.add(rcStaffGps);
            }
        } else if (type == 2) {
            //日常在线人ID
            List<Integer> rcList = patrolrecordMapper.findRcGPS(time, battalion);
            for (Integer rcId : rcList) {
                HashMap<String, Object> rcStaffGps = patrolrecordMapper.findStaffGps(time, rcId);
                staffBySectionList.add(rcStaffGps);
            }
        } else if (type == 3) {
            //夜巡在线人ID
            List<Integer> yxList = patrolrecordMapper.findYXGPS(time, battalion);
            for (Integer yxId : yxList) {
                HashMap<String, Object> yxStaffGps = patrolrecordMapper.findStaffGps(time, yxId);
                staffBySectionList.add(yxStaffGps);
            }
        }

        return staffBySectionList;
    }

    //统计各大队在线、应到人数
    @Override
    public HashMap findStaffSum(String time, String battalion, Integer type) {
        HashMap staffSumMap = new HashMap();

        //高峰应到数
        List<HashMap> gfPeoples = xareaMapper.countYDSum("高峰岗", battalion, null);
        //网格+高速+铁骑+其他
        List<HashMap> rcPeoples = xareaMapper.countRcYDsumC2(battalion);
        //固定+重点
        List<HashMap> rcPeoples1 = xareaMapper.countRcYDsum(battalion);
        //夜巡
        List<HashMap> yxPeoples = xareaMapper.countYxSum("3", battalion, null);

        int gfYDSum = (gfPeoples.size()) + (rcPeoples.size()) + (rcPeoples1.size());
        int rcYDSum = (rcPeoples.size()) + (rcPeoples1.size());
        int yxYDSum = yxPeoples.size();

        if (type == 1) {
            //高峰在线人ID
            List<Integer> gfList = patrolrecordMapper.findGFGPS(time, "高峰岗", battalion);
            //日常在线人ID
            List<Integer> rcList = patrolrecordMapper.findRcGPS(time, battalion);
            int gfSDSum = (gfList.size()) + (rcList.size());

            staffSumMap.put("SDsum", gfSDSum);
            staffSumMap.put("YDsum", gfYDSum);

        } else if (type == 2) {
            //日常在线人ID
            List<Integer> rcList = patrolrecordMapper.findRcGPS(time, battalion);
            int rcSDSum = rcList.size();

            staffSumMap.put("SDsum", rcSDSum);
            staffSumMap.put("YDsum", rcYDSum);

        } else if (type == 3) {
            //夜巡在线人ID
            List<Integer> yxList = patrolrecordMapper.findYXGPS(time, battalion);
            int yxSDSum = yxList.size();

            staffSumMap.put("SDsum", yxSDSum);
            staffSumMap.put("YDsum", yxYDSum);

        }

        return staffSumMap;
    }

    /**
     * 判断是否在多边形区域内
     *
     * @param pointLon 要判断的点的纵坐标
     * @param pointLat 要判断的点的横坐标
     * @param lon      区域各顶点的纵坐标数组
     * @param lat      区域各顶点的横坐标数组
     * @return
     */
    public static boolean isInPolygon(double pointLon, double pointLat, double[] lon,
                                      double[] lat) {
        // 将要判断的横纵坐标组成一个点
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        // 将区域各顶点的横纵坐标放到一个点集合里面
        List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
        double polygonPoint_x = 0.0, polygonPoint_y = 0.0;
        for (int i = 0; i < lon.length; i++) {
            polygonPoint_x = lon[i];
            polygonPoint_y = lat[i];
            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }
        return check(point, pointList);
    }

    /**
     * 一个点是否在多边形内
     *
     * @param point   要判断的点的横纵坐标
     * @param polygon 组成的顶点坐标集合
     * @return
     */
    private static boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();

        Point2D.Double first = polygon.get(0);
        // 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
            peneralPath.lineTo(d.x, d.y);
        }
        // 将几何多边形封闭
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        // 测试指定的 Point2D 是否在 Shape 的边界内。
        return peneralPath.contains(point);
    }

    @Override
    public List<HashMap> findCircleByGps(double circleX, double circleY, double r) {

        //固定岗
        HashMap<String, Object> gdMap = new HashMap<>();
        gdMap.put("gangwei", "固定岗");
        //高峰岗
        HashMap<String, Object> gfMap = new HashMap<>();
        gfMap.put("gangwei", "高峰岗");
        //重点
        HashMap<String, Object> zdMap = new HashMap<>();
        zdMap.put("gangwei", "重点岗");
        //铁骑
        HashMap<String, Object> tqMap = new HashMap<>();
        tqMap.put("gangwei", "铁骑");
        //网格
        HashMap<String, Object> wgMap = new HashMap<>();
        wgMap.put("gangwei", "网格岗");
        //夜巡
        HashMap<String, Object> yxMap = new HashMap<>();
        yxMap.put("gangwei", "夜巡");
        //高速
        HashMap<String, Object> gsMap = new HashMap<>();
        gsMap.put("gangwei", "高速岗");
        //快速
        HashMap<String, Object> ksMap = new HashMap<>();
        ksMap.put("gangwei", "快速岗");
        //特勤
        HashMap<String, Object> TQMap = new HashMap<>();
        TQMap.put("gangwei", "特勤岗");
        //其他
        HashMap<String, Object> qtMap = new HashMap<>();
        qtMap.put("gangwei", "其他岗");

        //固定
        List<HashMap> gdLsit = new ArrayList<>();
        //高峰
        List<HashMap> gfLsit = new ArrayList<>();
        //重点
        List<HashMap> zdLsit = new ArrayList<>();
        //铁骑
        List<HashMap> tqLsit = new ArrayList<>();
        //网格
        List<HashMap> wgLsit = new ArrayList<>();
        //夜巡
        List<HashMap> yxLsit = new ArrayList<>();
        //高速
        List<HashMap> gsLsit = new ArrayList<>();
        //快速
        List<HashMap> ksLsit = new ArrayList<>();
        //特勤
        List<HashMap> TQLsit = new ArrayList<>();
        //其他
        List<HashMap> qtLsit = new ArrayList<>();

        List<HashMap> inNowByGps = new ArrayList<>();

        List<Integer> nowIds = patrolrecordMapper.findNowId();
        if (nowIds.size() > 0) {
            for (Integer nowId : nowIds) {
                HashMap<String, Object> nowGps = patrolrecordMapper.findNowGps(nowId);
                if (nowGps != null) {
                    String gps_x = (String) nowGps.get("gps_x");
                    String gps_y = (String) nowGps.get("gps_y");

                    Double x = Double.parseDouble(gps_x);
                    Double y = Double.parseDouble(gps_y);

                    boolean result = distencePC(x, y, circleX, circleY, r);
                    if (result) {
                        if (nowGps.get("station").toString().equals("固定岗")) {
                            gdLsit.add(nowGps);
                        } else if (nowGps.get("station").toString().equals("高峰岗")) {
                            gfLsit.add(nowGps);
                        } else if (nowGps.get("conment").toString().equals("3")) {
                            yxLsit.add(nowGps);
                        } else if (nowGps.get("conment").toString().equals("2")) {
                            tqLsit.add(nowGps);
                        } else if (nowGps.get("gridding") != null) {
                            if (nowGps.get("gridding").toString().contains("网格")) {
                                wgLsit.add(nowGps);
                            }
                        } else if (nowGps.get("conment").toString().equals("4")) {
                            zdLsit.add(nowGps);
                        } else if (nowGps.get("station").toString().equals("高速岗")) {
                            gsLsit.add(nowGps);
                        } else if (nowGps.get("station").toString().equals("快速岗")) {
                            ksLsit.add(nowGps);
                        } else if (nowGps.get("station").toString().equals("特勤岗")) {
                            TQLsit.add(nowGps);
                        } else {
                            qtLsit.add(nowGps);
                        }

                    }
                }
            }
        }

        gdMap.put("data", gdLsit);
        gdMap.put("num", gdLsit.size());
        gfMap.put("data", gfLsit);
        gfMap.put("num", gfLsit.size());
        yxMap.put("data", yxLsit);
        yxMap.put("num", yxLsit.size());
        tqMap.put("data", tqLsit);
        tqMap.put("num", tqLsit.size());
        wgMap.put("data", wgLsit);
        wgMap.put("num", wgLsit.size());
        zdMap.put("data", zdLsit);
        zdMap.put("num", zdLsit.size());
        gsMap.put("data", gsLsit);
        gsMap.put("num", gsLsit.size());
        ksMap.put("data", ksLsit);
        ksMap.put("num", ksLsit.size());
        TQMap.put("data", TQLsit);
        TQMap.put("num", TQLsit.size());
        qtMap.put("data", qtLsit);
        qtMap.put("num", qtLsit.size());

        inNowByGps.add(gdMap);
        inNowByGps.add(gfMap);
        inNowByGps.add(yxMap);
        inNowByGps.add(tqMap);
        inNowByGps.add(wgMap);
        inNowByGps.add(zdMap);
        inNowByGps.add(gsMap);
        inNowByGps.add(ksMap);
        inNowByGps.add(TQMap);
        inNowByGps.add(qtMap);

        return inNowByGps;
    }

    @Override
    public ResultBean selectinfoByid(Integer id) {
        HashMap patrolrecord = patrolrecordMapper.selectinfoByid(id);
        if (patrolrecord != null) {
            return ResultUtil.setOK("success", patrolrecord);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }


    /**
     * 将两个经纬度坐标转化成距离（米）
     *
     * @param X       维度
     * @param Y       经度
     * @param circleX 中心点维度
     * @param circleY 中心点经度
     * @param r       半径
     */
    public boolean distencePC(double X, double Y, double circleX, double circleY, double r) {
        double a = X * Math.PI / 180.0 - circleX * Math.PI / 180.0;
        double b = Y * Math.PI / 180.0 - circleY * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(X * Math.PI / 180.0)
                * Math.cos(circleX * Math.PI / 180.0)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137 * 1000;
        s = Math.round(s);
        if (s > r) {
            return false;
        }
        return true;
    }

    //热力图统计
    @Override
    public List<HashMap> heatMap() {
        List<HashMap> typeGPSList = patrolrecordMapper.findByType();

        List<HashMap> resultList = new ArrayList<>();
        List<HashMap> nowGpsList = new ArrayList<>();
        List<Integer> nowIds = patrolrecordMapper.findNowId();
        for (Integer nowId : nowIds) {
            HashMap<String, Object> nowGps = patrolrecordMapper.findNowGps(nowId);
            nowGpsList.add(nowGps);
        }


        for (HashMap map : typeGPSList) {
            String gps = map.get("gps").toString();
            String[] split = gps.split(",");
            int num = 0;

            List<String> lonList = new ArrayList<>();//经度集合
            List<String> latList = new ArrayList<>();//维度集合

            for (int i = 1; i <= split.length; i++) {
                String s = split[i - 1];
                if (i % 2 == 0) {
                    //偶数
                    latList.add(s);
                } else {
                    lonList.add(s);
                }
            }

            double[] lonArr = new double[lonList.size()];
            double[] latArr = new double[latList.size()];
            for (int j = 0; j < lonList.size(); j++) {
                Double aa = Double.parseDouble(lonList.get(j));
                lonArr[j] = aa;
            }

            for (int k = 0; k < latList.size(); k++) {
                Double aa = Double.parseDouble(latList.get(k));
                latArr[k] = aa;
            }


            if (nowIds.size() > 0) {
                for (int l = 0; l < nowGpsList.size(); l++) {
                    if (nowGpsList.get(l) != null) {
                        String gps_x = (String) nowGpsList.get(l).get("gps_x");
                        String gps_y = (String) nowGpsList.get(l).get("gps_y");

                        Double x = Double.parseDouble(gps_x);
                        Double y = Double.parseDouble(gps_y);

                        boolean result = isInPolygon(x, y, lonArr, latArr);
                        if (result) {
                            num++;
                            nowGpsList.remove(l);
                        }
                    }

                }
            }
            String centre = map.get("centre").toString();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("centre", centre);
            resultMap.put("num", num);

            resultList.add(resultMap);
        }

        return resultList;
    }

    //热力图统计
    @Override
    public List<HashMap> heatMapYD() {
        //查询网格
        List<HashMap> typeGPSList = patrolrecordMapper.findByType();

        List<HashMap> dianList = patrolrecordMapper.findTypeGps("点");
        List<HashMap> xianList = patrolrecordMapper.findTypeGps("线");
        List<HashMap> mianList = patrolrecordMapper.findTypeGps("面");

        List<HashMap> resultList = new ArrayList<>();

        int sum = 0;

        for (HashMap map : typeGPSList) {
            String gps = map.get("gps").toString();
            String[] split = gps.split(",");
            int num = 0;

            List<String> lonList = new ArrayList<>();//经度集合
            List<String> latList = new ArrayList<>();//维度集合

            for (int i = 1; i <= split.length; i++) {
                String s = split[i - 1];
                if (i % 2 == 0) {
                    //偶数
                    latList.add(s);
                } else {
                    lonList.add(s);
                }
            }

            double[] lonArr = new double[lonList.size()];
            double[] latArr = new double[latList.size()];
            for (int j = 0; j < lonList.size(); j++) {
                Double aa = Double.parseDouble(lonList.get(j));
                lonArr[j] = aa;
            }

            for (int k = 0; k < latList.size(); k++) {
                Double aa = Double.parseDouble(latList.get(k));
                latArr[k] = aa;
            }

            //点
            for (int a = 0; a < dianList.size(); a++) {
                String gps_x = dianList.get(a).get("gps").toString().split(",")[0];
                String gps_y = dianList.get(a).get("gps").toString().split(",")[1];

                Double x = Double.parseDouble(gps_x);
                Double y = Double.parseDouble(gps_y);

                boolean result = isInPolygon(x, y, lonArr, latArr);
                if (result) {
                    num++;
                    dianList.remove(a);
                }
            }

            //线
            for (int b = 0; b < xianList.size(); b++) {
                if (!StringUtils.isEmpty(xianList.get(b).get("centre"))) {
                    String gps_x = xianList.get(b).get("centre").toString().split(",")[0];
                    String gps_y = xianList.get(b).get("centre").toString().split(",")[1];

                    Double x = Double.parseDouble(gps_x);
                    Double y = Double.parseDouble(gps_y);

                    boolean result = isInPolygon(x, y, lonArr, latArr);
                    if (result) {
                        num++;
                        xianList.remove(b);
                    }
                } else {
                    String gps_x = xianList.get(b).get("gps").toString().split(",")[0];
                    String gps_y = xianList.get(b).get("gps").toString().split(",")[1];

                    Double x = Double.parseDouble(gps_x);
                    Double y = Double.parseDouble(gps_y);

                    boolean result = isInPolygon(x, y, lonArr, latArr);
                    if (result) {
                        num++;
                        xianList.remove(b);
                    }
                }

            }

            //面
            for (int c = 0; c < mianList.size(); c++) {
                if (!StringUtils.isEmpty(mianList.get(c).get("centre"))) {
                    String gps_x = mianList.get(c).get("centre").toString().split(",")[0];
                    String gps_y = mianList.get(c).get("centre").toString().split(",")[1];

                    Double x = Double.parseDouble(gps_x);
                    Double y = Double.parseDouble(gps_y);

                    boolean result = isInPolygon(x, y, lonArr, latArr);
                    if (result) {
                        num++;
                        mianList.remove(c);
                    }
                } else {
                    String gps_x = mianList.get(c).get("gps").toString().split(",")[0];
                    String gps_y = mianList.get(c).get("gps").toString().split(",")[1];

                    Double x = Double.parseDouble(gps_x);
                    Double y = Double.parseDouble(gps_y);

                    boolean result = isInPolygon(x, y, lonArr, latArr);
                    if (result) {
                        num++;
                        mianList.remove(c);
                    }
                }

            }




            String centre = map.get("centre").toString();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("centre", centre);

            resultMap.put("num", num);

            resultList.add(resultMap);

            sum += num;
        }
        return resultList;
    }
}

