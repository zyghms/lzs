package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Xrelation;
import com.zygh.lz.mapper.XrelationMapper;
import com.zygh.lz.service.XrelationService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XrelationServiceImpl implements XrelationService {
    @Autowired
    private XrelationMapper xrelationMapper;

    @Override
    public ResultBean insertXrelation(Xrelation xrelation) {
        return ResultUtil.execOp(xrelationMapper.insertSelective(xrelation),"新增");
    }

    @Override
    public ResultBean updateXrelation(Xrelation xrelation) {
        return ResultUtil.execOp(xrelationMapper.updateByPrimaryKeySelective(xrelation),"修改");
    }

    @Override
    public ResultBean deleteXrelatonByid(Integer id) {
        return ResultUtil.execOp(xrelationMapper.deleteByPrimaryKey(id),"删除");
    }
}
