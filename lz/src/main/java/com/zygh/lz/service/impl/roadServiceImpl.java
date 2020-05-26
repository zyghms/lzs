package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Road;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.RoadMapper;
import com.zygh.lz.service.roadService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class roadServiceImpl implements roadService {
    @Autowired
    private RoadMapper roadMapper;

    /**
     * 新增道路
     * @param road
     * @return
     */
    @Override
    public ResultBean addRoad(Road road) {
        return ResultUtil.execOp(roadMapper.insertSelective(road),"添加");
    }

    /**
     * 修改道路信息
     * @param road
     * @return
     */
    @Override
    public ResultBean updateRoad(Road road) {
        return ResultUtil.execOp(roadMapper.updateByPrimaryKeySelective(road),"修改");
    }

    /**
     * 根据id删除道路信息
     * @param id
     * @return
     */
    @Override
    public ResultBean delRoadById(Integer id) {
        return ResultUtil.execOp(roadMapper.deleteByPrimaryKey(id),"删除");
    }

    /**
     * 根据道路类型、所属区域查询
     * @param roadType
     * @param urbanName
     * @return
     */
    @Override
    public ResultBean selectRoadByCondition(String keyword,String roadType,String urbanName) {
        List<Road> roads = roadMapper.selectRoadByCondition(keyword,roadType, urbanName);
        if(roads.size() > 0){
            return ResultUtil.setOK("success",roads);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 查询所有道路
     * @return
     */
    @Override
    public ResultBean selectAllRoad() {
        List<Road> roads = roadMapper.selectAllRoad();
        if(roads.size() > 0){
            return ResultUtil.setOK("success",roads);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
