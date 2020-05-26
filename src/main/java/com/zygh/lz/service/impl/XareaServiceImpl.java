package com.zygh.lz.service.impl;

import com.zygh.lz.admin.*;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.*;
import com.zygh.lz.service.xareaService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XareaServiceImpl implements xareaService {
    @Autowired
    private XareaMapper xareaMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private PatrolrecordMapper patrolrecordMapper;
    @Autowired
    private GpsMapper gpsMapper;
    @Autowired
    private XrelationMapper xrelationMapper;

    /**
     * 根据大队，中队，岗位，中队领导
     *
     * @param battalion
     * @param detachment
     * @param station
     * @param leader
     * @return
     */
    @Override
    public ResultBean selectXareabycondition(String battalion, String detachment, String station, String leader, String grid, String type) {
        //查询出来的区域
        List<Xarea> xareas = xareaMapper.selectXareabycondition(battalion, detachment, station, leader, grid, type);
        List<Staff> zg = new ArrayList<>();
        List<Patrolrecord> ydr = new ArrayList<>();
        if (xareas.size() == 0) {
            return ResultUtil.setError(SystemCon.RERROR1, "error", null);
        }
        int conment = 0;
        int num = 0;
        List<Staff> staff = null;
        //查询该区域的所有民警
        for (int i = 0; i < xareas.size(); i++) {
            //区域内应在岗人数
            staff = staffMapper.selectStaffByxarea(xareas.get(i).getId());
            for (int k = 0; k < staff.size(); k++) {
                //实际在岗人数
                Patrolrecord patrolrecords = patrolrecordMapper.selectPatrolrecordByStaffId(staff.get(k).getSysStaffId());
                //不为空说明在岗
                if (patrolrecords != null) {
                    if (staff.get(k).getSysStaffId() == patrolrecords.getSysStaffId()) {
                        staff.get(k).setStaffOnline("1");
                        //获取gps点
                        Gps gps = gpsMapper.gpsEnd(staff.get(k).getSysStaffId());
                        num++;
                        staff.get(k).setGps(gps);
                    }

                }


            }
            conment += staff.size();
            xareas.get(i).setStaff(staff);

        }
        return ResultUtil.setOK("success", xareas);
    }

    /**
     * 新增区域，同时新增区域与人员的关系表
     *
     * @param xarea
     * @return
     */
    @Override
    public ResultBean insertXarea(Xarea xarea, Integer staffId) {
        int i = xareaMapper.insertSelective(xarea);
        if (i > 0) {
            Xarea xarea1 = xareaMapper.selectXareaEnd();
            Xrelation xrelation = new Xrelation();
            xrelation.setStaffId(staffId);
            xrelation.setXareaId(xarea1.getId());
            return ResultUtil.execOp(xrelationMapper.insertSelective(xrelation), "新增");
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    @Override
    public ResultBean updateXarea(Xarea xarea) {
        return ResultUtil.execOp(xareaMapper.updateByPrimaryKeySelective(xarea), "修改");
    }

    @Override
    public ResultBean deleteXarea(Integer id) {
        int i = xareaMapper.deleteByPrimaryKey(id);
        if(i>0){
            xrelationMapper.deleteByPrimaryKey(id);
            return ResultUtil.execOp(i, "删除");
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    @Override
    public ResultBean selectXareaByName(String name,String battalion,String detachment) {
        List<Xarea> xareas = xareaMapper.selectXareaByName(name,battalion,detachment);
        if (xareas.size() >= 0) {
            return ResultUtil.setOK("success", xareas);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    @Override
    public ResultBean selectXareaAll() {
        return ResultUtil.setOK("success", xareaMapper.selectXareaAll());
    }


    /**
     * 在线人数
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean selectonlineNumber(Integer id) {
        Map<String, Object> map = new HashMap<>();
        //根据id查询区域
        Xarea xarea = xareaMapper.selectByPrimaryKey(id);
        //查询区域里的应在岗民警
        List<Staff> staff = staffMapper.selectStaffByxarea(xarea.getId());
        //查询区域里实在岗人数
        List<Patrolrecord> online = new ArrayList<>();
        for (int k = 0; k < staff.size(); k++) {
            //实际在岗人数
            Patrolrecord patrolrecords = patrolrecordMapper.selectPatrolrecordByStaffId(staff.get(k).getSysStaffId());
            if (patrolrecords != null) {
                //获取gps点
                Gps gps = gpsMapper.gpsEnd(staff.get(k).getSysStaffId());
                online.add(patrolrecords);
                patrolrecords.setGpsdw(gps);
            }
        }
        map.put("yz", staff);
        map.put("sz", online);
        return ResultUtil.setOK("success", map);
    }

    //按大队显示所有警力
    @Override
    public ResultBean selectpolice() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        Map<String, Object> map5 = new HashMap<>();
        Map<String, Object> map6 = new HashMap<>();
        Map<String, Object> map7 = new HashMap<>();


        List<Staff> staff1 = staffMapper.selectStaffYdByAll(null, 25, null);

        List<Staff> staffsd1 = staffMapper.selectStaffByzg(null, null, "一大队");
        List<Staff> staff2 = staffMapper.selectStaffYdByAll(null, 26, null);
        List<Staff> staffsd2 = staffMapper.selectStaffByzg(null, null, "二大队");
        List<Staff> staff3 = staffMapper.selectStaffYdByAll(null, 27, null);
        List<Staff> staffsd3 = staffMapper.selectStaffByzg(null, null, "三大队");
        List<Staff> staff4 = staffMapper.selectStaffYdByAll(null, 28, null);
        List<Staff> staffsd4 = staffMapper.selectStaffByzg(null, null, "四大队");
        List<Staff> staff5 = staffMapper.selectStaffYdByAll(null, 29, null);
        List<Staff> staffsd5 = staffMapper.selectStaffByzg(null, null, "五大队");
        List<Staff> staff6 = staffMapper.selectStaffYdByAll(null, 30, null);
        List<Staff> staffsd6 = staffMapper.selectStaffByzg(null, null, "六大队");

        map1.put("id", 25);
        map1.put("name", "一大队");
        map1.put("ydnum", staff1.size());
        map1.put("sdnum", staffsd1.size());
        map2.put("id", 26);
        map2.put("name", "二大队");
        map2.put("ydnum", staff2.size());
        map2.put("sdnum", staffsd2.size());
        map3.put("id", 27);
        map3.put("name", "三大队");
        map3.put("ydnum", staff3.size());
        map3.put("sdnum", staffsd3.size());
        map4.put("id", 28);
        map4.put("name", "四大队");
        map4.put("ydnum", staff4.size());
        map4.put("sdnum", staffsd4.size());
        map5.put("id", 29);
        map5.put("name", "五大队");
        map5.put("ydnum", staff5.size());
        map5.put("sdnum", staffsd5.size());
        map6.put("id", 30);
        map6.put("name", "六大队");
        map6.put("ydnum", staff6.size());
        map6.put("sdnum", staffsd6.size());
        map7.put("id", 222);
        map7.put("name", "港区大队");
        map7.put("ydnum", 0);
        map7.put("sdnum", 0);

        List<Object> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        list.add(map7);
        return ResultUtil.setOK("success", list);
    }

    @Override
    public ResultBean selectks() {
        List<Xarea> selectks = xareaMapper.selectks();
        if (selectks.size() >= 0) {
            return ResultUtil.setOK("success", selectks);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    @Override
    public ResultBean selectqt(String conment) {
        //查询出来的区域
        List<Xarea> xareas = xareaMapper.selectqt(conment);
        List<Staff> zg = new ArrayList<>();
        List<Patrolrecord> ydr = new ArrayList<>();
        if (xareas.size() == 0) {
            return ResultUtil.setError(SystemCon.RERROR1, "error", null);
        }
        int conments = 0;
        int num = 0;
        List<Staff> staff = null;
        //查询该区域的所有民警
        for (int i = 0; i < xareas.size(); i++) {
            //区域内应在岗人数
            staff = staffMapper.selectStaffByxarea(xareas.get(i).getId());
            for (int k = 0; k < staff.size(); k++) {
                //实际在岗人数
                Patrolrecord patrolrecords = patrolrecordMapper.selectPatrolrecordByStaffId(staff.get(k).getSysStaffId());

                if (patrolrecords != null) {
                    if (staff.get(k).getSysStaffId() == patrolrecords.getSysStaffId()) {
                        staff.get(k).setStaffOnline("1");
                        //获取gps点
                        Gps gps = gpsMapper.gpsEnd(staff.get(k).getSysStaffId());
                        num++;
                        staff.get(k).setGps(gps);
                    }

                }
            }
            conments += staff.size();
            xareas.get(i).setStaff(staff);
        }
        return ResultUtil.setOK("success", xareas);
    }

    /**
     * 日间警力部署
     * 固定岗每个大队几个人查询
     *
     * @return
     */
    @Override
    public ResultBean selectfixationRJ(String station) {
        if (station.equals("固定岗")) {
            //固定岗每个大队几个人查询
            List<HashMap> list = xareaMapper.selectfixationRJ(station);

            if (list.size() >= 0) {

                for (int i = 0; i < list.size(); i++) {
                    String sectionName = list.get(i).get("sectionName").toString();
                    if (sectionName.indexOf("大队") != -1) {
                        String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                        list.get(i).put("sectionName", sectionName1);
                    }
                    //查询岗位数
                    int i1 = xareaMapper.countggZSum(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                    list.get(i).put("number", i1);
                    //辅警各个大队固定岗人数
                    Integer fjddnum = xareaMapper.selectAssistPolice("固定岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", null, null).size();
                    list.get(i).put("fjnum", fjddnum);
                    //细分到中队
                    List<HashMap> hashMaps1 = xareaMapper.selectfixationzdRJ(station, list.get(i).get("sectionName").toString());
                    for (int k = 0; k < hashMaps1.size(); k++) {
                        List<HashMap> hashMaps = new ArrayList<>();
                        String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                        String substring = hashMaps1.get(k).get("sectionName").toString().substring(sectionName.indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                        hashMaps = xareaMapper.countYDSum(station, dadui, substring);
                        /*for (int t = 0; t < hashMaps.size(); t++) {
                            if (!hashMaps.get(t).get("section_name").equals(hashMaps1.get(k).get("sectionName"))) {
                                hashMaps.remove(t);
                                //删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                                // 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完
                                t--;
                            }
                        }*/
                        int num = xareaMapper.countggZSum(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                        //辅警各个中队人数
                        Integer fjzdnum = xareaMapper.selectAssistPolice("固定岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", null, null).size();
                        hashMaps1.get(k).put("fjnum", fjzdnum);
                        hashMaps1.get(k).put("number", num);
                        hashMaps1.get(k).put("detachment", hashMaps);
                    }
                    list.get(i).put("detachment", hashMaps1);

                }

                return ResultUtil.setOK("success", list);
            }

            return ResultUtil.setError(SystemCon.RERROR1, "error", null);
        } else {//高峰岗
            //固定岗每个大队几个人查询
            List<HashMap> list = xareaMapper.selectfixationRJ(station);
            if (list.size() >= 0) {

                for (int i = 0; i < list.size(); i++) {
                    String sectionName = list.get(i).get("sectionName").toString();
                    if (sectionName.indexOf("大队") != -1) {
                        String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                        list.get(i).put("sectionName", sectionName1);
                    }
                    //高峰岗部署警力一岗一人
                    List<Xarea> xareas = xareaMapper.selectXareapolic(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                    list.get(i).put("count", xareas.size());
                    //查询岗位数
                    int i1 = xareaMapper.countggZSum(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                    list.get(i).put("number", i1);
                    //辅警高峰岗各大队人数
                    Integer fjzdnum = xareaMapper.selectAssistPolice("高峰岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", null, null).size();
                    list.get(i).put("fjnum", fjzdnum);
                    //细分到中队
                    List<HashMap> hashMaps1 = xareaMapper.selectfixationzdRJ(station, list.get(i).get("sectionName").toString());
                    for (int k = 0; k < hashMaps1.size(); k++) {
                        String substring = hashMaps1.get(k).get("sectionName").toString().substring(sectionName.indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                        //高峰岗部署警力一岗一人
                        List<Xarea> xareaes = xareaMapper.selectXareapolic(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                       /* list.get(i).put("count",xareas.size());
                        Integer count =Integer.valueOf(hashMaps1.get(k).get("count").toString());
                        int cc = (int)Math.ceil(count/2)+1;//向上*/
                        hashMaps1.get(k).put("count", xareaes.size());
                        List<HashMap> hashMaps = new ArrayList<>();
                        String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                        //各个中队得部署警力
                        int num = xareaMapper.countggZSum(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                        hashMaps1.get(k).put("number", num);
                        hashMaps = xareaMapper.countYDSum(station, dadui, substring);
                        for (int t = 0; t < hashMaps.size(); t++) {
                            if (!hashMaps.get(t).get("section_name").equals(hashMaps1.get(k).get("sectionName")) || hashMaps1.get(k).get("count") == null) {
                                hashMaps.remove(t);
                                //删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                                // 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完
                                t--;
                            }
                        }
                        //辅警高峰岗各中队人数
                        Integer fjgzdnum = xareaMapper.selectAssistPolice("高峰岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", null, null).size();
                        hashMaps1.get(k).put("detachment", hashMaps);
                        hashMaps1.get(k).put("fjnum", fjgzdnum);
                    }
                    for (int j = 0; j < hashMaps1.size(); j++) {
                        //大队名称是否一致
                        String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                        if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                            hashMaps1.remove(j);
                            j--;
                        }
                    }
                    for (int j = 0; j < hashMaps1.size(); j++) {
                        Object detachment = hashMaps1.get(j).get("detachment");
                        if (detachment == null) {
                            hashMaps1.get(j).remove("sectionName");
                            hashMaps1.get(j).remove("count");
                        }
                    }
                    list.get(i).put("detachment", hashMaps1);

                }

                return ResultUtil.setOK("success", list);
            }

            return ResultUtil.setError(SystemCon.RERROR1, "error", null);
        }
    }

    /**
     * 重点单位岗
     *
     * @return
     */
    @Override
    public ResultBean selectemphasisRJ() {
        //重点机关岗各个大队人数
        List<HashMap> list = xareaMapper.selectemphasisRJ();
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                int i1 = xareaMapper.countZdZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", i1);
                //辅警高峰岗各大队人数
                Integer fjddnum = xareaMapper.selectAssistPolice("重点机关岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", null, null).size();
                list.get(i).put("fjnum", fjddnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectemphasisZd();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(sectionName.indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectemphasisPope(dadui, substring);
                    for (int t = 0; t < hashMaps.size(); t++) {
                        if (!hashMaps.get(t).get("section_name").equals(hashMaps1.get(k).get("sectionName"))) {
                            hashMaps.remove(t);
                            //删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                            // 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完
                            t--;
                        }
                    }
                    int num = xareaMapper.countZdZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    //辅警高峰岗各中队人数
                    Integer fjzdnum = xareaMapper.selectAssistPolice("重点机关岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", null, null).size();
                    hashMaps1.get(k).put("number", num);
                    hashMaps1.get(k).put("fjnum", fjzdnum);
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                }
                //中队详情添加到大队里
                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 日间铁骑
     *
     * @return
     */
    @Override
    public ResultBean selectcavalryRJ() {
        //铁骑
        List<HashMap> list = xareaMapper.selectcavalryRJ();
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                List<Integer> integers = xareaMapper.countTqZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", integers.size());
                //辅警铁骑各大队人数
                Integer tqddnum = xareaMapper.selectAssistPolice(null, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", "铁骑", "2").size();
                list.get(i).put("fjnum", tqddnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectcavalryzdRJ();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    List<HashMap> hashMaps = new ArrayList<>();
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    hashMaps = xareaMapper.countYxSum("2", dadui, substring);
                    for (int t = 0; t < hashMaps.size(); t++) {
                        if (!hashMaps.get(t).get("section_name").equals(hashMaps1.get(k).get("sectionName"))) {
                            hashMaps.remove(t);
                            //删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                            // 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完
                            t--;
                        }
                    }
                    List<Integer> num = xareaMapper.countTqZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    hashMaps1.get(k).put("number", num.size());
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    //辅警铁骑各中队人数
                    Integer tqzdnum = xareaMapper.selectAssistPolice(null, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", "铁骑", "2").size();
                    list.get(i).put("fjnum", tqzdnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {

                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }

                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 日间网格
     *
     * @return
     */
    @Override
    public ResultBean selectgriddingRJ() {
        //网格
        List<HashMap> list = xareaMapper.selectgriddingRJ();
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                List<String> strings = xareaMapper.countWgZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", strings.size());
                //辅警网格各大队
                Integer wgddnum = xareaMapper.selectAssistPolice("区域", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", "网格", null).size();
                list.get(i).put("fjnum", wgddnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectgriddingZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectgriddingPope(dadui, substring);
                    List<String> num = xareaMapper.countWgZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    hashMaps1.get(k).put("number", num.size());
                    //辅警网格各中队
                    Integer wgzdnum = xareaMapper.selectAssistPolice("区域", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", "网格", null).size();
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    hashMaps1.get(k).put("fjnum", wgzdnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }

                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 日间高速岗
     *
     * @param station
     * @return
     */
    @Override
    public ResultBean selectexpresswayRJ(String station) {
        //高速
        List<HashMap> list = xareaMapper.selectexpresswayRJ("高速岗");
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                /*Integer countlist =Integer.valueOf(list.get(i).get("count").toString());
                int cc = (int)Math.ceil(countlist/2);//向上
                list.get(i).put("count",cc);*/
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                List<HashMap> strings = xareaMapper.countGsOrKsZSum(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", strings.size());
                //辅警高速各大队
                Integer xngsnum = xareaMapper.selectAssistPolice("高速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", null, null).size();
                list.get(i).put("fjnum", xngsnum);

                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectexpresswayZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    List<HashMap> hashMaps = new ArrayList<>();
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    hashMaps = xareaMapper.selectexpresswayPope("高速岗", dadui, null, substring);
                    for (int t = 0; t < hashMaps.size(); t++) {
                        if (!hashMaps.get(t).get("section_name").equals(hashMaps1.get(k).get("sectionName"))) {
                            hashMaps.remove(t);
                            //删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                            // 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完
                            t--;
                        }
                    }
                    List<HashMap> num = xareaMapper.countGsOrKsZSum(station, list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    hashMaps1.get(k).put("number", num.size());
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    //辅警高速各中队
                    Integer gszdnum = xareaMapper.selectAssistPolice("高速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", null, null).size();
                    hashMaps1.get(k).put("fjnum", gszdnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {

                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }

                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 日间其他警力部署
     *
     * @return
     */
    @Override
    public ResultBean selectqtRJ() {
        //其他
        List<HashMap> list = xareaMapper.selectqtRJ("民警");
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                //岗位数
                int i1 = xareaMapper.countQtZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, null);
                list.get(i).put("number", i1);
                Integer fjddnum = xareaMapper.selectqtPope(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警").size();
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectqtZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectqtPope(dadui, substring, "民警");
                    //岗位数
                    int num = xareaMapper.countQtZSum(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, substring);
                    hashMaps1.get(k).put("number", num);
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    Integer fjnum = xareaMapper.selectqtPope(dadui, substring, "辅警").size();
                    hashMaps1.get(k).put("fjnum", fjnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }
                list.get(i).put("detachment", hashMaps1);

            }
            return ResultUtil.setOK("success", list);

        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 全部在线人警力部署
     *
     * @return
     */
    @Override
    public ResultBean selectInformant() {
        //全部在线人
        List<HashMap> list = xareaMapper.selectInformant();
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectInformantZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectInformantPope(dadui, substring);
                    //详情添加到中队里
                    if (dadui.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.get(k).put("detachment", hashMaps);
                    }

                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    Object detachment = hashMaps1.get(j).get("detachment");
                    if (detachment == null) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }

                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 夜间夜巡
     *
     * @return
     */
    @Override
    public ResultBean selectNightTour() {
        //夜间夜巡
        List<HashMap> list = xareaMapper.selectNightTour();
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                List<Integer> integers = xareaMapper.countYxZ(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", integers.size());
                //辅警夜巡
                Integer yxnum = xareaMapper.selectAssistPolice("区域", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", "夜巡", "3").size();
                list.get(i).put("fjnum", yxnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectNightTourZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectNightTourPope(dadui, substring);
                    for (int t = 0; t < hashMaps.size(); t++) {
                        if (!hashMaps.get(t).get("section_name").equals(hashMaps1.get(k).get("sectionName"))) {
                            hashMaps.remove(t);
                            //删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                            // 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完
                            t--;
                        }
                    }
                    List<Integer> num = xareaMapper.countYxZ(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    hashMaps1.get(k).put("number", num.size());
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    //辅警夜巡
                    Integer yxzdnum = xareaMapper.selectAssistPolice("区域", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", "夜巡", "3").size();
                    hashMaps1.get(k).put("fjnum", yxzdnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1)) || hashMaps1.get(j).get("detachment") == null) {
                        hashMaps1.remove(j);
                        j--;
                    }

                }
                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 夜间快速警力部署
     *
     * @param station
     * @return
     */
    @Override
    public ResultBean selectcelerity(String station) {
        //夜间快速警力部署
        List<HashMap> list = xareaMapper.selectcelerity(station);
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                List<Integer> integers = xareaMapper.countKsZ("快速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", integers.size());
                //辅警
                Integer ksgnum = xareaMapper.selectAssistPolice("快速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", null, null).size();
                list.get(i).put("fjnum", ksgnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectcelerityPopeZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString();
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectcelerityPope(station, dadui, null);
                    List<Integer> num = xareaMapper.countKsZ("快速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    hashMaps1.get(k).put("number", num.size());
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    //辅警
                    Integer kszdgnum = xareaMapper.selectAssistPolice("快速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", null, null).size();
                    hashMaps1.get(k).put("fjnum", kszdgnum);
                }
                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 夜间高速警力部署
     *
     * @param station
     * @return
     */
    @Override
    public ResultBean selectceleritygs(String station) {
        //夜间高速警力部署
        List<HashMap> list = xareaMapper.selectceleritygs(station);
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                List<Integer> integers = xareaMapper.countGsOrKsZ("高速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null);
                list.get(i).put("number", integers.size());
                //辅警高速
                Integer fjgsnum = xareaMapper.selectAssistPolice("高速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警", null, null).size();
                list.get(i).put("fjnum", fjgsnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectceleritygsPopeZD();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectceleritygsPope(station, dadui, substring);
                    List<Integer> num = xareaMapper.countGsOrKsZ("高速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring);
                    hashMaps1.get(k).put("number", num.size());
                    //详情添加到中队里
                    hashMaps1.get(k).put("detachment", hashMaps);
                    //辅警高速
                    Integer fjzdnum = xareaMapper.selectAssistPolice("高速岗", list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), substring, "辅警", null, null).size();
                    hashMaps1.get(k).put("fjnum", fjzdnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    String detachment = hashMaps1.get(j).get("sectionName").toString().substring(0, hashMaps1.get(j).get("sectionName").toString().indexOf("队") + 1);
                    if (!detachment.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1)) || hashMaps1.get(j).get("detachment") == null) {
                        hashMaps1.remove(j);
                        j--;
                    }

                }
                list.get(i).put("detachment", hashMaps1);

            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 夜间其他警力部署
     *
     * @return
     */
    @Override
    public ResultBean selectqita() {
        //夜间其他警力部署
        List<HashMap> list = xareaMapper.selectqita();
        if (list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                String sectionName = list.get(i).get("sectionName").toString();
                if (sectionName.indexOf("大队") != -1) {
                    String sectionName1 = list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1);
                    list.get(i).put("sectionName", sectionName1);
                }
                Integer fjddnum = xareaMapper.selectqitaPope(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1), null, "辅警").size();
                list.get(i).put("fjnum", fjddnum);
                //各个中队人数
                List<HashMap> hashMaps1 = xareaMapper.selectqita();
                for (int k = 0; k < hashMaps1.size(); k++) {
                    String dadui = hashMaps1.get(k).get("sectionName").toString().substring(0, hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1);
                    String substring = hashMaps1.get(k).get("sectionName").toString().substring(hashMaps1.get(k).get("sectionName").toString().indexOf("队") + 1, hashMaps1.get(k).get("sectionName").toString().length());
                    List<HashMap> hashMaps = xareaMapper.selectqitaPope(dadui, substring, "民警");
                    Integer fjzdnum = xareaMapper.selectqitaPope(dadui, substring, "辅警").size();
                    //详情添加到中队里
                    if (dadui.equals(list.get(i).get("sectionName").toString().substring(0, sectionName.indexOf("队") + 1))) {
                        hashMaps1.get(k).put("detachment", hashMaps);
                    }
                    hashMaps1.get(k).put("fjnum", fjzdnum);
                }
                for (int j = 0; j < hashMaps1.size(); j++) {
                    Object detachment = hashMaps1.get(j).get("detachment");
                    if (detachment == null) {
                        hashMaps1.remove(j);
                        j--;
                    }
                }
                list.get(i).put("detachment", hashMaps1);
            }

            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 九主六块十六示范区
     *
     * @param station
     * @return
     */
    @Override
    public ResultBean selectDemonstrationPlot(String station) {
        List<Xarea> xareas = xareaMapper.selectDemonstrationPlot(station);
        if (xareas.size() >= 0) {
            return ResultUtil.setOK("success", xareas);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);

    }

    /**
     * 根据区域名字模糊匹配部署警力
     *
     * @param name
     * @return
     */
    @Override
    public ResultBean selctStrength(String name) {
        List<HashMap> hashMaps = xareaMapper.selctStrength(name, null);
        if (hashMaps.size() >= 0) {
            return ResultUtil.setOK("success", hashMaps);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }

    /**
     * 九主六块多有人
     *
     * @return
     */
    @Override
    public ResultBean selectAllByDemonstration(String station) {
        //根据九主六块查出的区域
        List<Xarea> xareas = xareaMapper.selectDemonstrationPlot(station);
        List<String> ddNames = xareaMapper.findDd();
        List<HashMap> qyList = new ArrayList<>();
        for (Xarea xarea : xareas) {
            HashMap<String, Object> qyMap = new HashMap<>();
            List<HashMap> ddList = new ArrayList<>();
            for (String ddName : ddNames) {
                HashMap<String, Object> ddMap = new HashMap<>();
                ddMap.put("sectionName", ddName);
                //根据区域名字模糊匹配部署警力
                List<HashMap> list = xareaMapper.selctStrength(xarea.getGridding(), ddName);
                ddMap.put("count", list.size());
                ddMap.put("detachment", list);
                ddList.add(ddMap);
            }

            qyMap.put("ddList", ddList);
            qyMap.put("qyData", xarea.getGridding());
            qyList.add(qyMap);

        }


        return ResultUtil.setOK("success", qyList);
    }

    /**
     * 对接市局接口
     * 根据不同条件查询标注信息
     * @param xarea
     * @return
     */
    @Override
    public ResultBean selectXareaByInfo(Xarea xarea) {
        return ResultUtil.setOK("success", xareaMapper.selectXareaByInfo(xarea));
    }

    @Override
    public ResultBean selectPoliceNumber(Xarea xarea) {
        return ResultUtil.setOK("success", xareaMapper.selectPoliceNumber(xarea));
    }

    //根据区域查询相应警力
    @Override
    public ResultBean selctStrengthById(Xarea xarea) {
        return ResultUtil.setOK("success", xareaMapper.selctStrengthById(xarea));
    }
    /**
     * 任务组信息
     *
     * @return
     */
    @Override
    public ResultBean selectTaskSetInfo() {
        return ResultUtil.setOK("success", xareaMapper.selectTaskSetInfo());
    }

    /**
     * 任务信息
     *
     * @return
     */
    @Override
    public ResultBean selectTaskInfo() {
        return ResultUtil.setOK("success", xareaMapper.selectTaskInfo());
    }
}
