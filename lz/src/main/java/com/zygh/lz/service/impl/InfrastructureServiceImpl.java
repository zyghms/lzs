package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Infrastructure;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.InfrastructureMapper;
import com.zygh.lz.service.InfrastructureService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfrastructureServiceImpl implements InfrastructureService {
    @Autowired
    private InfrastructureMapper infrastructureMapper;

    /**
     * 新增交通基础设施
     *
     * @param infrastructure
     * @return
     */
    @Override
    public ResultBean addInfrastructure(Infrastructure infrastructure) {
        return ResultUtil.execOp(infrastructureMapper.insertSelective(infrastructure), "新增");
    }

    /**
     * 修改交通基础设施
     *
     * @param infrastructure
     * @return
     */
    @Override
    public ResultBean updateInfrastructure(Infrastructure infrastructure) {
        return ResultUtil.execOp(infrastructureMapper.updateByPrimaryKeySelective(infrastructure), "修改");
    }

    /**
     * 根据id删除交通基础设施
     *
     * @param id
     * @return
     */
    @Override
    public ResultBean delInfrastructure(Integer id) {
        return ResultUtil.execOp(infrastructureMapper.deleteByPrimaryKey(id), "删除");
    }

    /**
     * 根据设施类型、道路名称模糊查询
     *
     * @param type
     * @param roadName
     * @return
     */
    @Override
    public ResultBean selectInfrastructureByInfo(String type, String roadName) {
        List<Infrastructure> infrastructures = infrastructureMapper.selectInfrastructureByInfo(type, roadName);
        if (infrastructures.size() > 0) {
            return ResultUtil.setOK("success", infrastructures);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }
}
