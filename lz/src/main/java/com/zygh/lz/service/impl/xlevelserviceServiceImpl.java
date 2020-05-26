package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Xlevelservice;
import com.zygh.lz.mapper.XlevelserviceMapper;
import com.zygh.lz.service.xlevelserviceService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class xlevelserviceServiceImpl implements xlevelserviceService {
    @Autowired
    private XlevelserviceMapper xlevelserviceMapper;

    /**
     * 查询全部等级勤务
     * @return
     */
    @Override
    public ResultBean selectorderlyAll(String sectionName) {
        int success = xlevelserviceMapper.selectorderlyAll(sectionName);
        return ResultUtil.setOK("success",success);
    }

    /**
     * 等级勤务
     * @return
     */
    @Override
    public ResultBean selectorderlydjyd(String sectionName) {
        List<Map> list=new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();
        Map<String,Object> map2=new HashMap<>();
        Map<String,Object> map3=new HashMap<>();
        //一级应到
        List<HashMap> selectorderlyoneyd = xlevelserviceMapper.selectorderlyoneyd(sectionName);
        Integer selectyjyd = xlevelserviceMapper.selectyjyd(sectionName);
        if(selectyjyd==null){
            selectyjyd=0;
        }
        for (int i=0;i<selectorderlyoneyd.size();i++){
            selectorderlyoneyd.get(i).put("SDnum",0);
            selectorderlyoneyd.get(i).put("gfZXL",0);
        }
        //二级应到
        List<HashMap> selectorderlytweyd = xlevelserviceMapper.selectorderlytweyd(sectionName);
        Integer selectejyd = xlevelserviceMapper.selectejyd(sectionName);
        if(selectejyd==null){
            selectejyd=0;
        }
        for (int i=0;i<selectorderlytweyd.size();i++){
            selectorderlytweyd.get(i).put("SDnum",0);
            selectorderlytweyd.get(i).put("gfZXL",0);
        }
        //三级应到
        List<HashMap> selectorderlythreeyd = xlevelserviceMapper.selectorderlythreeyd(sectionName);
        Integer selectsjyd = xlevelserviceMapper.selectsjyd(sectionName);
        if(selectsjyd==null){
            selectsjyd=0;
        }
        for (int i=0;i<selectorderlythreeyd.size();i++){
            selectorderlythreeyd.get(i).put("SDnum",0);
            selectorderlythreeyd.get(i).put("gfZXL",0);
        }

        map1.put("ddName","一级勤务");
        map1.put("ddSDnum",0);
        map1.put("ddYDnum",selectyjyd);
        map1.put("gfZXL",0);
        map1.put("zdCount",selectorderlyoneyd);

        map2.put("ddName","二级勤务");
        map2.put("ddSDnum",0);
        map2.put("ddYDnum",selectejyd);
        map2.put("gfZXL",0);
        map2.put("zdCount",selectorderlytweyd);

        map3.put("ddName","三级勤务");
        map3.put("ddSDnum",0);
        map3.put("ddYDnum",selectsjyd);
        map3.put("gfZXL",0);
        map3.put("zdCount",selectorderlythreeyd);

        list.add(map1);
        list.add(map2);
        list.add(map3);
        /*map.put("stair",selectorderlyoneyd);
        map.put("second",selectorderlytweyd);
        map.put("three",selectorderlythreeyd);*/
        return ResultUtil.setOK("success",list);
    }

    @Override
    public ResultBean selectxleveBydj(Integer hierarchy, String sectionName) {
        List<Xlevelservice> xlevelservices = xlevelserviceMapper.selectxleveBydj(hierarchy, sectionName);
        if(xlevelservices.size()>=0){
            return ResultUtil.setOK("success",xlevelservices);
        }
        return null;
    }

}
