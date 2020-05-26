package com.zygh.lz.util;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/** 系统日志：切面处理类 */
@Aspect
@Component
public class SysLogAspect/* implements Filter*/ {

    private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);

    /*@Autowired
    private IOperationService operationService;*/

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.zygh.lz.util.ViLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")         //AfterReturning
    public void saveOperation(JoinPoint joinPoint) throws IOException, ServletException {
        log.info("---------------接口日志记录---------------");
        //保存日志
        Operation operation = new Operation();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        System.out.println("signature="+signature);
        //获取切入点所在的方法
        Method method = signature.getMethod();
//        System.out.println("method="+method);

        //获取操作--方法上的ViLog的值
        ViLog viLog = method.getAnnotation(ViLog.class);
        if (viLog != null) {
            //保存操作事件、日志类型
            String logType = viLog.logType();
            operation.setLogType(logType);

            //功能模块
            String module = viLog.module();
            operation.setModule(module);

        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String httpMethod = request.getMethod();
        /*//操作的url
        String requestURI = request.getRequestURI();
        String requestURL = request.getRequestURL().toString();
        operation.setOperUrl(requestURL);*/
        // 客户端ip
        String ip = request.getRemoteAddr();
        operation.setTerminalIp(ip);
        if(request.getAttribute("policeNum") == null || request.getAttribute("policeNum").equals("")){
            // 操作人警员号
            HttpSession session = request.getSession(true);
            String policeNum = session.getAttribute("policeNum").toString();
            System.out.println("=================="+policeNum);
            operation.setPoliceId(policeNum);
        }else{
            operation.setPoliceId((String) request.getAttribute("policeNum"));
        }

        //浏览器唯一标识
        String sessionId = request.getSession().getId();
        operation.setSessionId(sessionId);

        //请求参数信息
        String paramter = "";
        //get的参数
        if(httpMethod.equalsIgnoreCase("GET")){
            String param = "";
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<String> keys = parameterMap.keySet();
            if(!keys.isEmpty()) {
                for (String key : keys) {
                    if(parameterMap.get(key) != null && parameterMap.get(key).length > 0){
                        param += key + ":" + parameterMap.get(key)[0] + ",";
                    }else{
                        param += key + ": '',";
                    }
                }
            }else {
                param = "";
            }

            operation.setFormatParam(param);
            //其他方法的参数
        }else {
            String param = "";
            try {
                request.setCharacterEncoding("UTF-8");
                if(request.getMethod().equalsIgnoreCase("post")){
                    //BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
                    ServletInputStream inputStream = request.getInputStream();
                    StringBuilder responseStrBuilder = new StringBuilder();
                    int bytesRead = -1;
                    byte[] bytes = new byte[1024*5];
                    while((bytesRead = inputStream.read(bytes)) > 0){
                        responseStrBuilder.append(new String(bytes,0,bytesRead,"UTF-8"));
                    }
                    inputStream.close();
                    if(responseStrBuilder.toString() != null && !responseStrBuilder.toString().equals("")) {
                        param = responseStrBuilder.toString();
                    }else{
                        Map<String, String[]> parameterMap = request.getParameterMap();
                        Set<String> keys = parameterMap.keySet();
                        if(!keys.isEmpty()) {
                            for (String key : keys) {
                                if(parameterMap.get(key) != null && parameterMap.get(key).length > 0){
                                    param += key + ":" + parameterMap.get(key)[0] + ",";
                                }else{
                                    param += key + ": '',";
                                }
                            }
                        }else {
                            param = "";
                        }
                    }
                }
            } catch (IOException e) {
                log.error("error："+e);
            }
            operation.setFormatParam(param);
        }

        //响应数据
        String result = JSON.toJSONString( request.getAttribute("result"));
        operation.setResponse(result);

        operation.setResponseType("1");
        try {
            Map<String,String> map = (Map<String, String>) JSON.parse(result);
            if(String.valueOf(map.get("code")).equals("1") || String.valueOf(map.get("code")).equals("200")){
                operation.setResult("成功");
                operation.setErrorCode("");
                operation.setErrorLog("");
            }else{
                operation.setResult("失败");
                operation.setErrorCode((map.get("code")+""));
                operation.setErrorLog(map.get("msg"));
            }
        } catch (Exception e) {
            //e.printStackTrace();
            operation.setResult("成功");
            operation.setErrorCode("");
            operation.setErrorLog("");
        }

        System.out.println("result:"+operation);
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //开始执行耗时操作
                Thread.sleep(1000 * 5);
                return "线程执行完成.";
            }
        };
        try {
            Future<String> future = exec.submit(call);
            String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
            SendLogUtils.sendLog(operation);
            System.out.println("================发送日志成功================");
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            //ex.printStackTrace();
        }catch (Exception e) {
            //e.printStackTrace();
            //发送日志失败
            System.out.println("------发送日志失败--------");
        }
    }
}
