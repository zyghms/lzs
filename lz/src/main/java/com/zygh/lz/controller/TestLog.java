package com.zygh.lz.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.zygh.lz.util.ViLog;
import net.sf.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("/app")
@RestController
public class TestLog {

    @RequestMapping("/test")
    @ViLog(logType = "1",module = "统一日志测试")
    public void testLog() {
        System.out.println("统一日志");
        try {
            String tokenUrl = "http://62.64.11.15:8080/smcs/api/public/login";// 获取token路径
            String username = "admin";// 获取token登录名
            String password = "111111";// 获取token登录密码
            String url = "http://62.64.11.15:8080/smcs/api/userActive/save";// 日志推送路径

            HttpClient hc = HttpClientBuilder.create().build();
            HttpPost tokenPost = new HttpPost(tokenUrl);
            tokenPost.addHeader("Content-type", "application/json");
            Map<String, String> tokenMap = new HashMap<String, String>();
            tokenMap.put("username", username);
            tokenMap.put("password", password);
            String tokenBody = JSON.toJSONString(tokenMap);
            tokenPost.setEntity(new StringEntity(tokenBody, "utf-8"));
            HttpResponse tokenResponse = hc.execute(tokenPost);
            String tokenJson = EntityUtils.toString(tokenResponse.getEntity());
            if (tokenJson != null && tokenJson != "") {
                JSONObject json = new JSONObject(tokenJson);
                if (json.has("result")) { // 判断是否存在返回结果
                    String returnToken = json.get("result").toString();
                    if (returnToken != "" && returnToken != null) {
                        if (StringUtil.isNotEmpty(url)) {
                            HttpPost post = new HttpPost(url);
                            post.addHeader("token", returnToken); // 上一步获取返回结果中的token
                            post.addHeader("Content-type", "application/json");

                            List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
                            for (int i = 0; i < 10; i++) {
                                Map<String, String> param = new HashMap<String, String>();
                                param.put("policeId", "00001");// 用户标识（警号）
                                param.put("sn", "b00011");// 证书sn号
                                param.put("cardNo", "410833333333333333");// 身份证号
                                param.put("requestId", "");// 请求标识uuid全局唯一
                                param.put("sessionId", "jskdfe77");// session标识
                                param.put("terminalIp", "192.168.0.73");// 终端标识ip
                                param.put("source", "7");// 日志来源
                                param.put("logType", "1");// 日志类型//0：登录；1：查询；2：新增；3：修改；4：删除；5：登出；6：其他
                                param.put("module", "通讯录>个人通讯录");// 功能模块
                                param.put("params", "randNum=765;");// 原始请求参数
                                param.put("formatParam", "{\"身份证号\":\"41888888\",\"姓名\":\"测试1\"}");// 请求参数（格式化）
                                param.put("url", "http://11.12.108.217:8187/smcs/api/public");// 访问url
                                param.put("uri", "/smcs/api/public/terminal/status/save");// uri
                                param.put("content", "/files/七月份工资单.excel");// 访问内容
                                param.put("response", "[{\"性别\":\"男\",\"姓名\":\"测试1\"},{\"性别\":\"男\",\"姓名\":\"测试2\"}]");// 响应内容
                                param.put("responseType", "1");// 响应内容类型html/json
                                param.put("responseTime", "20");// 请求耗时（ms）
                                param.put("result", "成功");// 访问结果成功失败
                                param.put("errorCode", "400");// 失败原因
                                param.put("errorLog", "数据库操作异常!");// 失败日志
                                param.put("sourceIp", "11.12.110.254");// 源IP
                                param.put("sourcePort", "22");// 源端口
                                param.put("destIp", "11.12.108.217");// 目的IP
                                param.put("destPort", "89");// 目的端口
                                param.put("time", String.valueOf(new Date().getTime()));// 日志记录时间，时间戳，格式如1519978042000
                                paramList.add(param);
                            }
                            JSONArray array = JSONArray.fromObject(paramList);
                            String body = JSON.toJSONString(array);
                            post.setEntity(new StringEntity(body, "utf-8"));
                            HttpResponse response = hc.execute(post);
                            System.out.println(EntityUtils.toString(response
                                    .getEntity()));// {"code":"1","message":"成功","result":null}
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
