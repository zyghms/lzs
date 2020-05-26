package com.zygh.lz.service;

import com.zygh.lz.admin.Duty;
import com.zygh.lz.vo.ResultBean;

public interface dutyService {
    //联表查询所有道路责任明细
    ResultBean selectAllDuty();
    //模糊查询
    ResultBean seleteDimAsset(Duty duty);
    //新增
    ResultBean addDuty(Duty duty);
    //修改
    ResultBean updateDuty(Duty duty);
    //删除
    ResultBean delDuty(Integer id);
}
