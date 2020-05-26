package com.zygh.lz.constant;

/**
 *@Author feri
 *@Date Created in 2018/11/28 09:28
 */
public class SystemCon {
    //返回码
    public static final int ROK=1;
    public static final int RERROR1=0;


    //请求方式
    public static final String GET="GET";
    public static final String POST="POST";

    //需要用到秘钥  AES
    public static final String TOKENKEY="rIx0kOItRGXwfKJksWVhmg==";
    public static final String PASSKEY="xQyfQmWAlLQCT9Y5Gxh2IQ==";

    //token对应的redis的key
    public static final String TOKENHASH="usertokens";
    public static final String TOKECOOKIE="usertoken";
    public static final String TOKENPRE="token:";

    //分页
    public static final Integer PAGENUM=1;
    public static final Integer PAGESIZE=10;

    //Redus存储共有信息的key
    public static final String CLASSESANDEQUI="classesAndEqui";



}
