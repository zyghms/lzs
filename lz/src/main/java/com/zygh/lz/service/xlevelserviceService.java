package com.zygh.lz.service;

import com.zygh.lz.vo.ResultBean;

public interface xlevelserviceService {

    //等级勤务所有应到人数
    ResultBean selectorderlyAll(String sectionName);

    //一级勤务按大队应到人数
    ResultBean selectorderlydjyd(String sectionName);

    ResultBean selectxleveBydj(Integer hierarchy, String sectionName);
}
