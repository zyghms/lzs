package com.zygh.lz.service;

import com.zygh.lz.vo.ResultBean;

public interface problemTypeService {
    //查询所有o问题类型详情
    ResultBean selectAllByProblemdetail(Integer id);
    //查询所有问题类型
    ResultBean selectAllByProblemtype();

}
