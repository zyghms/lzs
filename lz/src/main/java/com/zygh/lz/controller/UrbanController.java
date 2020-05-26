package com.zygh.lz.controller;

import com.zygh.lz.admin.Urban;
import com.zygh.lz.service.roadService;
import com.zygh.lz.service.urbanService;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UrbanController {
    @Autowired
    private urbanService urbanService;

    //查询所有区域
    @GetMapping("selectAllUrban")
    @ViLog(logType = "1", module = "区域管辖>查询所有区域")
    public ResultBean selectAllUrban(HttpServletRequest request) {
        //request.setAttribute("result", urbanService.selectAllUrban());
        return urbanService.selectAllUrban();
    }

    //新增区域
    @PostMapping("insertUrban")
    @ViLog(logType = "2", module = "区域管辖>新增区域")
    public ResultBean insertUrban(Urban urban,HttpServletRequest request) {
        //request.setAttribute("result",urbanService.insertUrban(urban));
        return urbanService.insertUrban(urban);
    }

    //删除
    @GetMapping("deleteSomeUrban")
    @ViLog(logType = "1", module = "区域管辖>删除")
    public ResultBean deleteSomeUrban(Integer id,HttpServletRequest request) {
        //request.setAttribute("result",urbanService.deleteSomeUrban(id));
        return urbanService.deleteSomeUrban(id);
    }

    //修改
    @GetMapping("updateUrban")
    @ViLog(logType = "1", module = "区域管辖>修改")
    public ResultBean updateUrban(Urban urban,HttpServletRequest request) {
        //request.setAttribute("result",urbanService.updateUrban(urban));
        return urbanService.updateUrban(urban);
    }

    //查询
    @GetMapping("seleteDimUrban")
    @ViLog(logType = "1", module = "区域管辖>查询")
    public ResultBean seleteDimUrban(Urban urban,HttpServletRequest request) {
        //request.setAttribute("result",urbanService.seleteDimUrban(urban));
        return urbanService.seleteDimUrban(urban);
    }

    //按照大队统计各管辖区域发现问题数目。统计
    @GetMapping("selectUrbanByCount")
    @ViLog(logType = "1", module = "区域管辖>按照大队统计各管辖区域发现问题数目。统计")
    public ResultBean selectUrbanByCount(Integer sectionId,HttpServletRequest request){
        //request.setAttribute("result",urbanService.selectUrbanByCount(sectionId));
        return urbanService.selectUrbanByCount(sectionId);
    }


}
