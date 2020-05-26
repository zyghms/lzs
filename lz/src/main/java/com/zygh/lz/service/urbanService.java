package com.zygh.lz.service;

import com.zygh.lz.admin.Urban;
import com.zygh.lz.vo.ResultBean;

public interface urbanService {
    //查询所有区域
    ResultBean selectAllUrban();
    //新增区域
    ResultBean insertUrban(Urban urban);
    //删除
    ResultBean deleteSomeUrban(Integer id);
    //修改
    ResultBean updateUrban(Urban urban);
    //查询
    ResultBean seleteDimUrban(Urban urban);
    //统计查询
    ResultBean selectUrbanByCount(Integer sectionId);
}
