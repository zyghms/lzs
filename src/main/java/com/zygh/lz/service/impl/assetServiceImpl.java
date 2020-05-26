package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Asset;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.AssetMapper;
import com.zygh.lz.service.assetService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class assetServiceImpl implements assetService {
    @Autowired
    private AssetMapper assetMapper;

    /**
     * 资产新增
     * @param asset
     * @return
     */
    @Override
    public ResultBean addAsset(Asset asset) {
        return ResultUtil.execOp(assetMapper.insertSelective(asset),"新增");
    }

    /**
     * 资产修改
     * @param asset
     * @return
     */
    @Override
    public ResultBean updateAsset(Asset asset) {
        return ResultUtil.execOp(assetMapper.updateByPrimaryKeySelective(asset),"修改");
    }

    /**
     * 资产删除
     * @param id
     * @return
     */
    @Override
    public ResultBean delAsset(Integer id) {
        return ResultUtil.execOp(assetMapper.deleteByPrimaryKey(id),"新增");
    }

    /**
     * 按照资产类型、所属部门查询资产
     * @param type
     * @param dept
     * @return
     */
    @Override
    public ResultBean selectAssetBytype(String assetName,String type, String dept) {
        List<Asset> assets = assetMapper.selectAssetBytype(assetName,type, dept);
        if(assets.size() > 0){
            return ResultUtil.setOK("success",assets);
        }
        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }

    /**
     * 查询所有资产类型
     * @return
     */
    @Override
    public ResultBean selectByTypeAsset() {
        List<Asset> assets = assetMapper.selectByTypeAsset();
        if(assets.size() > 0){
            return ResultUtil.setOK("success",assets);
        }
      return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
