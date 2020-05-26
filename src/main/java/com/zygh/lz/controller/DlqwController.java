package com.zygh.lz.controller;

import com.alibaba.fastjson.JSON;
import com.zygh.lz.admin.PaperValidationVo;
import com.zygh.lz.admin.Staff;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.StaffMapper;
import com.zygh.lz.util.Operation;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/app/xdja")
@RestController
public class DlqwController {

    @Autowired
    private StaffMapper staffMapper;

    @PostMapping("/token")
    @ViLog(logType = "6", module = "登录认证>解析认证")
    public ResultBean test(@RequestBody PaperValidationVo paperValidationVo, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("解析认证");
        List<Object> list = new ArrayList<>();
        String strURL = "http://62.64.11.8:9020/uas/sso/singlesignoncontrol/checktoken.do";
        String params = "{\"strToken\":" + "\"" + paperValidationVo.getToken() + "\"" + "}";
        System.out.println("params票据:" + params);
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }

            reader.close();
            //解析返回数据
            System.out.println("==============解析返回数据============:" + res);
            Map<String, Object> policeMap = null;
            String policeNum = "";
            String person=null;
            try {
                Map<String, Object> map = (Map<String, Object>) JSON.parse(res);
                policeMap = (Map<String, Object>) JSON.parse((map.get("userInfo").toString()));
                policeNum = (String) policeMap.get("code");
                person = policeMap.get("id").toString();
                request.setAttribute("policeNum", policeNum);
                //list.add(person);
                System.out.println("警号----===:" + policeNum);

            } catch (Exception e) {
                //信达捷安返回的数据有错误
                e.printStackTrace();
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(ResultUtil.setError(SystemCon.RERROR1, "信大捷安认证失败", null));

            }
            Staff staff = staffMapper.selectStaffByNum(policeNum);

            staff.setStrength(person);
            //本地查询警员信息
            list.add(staff);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(ResultUtil.setError(204, "系统维护中，请联系管理员", ""));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (list.size() > 0 || list.size() == 0) {
            request.setAttribute("result", list);
            return ResultUtil.setOK("success", list);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }
}
