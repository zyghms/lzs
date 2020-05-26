package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Urban;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.UrbanMapper;
import com.zygh.lz.service.urbanService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class urbanServiceImpl implements urbanService {
    @Autowired
    private UrbanMapper urbanMapper;

    /**
     * 查询全部区域
     * @return
     */
    @Override
    public ResultBean selectAllUrban() {
        List<Urban> urbans = urbanMapper.selectAllUrban();
        if(urbans.size() > 0){
            return ResultUtil.setOK("success",urbans);
        }
       return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 新增区域
     * @param urban
     * @return
     */
    @Override
    public ResultBean insertUrban(Urban urban) {
        return ResultUtil.execOp(urbanMapper.insertSelective(urban),"新增");
    }

    /**
     * 根据id删除区域
     * @param id
     * @return
     */
    @Override
    public ResultBean deleteSomeUrban(Integer id) {
        return ResultUtil.execOp(urbanMapper.deleteByPrimaryKey(id),"删除");
    }

    /**
     * 修改区域
     * @param urban
     * @return
     */
    @Override
    public ResultBean updateUrban(Urban urban) {
        return ResultUtil.execOp(urbanMapper.updateByPrimaryKeySelective(urban),"修改");
    }

    /**
     *模糊查询、匹配所属部门、区域名称
     * @param urban
     * @return
     */
    @Override
    public ResultBean seleteDimUrban(Urban urban) {
        List<Urban> urbans = urbanMapper.seleteDimUrban(urban);
        if(urbans.size() > 0){
            return ResultUtil.setOK("success",urbans);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",urbans);
    }

    /**
     * 统计查询
     * @param sectionId
     * @return
     */
    @Override
    public ResultBean selectUrbanByCount(Integer sectionId) {
        List<Urban> urbans = urbanMapper.selectUrbanByCount(sectionId);
        if(urbans.size() > 0){
            return ResultUtil.setOK("success",urbans);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
