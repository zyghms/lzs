package com.zygh.lz.controller;

import com.google.gson.GsonBuilder;
import com.zygh.lz.admin.Basic;
import com.zygh.lz.mapper.BasicMapper;
import com.zygh.lz.util.ViLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公司：中裕广恒科技股份有限公司
 * 项目：路长制
 * 编程人员：研发部
 * 时间：2018/11/5 16:38
 * 版本：1
 * 描述：基础设施操作（主要是相机，中控机等）
 * 修改人：
 */
@RestController
@RequestMapping(value = "/basic", produces = "text/plain;charset=utf-8")
public class BasicController {
    @Autowired
    private BasicMapper basicMapper;

    /**
     * 查询所有基础设施
     *
     * @param request
     * @return java.lang.String
     * @author 研发部
     * @date 2018/11/21 14:25
     */

    @RequestMapping(value = "/selectAll")
    @ViLog(logType = "1",module = "基础设施>查询所有基础设施")
    public String selectAsset(HttpServletRequest request) {
        List<Basic> basicList = basicMapper.selectAll();
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        //定义data用来接收使用gb.create().toJson将list转换为json的数据
        String data = gb.create().toJson(basicList);
        return data;
    }

}
