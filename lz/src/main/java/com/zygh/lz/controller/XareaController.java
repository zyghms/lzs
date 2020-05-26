package com.zygh.lz.controller;

import com.zygh.lz.admin.Xarea;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/Xarea")
@RestController
public class XareaController {
    @Autowired
    private com.zygh.lz.service.xareaService xareaService;

    //根据大队，中队，岗位，中队领导
    @GetMapping("selectXareabycondition")
    @ViLog(logType = "1", module = "交警巡查区域>根据大队，中队，岗位，中队领导")
    public ResultBean selectXareabycondition(String battalion, String detachment, String station, String leader, String grid, String type){
        return xareaService.selectXareabycondition(battalion,detachment,station,leader,grid,type);
    }

    @PostMapping("insertXarea")
    @ViLog(logType = "2", module = "新增交警巡查区域")
    public ResultBean insertXarea(Xarea xarea,Integer staffId){
        return xareaService.insertXarea(xarea,staffId);
    }

    //修改
    @GetMapping("updateXarea")
    @ViLog(logType = "3", module = "修改交警巡查区域")
    public ResultBean updateXarea(Xarea xarea){
        return xareaService.updateXarea(xarea);
    }

    //删除
    @GetMapping("deleteXarea")
    @ViLog(logType = "4", module = "删除交警巡查区域")
    public ResultBean deleteXarea(Integer id){
        return xareaService.deleteXarea(id);
    }

    //根据名字模糊查询
    @GetMapping("selectXareaByName")
    @ViLog(logType = "1", module = "根据名字查询交警巡查区域")
    public ResultBean selectXareaByName(String name,String battalion,String detachment) {
        return xareaService.selectXareaByName(name,battalion,detachment);
    }

    //查询所有区域
    @GetMapping("selectXareaAll")
    @ViLog(logType = "1", module = "查询所有交警巡查区域")
    public ResultBean selectXareaAll(){
        return xareaService.selectXareaAll();
    }

    //查询所有区域里民警应到、实到人数
    @GetMapping("selectonlineNumber")
    @ViLog(logType = "1", module = "根据区域id查询民警应到、实到人数")
    public ResultBean selectonlineNumber(Integer id){
        return xareaService.selectonlineNumber(id);
    }

    //按大队区分所有在线警力
    @GetMapping("selectpolice")
    @ViLog(logType = "1", module = "按大队区分所有在线警力")
    public ResultBean selectpolice(){
        return xareaService.selectpolice();
    }

    @GetMapping("selectqt")
    @ViLog(logType = "1", module = "查询白天夜间其他警力")
    public ResultBean selectqt(String conment){
        return xareaService.selectqt(conment);
    }

    /**
     * 日间固定岗/高峰岗
     * @param station
     * @return
     */
    @GetMapping("selectfixationRJ")
    @ViLog(logType = "1", module = "日间固定岗/高峰岗")
    public ResultBean selectfixationRJ(String station) {
        return xareaService.selectfixationRJ(station);
    }

    //日间重点机关岗
    @GetMapping("selectemphasisRJ")
    @ViLog(logType = "1", module = "日间重点机关岗")
    public ResultBean selectemphasisRJ() {
        return xareaService.selectemphasisRJ();
    }

    //日间铁骑
    @GetMapping("selectcavalryRJ")
    @ViLog(logType = "1", module = "日间铁骑")
    public ResultBean selectcavalryRJ() {
        return xareaService.selectcavalryRJ();
    }

    //日间网格警力部署
    @GetMapping("selectgriddingRJ")
    @ViLog(logType = "1", module = "日间网格警力部署")
    public ResultBean selectgriddingRJ() {
        return xareaService.selectgriddingRJ();
    }

    //日间快速岗部署
    @GetMapping("selectexpresswayRJ")
    @ViLog(logType = "1", module = "日间快速岗部署")
    public ResultBean selectexpresswayRJ(String station) {
        return xareaService.selectexpresswayRJ(station);
    }

    //日间其他部署
    @GetMapping("selectqtRJ")
    @ViLog(logType = "1", module = "日间其他部署")
    public ResultBean selectqtRJ() {
        return xareaService.selectqtRJ();
    }

    //全部在线人
    @GetMapping("selectInformant")
    @ViLog(logType = "1", module = "全部在线人")
    public ResultBean selectInformant() {
        return xareaService.selectInformant();
    }
    //夜间夜巡
    @GetMapping("selectNightTour")
    @ViLog(logType = "1", module = "夜间夜巡")
    public ResultBean selectNightTour() {
        return xareaService.selectNightTour();
    }

    //夜间快速/高速警力部署
    @GetMapping("selectcelerity")
    @ViLog(logType = "1", module = "夜间快速/高速警力部署")
    public ResultBean selectcelerity(String station) {
        return xareaService.selectcelerity(station);
    }
    //夜间其他警力部署
    @GetMapping("selectqita")
    @ViLog(logType = "1", module = "夜间其他警力部署")
    public ResultBean selectqita() {
        return xareaService.selectqita();
    }

    //九主六块十六示范区
    @GetMapping("selectDemonstrationPlot")
    @ViLog(logType = "1", module = "九主六块十六示范区")
    public ResultBean selectDemonstrationPlot(String station) {
        return xareaService.selectDemonstrationPlot(station);
    }

    //根据区域名字模糊匹配部署警力
    @GetMapping("selctStrength")
    @ViLog(logType = "1", module = "根据区域名字模糊匹配部署警力")
    public ResultBean selctStrength(String name) {
        return xareaService.selctStrength(name);
    }

    //九主六块警力详情
    @GetMapping("selectAllByDemonstration")
    @ViLog(logType = "1", module = "九主六块警力详情")
    public ResultBean selectAllByDemonstration(String station) {
        return xareaService.selectAllByDemonstration(station);
    }
}
