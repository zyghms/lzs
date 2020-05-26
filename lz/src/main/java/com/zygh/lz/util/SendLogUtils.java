package com.zygh.lz.util;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import net.sf.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SendLogUtils {

    /**
     *
     * @ policeId   警员号
     * @ sessionId   session标识
     * @ terminalIp  终端ip
     * @ logType    日志类型//0：登录；1：查询；2：新增；3：修改；4：删除；5：登出；6：其他
     * @ module     功能模块，格式如：通讯录>通讯录查询
     * @ formatParam  请求参数
     * @ response     响应内容
     * @ responseType  响应内容类型html/json  1.json 2.html
     * @ result      访问结果成功失败  成功或失败，失败时要写失败原因
     * @ errorCode   操作失败时错误代码
     * @ errorLog    操作失败时错误原因
     */
    public static void sendLog(Operation operation) throws Exception {
            Map<String, String> param = new HashMap<>();
            param.put("policeId",operation.getPoliceId());// 用户标识（警号） 警号、证书sn号、身份证号三者只要一个就行
            param.put("sn", "");// 证书sn号
            param.put("cardNo", "");// 身份证号

            param.put("requestId", "");// 请求标识uuid全局唯一   可以为空
            param.put("sessionId",operation.getSessionId());// session标识    不能为空
            param.put("terminalIp",operation.getTerminalIp());// 终端标识ip  发起请求的终端ip地址，不能为空
            param.put("source", "7");// 日志来源  字典项，应用唯一标识，需要找集中管控技术支持进行申请
            param.put("logType",operation.getLogType());// 日志类型//0：登录；1：查询；2：新增；3：修改；4：删除；5：登出；6：其他  不能为空
            param.put("module",operation.getModule());// 功能模块，格式如：通讯录>通讯录查询 不能为空
            param.put("params", "");// 原始请求参数   可以为空
            param.put("formatParam",operation.getFormatParam());// 请求参数（格式化） ，不能为空
            param.put("url", "");// 访问url   可以为空
            param.put("uri", "");// uri     可以为空
            param.put("content", "");// 访问内容    可以为空
            param.put("response",operation.getResponse());// 响应内容   不可以为空
            param.put("responseType", operation.getResponseType());// 响应内容类型html/json  不能为空  1.json 2.html
            param.put("responseTime", "");// 请求耗时（ms）  可以为空
            param.put("result",operation.getResult());// 访问结果成功失败  成功或失败，失败时要写失败原因  不能为空
            param.put("errorCode",operation.getErrorCode());// 失败原因   可以为空
            param.put("errorLog", operation.getErrorLog());// 失败日志   可以为空
            param.put("sourceIp", "");// 源IP       可以为空
            param.put("sourcePort", "");// 源端口   可以为空
            param.put("destIp", "");// 服务的ip  可以为空
            param.put("destPort", "");// 服务的端口  可以为空
            param.put("time", String.valueOf(new Date().getTime()));// 日志记录时间,时间戳,格式如1519978042000   不能为空


            String tokenUrl = "http://62.64.11.15:8080/smcs/api/public/login";// 获取token路径
            String username = "admin";// 获取token登录名
            String password = "111111";// 获取token登录密码
            String sendUrl = "http://62.64.11.15:8080/smcs/api/userActive/save";// 日志推送路径

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
                        if (StringUtil.isNotEmpty(sendUrl)) {
                            HttpPost post = new HttpPost(sendUrl);
                            post.addHeader("token", returnToken); // 上一步获取返回结果中的token
                            post.addHeader("Content-type", "application/json");

                            List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();

                            paramList.add(param);
                            JSONArray array = JSONArray.fromObject(paramList);
                            String body = JSON.toJSONString(array);
                            post.setEntity(new StringEntity(body, "utf-8"));
                            HttpResponse sendResponse = hc.execute(post);
                            System.out.println(EntityUtils.toString(sendResponse
                                    .getEntity()));// {"code":"1","message":"成功","result":null}
                        }
                    }
                }
            }
    }

    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getSessionId(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        return sessionId;
    }
}
