package com.zygh.lz.service;

import com.zygh.lz.admin.Notice;
import com.zygh.lz.vo.ResultBean;

public interface noticeService {
    //新增公告
    ResultBean addNotice(Notice notice);
    //修改公告
    ResultBean updaNotice(Notice notice);
    //删除公告
    ResultBean delNotice(Integer id);
    //根据部门
    ResultBean selectAllNotice(Integer sectionId);
    //查询所有公告
    ResultBean selectNotice();
}
