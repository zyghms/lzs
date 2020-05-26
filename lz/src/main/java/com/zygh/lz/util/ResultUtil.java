package com.zygh.lz.util;


import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.vo.PageBean;
import com.zygh.lz.vo.ResultBean;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/11/28 09:26
 */
public class ResultUtil {

    //操作成功
    public static ResultBean setOK(String msg, Object data){
        ResultBean resultBean=new ResultBean();
        resultBean.setCode(1);
        resultBean.setData(data);
        resultBean.setMsg(msg);
        return resultBean;
    }
    //操作失败
    public static ResultBean setError(int code, String msg, Object data){
        ResultBean resultBean=new ResultBean();
        resultBean.setCode(code);
        resultBean.setData(data);
        resultBean.setMsg(msg);
        return resultBean;
    }
    //分页操作
    //操作成功
    public static PageBean setPageOK(int page, int limit, int count, List<Object> data){
        PageBean pageBean=new PageBean();
        pageBean.setCode(1);
        pageBean.setData(data);
        pageBean.setMsg("OK");
        pageBean.setCurrpage(page);
        pageBean.setLimit(limit);
        pageBean.setTotalcount(count);
        pageBean.setTotalpage(count%limit==0?count/limit:count/limit+1);
        return pageBean;
    }

    public static PageBean setPageError(List<Object> data){
        PageBean pageBean=new PageBean();
        pageBean.setCode(0);
        pageBean.setData(null);
        pageBean.setMsg("error");
        return pageBean;
    }
    //非查询操作
    public static ResultBean execOp(int count, String msg){
        if(count>0){
            return setOK(msg+"成功",null);
        }else{
            return setError(SystemCon.RERROR1,msg+"失败",null);
        }
    }
}