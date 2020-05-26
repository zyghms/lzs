package com.zygh.lz.mapper;

import com.zygh.lz.admin.Asset;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssetMapper {
    int deleteByPrimaryKey(Integer sysAssetId);

    int insert(Asset record);

    int insertSelective(Asset record);

    Asset selectByPrimaryKey(Integer sysAssetId);

    int updateByPrimaryKeySelective(Asset record);

    int updateByPrimaryKey(Asset record);

    //资产根据资产名字、类型、部门模糊查询
    List<Asset> selectAssetBytype(@Param("assetName") String assetName,@Param("type") String type,@Param("dept") String dept);

    //查询所有资产类型
    List<Asset> selectByTypeAsset();
}