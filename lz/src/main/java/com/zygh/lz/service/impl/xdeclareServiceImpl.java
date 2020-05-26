package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Xdeclare;
import com.zygh.lz.mapper.XdeclareMapper;
import com.zygh.lz.service.xdeclareService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class xdeclareServiceImpl implements xdeclareService {
    @Autowired
    private XdeclareMapper xdeclareMapper;

    /**
     * 新增申报
     * @param xdeclare
     * @return
     */
    @Override
    public ResultBean insertXdeclare(Xdeclare xdeclare) {
        xdeclare.setEstablishtime(new Date());
        return ResultUtil.execOp(xdeclareMapper.insertSelective(xdeclare),"新增");
    }
}
