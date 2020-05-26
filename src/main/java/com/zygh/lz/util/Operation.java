package com.zygh.lz.util;

public class Operation {

    private String policeId;  //警员号
    private String sessionId; // session标识
    private String terminalIp; // 终端ip
    private String logType; // 日志类型//0：登录；1：查询；2：新增；3：修改；4：删除；5：登出；6：其他
    private String module; // 功能模块，格式如：通讯录>通讯录查询
    private String formatParam; // 请求参数
    private String response;   //响应内容
    private String responseType; // 响应内容
    private String result; // 响应内容类型html/json  1.json 2.html
    private String errorCode; // 访问结果成功失败  成功或失败，失败时要写失败原因
    private String errorLog; // 操作失败时错误代码


    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFormatParam() {
        return formatParam;
    }

    public void setFormatParam(String formatParam) {
        this.formatParam = formatParam;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "policeId='" + policeId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", terminalIp='" + terminalIp + '\'' +
                ", logType='" + logType + '\'' +
                ", module='" + module + '\'' +
                ", formatParam='" + formatParam + '\'' +
                ", response='" + response + '\'' +
                ", responseType='" + responseType + '\'' +
                ", result='" + result + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorLog='" + errorLog + '\'' +
                '}';
    }
}
