package com.zygh.lz.service;

import com.zygh.lz.admin.Infrastructure;
import com.zygh.lz.vo.ResultBean;

public interface InfrastructureService {
    //新增交通设施信息
    ResultBean addInfrastructure(Infrastructure infrastructure);
    //修改交通设施信息
    ResultBean updateInfrastructure(Infrastructure infrastructure);
    //根据id删除交通设施信息
    ResultBean delInfrastructure(Integer id);
    //根据交通设施类型、道路名模糊查询
    ResultBean selectInfrastructureByInfo(String type,String roadName);
}
