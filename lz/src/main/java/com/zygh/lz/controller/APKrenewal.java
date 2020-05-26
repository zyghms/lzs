package com.zygh.lz.controller;

import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.util.ViLog;
import com.zygh.lz.vo.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class APKrenewal {

    //APK更新
    @GetMapping("renewal")
    @ViLog(logType = "6",module = "APK跟新,版本号查询")
    public ResultBean renewal() {
        //String filepath = "D:\\apache-tomcat-7.0.78\\webapps\\";
        String filepath="C:\\java\\apache-tomcat-8.5.27\\webapps\\";
        File file = new File(filepath);//File类型可以是文件也可以是文件夹
        File[] fileArray = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
        if (fileArray.length != 0) {

            for (int n = 0; n < fileArray.length; n++) {
                if (fileArray[n].isFile()) {//判断是否为文件，如果是就...
                    File file1 = fileArray[n];
                    System.out.println("=====" + file1);
                    String filename = file1.toString();
                    System.out.println(filename);
                    String str2 = filename.substring(filename.length() - 3, filename.length());
                    if (str2.equals("apk")) {
                        //System.out.println("aasf"+filename);
                        //String filePathB = fileArray[n].getAbsolutePath();//获取绝对路径
                        String filePathB = filename;//获取绝对路径
                        String substring = filePathB.substring(41,45);
                        return ResultUtil.setOK("success", substring);
                    }
                }

            }
        }
        return ResultUtil.setError(SystemCon.RERROR1, "errot", null);
    }


    //错误日志上传
    @PostMapping("deLogin")
    @ViLog(logType = "2",module = "错误日志上传")
    public ResultBean deLogin(String log) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(new Date());
        //需要写入的文件的路径
        String filePath = "C:\\java\\apache-tomcat-8.5.27\\webapps\\html\\"+time+"log.html";
        String path="http://62.66.6.163:8090/html/"+time+"log.html";
        //写入的文件的内容

        try {
            File file = new File(filePath);
            FileOutputStream fos = null;
            if (!file.exists()) {
                file.createNewFile();//如果文件不存在，就创建该文件
                fos = new FileOutputStream(file);//首次写入获取
            } else {
                //如果文件已存在，那么就在文件末尾追加写入
                fos = new FileOutputStream(file, true);//这里构造方法多了一个参数true,表示在文件末尾追加写入
            }

            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//指定以UTF-8格式写入文件


            osw.write(log);
            //遍历list

            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.setOK("success",path);
    }

}
