package com.zygh.lz.controller;

import com.zygh.lz.admin.Asset;
import com.zygh.lz.service.assetService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AssetController {
    @Autowired
    private assetService assetService;

    //资产新增
    @PostMapping("addAsset")
    @ViLog(logType = "2",module = "支队资产管理>资产新增")
    public ResultBean addAsset(Asset asset,HttpServletRequest request){
        //request.setAttribute("result",assetService.addAsset(asset));
        return assetService.addAsset(asset);
    }
    //资产修改
    @GetMapping("updateAsset")
    @ViLog(logType = "3",module = "支队资产管理>资产修改")
    public ResultBean updateAsset(Asset asset,HttpServletRequest request){
        //request.setAttribute("result",assetService.updateAsset(asset));
        return assetService.updateAsset(asset);
    }
    //资产删除
    @GetMapping("delAsset")
    @ViLog(logType = "4",module = "支队资产管理>资产删除")
    public ResultBean delAsset(Integer id,HttpServletRequest request){
        //request.setAttribute("result",assetService.delAsset(id));
        return assetService.delAsset(id);
    }
    //按照资产类型、所属部门查询
    @GetMapping("selectAssetBytype")
    @ViLog(logType = "1",module = "支队资产管理>按照资产类型、所属部门查询")
    public ResultBean selectAssetBytype(String assetName,String type,String dept,HttpServletRequest request){
        //request.setAttribute("result",assetService.selectAssetBytype(assetName,type,dept));
        return assetService.selectAssetBytype(assetName,type,dept);
    }

    //查询所有资产类型
    @GetMapping("selectByTypeAsset")
    @ViLog(logType = "1",module = "查询所有资产类型")
    public ResultBean selectByTypeAsset(HttpServletRequest request){
        //request.setAttribute("result",assetService.selectByTypeAsset());
        return assetService.selectByTypeAsset();
    }
}
