package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Subsystem;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.SubsystemMapper;
import com.zygh.lz.service.subsystemService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubsystemServiceImpl implements subsystemService {
    @Autowired
    private SubsystemMapper subsystemMapper;

    @Override
    public ResultBean selectAllSubsystem() {
        List<Subsystem> subsystemList = subsystemMapper.selectAllSubsystem();
        if (subsystemList.size() > 0){
            return ResultUtil.setOK("success",subsystemList);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    @Override
    public ResultBean selectControlsystem() {
        List<Subsystem> subsystemList = subsystemMapper.selectControlsystem();
        if(subsystemList.size() > 0){
            return ResultUtil.setOK("success",subsystemList);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
    @Override
    public ResultBean selectControlsubsystem(Integer id) {
        List<Subsystem> subsystemList = subsystemMapper.selectControlsubsystem(id);
        if(subsystemList.size() > 0){
            return ResultUtil.setOK("success",subsystemList);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
    @Override
    public ResultBean selectRank() {
        List<Subsystem> subsystemList = subsystemMapper.selectRank();
        if(subsystemList.size() > 0){
            return ResultUtil.setOK("success",subsystemList);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
    @Override
    public ResultBean selectRepair() {
        List<Subsystem> subsystemList = subsystemMapper.selectRepair();
        if (subsystemList.size() > 0){
            return ResultUtil.setOK("success",subsystemList);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
