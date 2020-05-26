package com.zygh.lz.service;

import com.zygh.lz.admin.Asset;
import com.zygh.lz.vo.ResultBean;

public interface assetService {
    //资产新增
    ResultBean addAsset(Asset asset);
    //资产修改
    ResultBean updateAsset(Asset asset);
    //资产删除
    ResultBean delAsset(Integer id);
    //按照资产类型、所属部门查询
    ResultBean selectAssetBytype(String assetName,String type,String dept);
    //查询所有资产类型
    ResultBean selectByTypeAsset();
}
