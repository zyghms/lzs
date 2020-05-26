package com.zygh.lz.controller;

import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FileUpload {


    private String RedisImgBase64(String img) {

        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(img);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //encoder.encode(data);//返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    //文件上传
    @PostMapping("fileUpload")
    public ResultBean fileUpload(MultipartFile[] file) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(new Date());
        List<String> list = new ArrayList<>();
        if (file.length == 0) {
            return ResultUtil.setError(0, "FUCK", null);

        } else {
            for (int i = 0; i < file.length; i++) {

                String filename = null;
                String fileUrl = null;
                String fileUrlOne = null;
                String fileUrlTwo = null;
                String fileUrlThree = null;

                filename = file[i].getOriginalFilename();

                //存放Tomcat项目路径
                fileUrl = "C:\\java\\apache-tomcat-8.5.27\\webapps\\video\\" + new Date().getTime() + filename;
                //fileUrl = "D:\\software\\Tomcat\\apache-tomcat-7.0.93\\webapps\\video\\" + new Date().getTime() + filename;
                InetAddress addr = InetAddress.getLocalHost();
                String url1 = "D:\\file\\" + new Date().getTime() + filename;
                //fileUrlOne = "D:\\software\\Tomcat\\apache-tomcat-7.0.93\\webapps\\photo\\" + new Date().getTime() + filename;
                fileUrlTwo = "C:\\java\\apache-tomcat-8.5.27\\webapps\\apk\\" + new Date().getTime() + filename;
                fileUrlOne = "C:\\java\\apache-tomcat-8.5.27\\webapps\\photo\\" + new Date().getTime() + filename;
                fileUrlThree = "C:\\java\\apache-tomcat-8.5.27\\webapps\\html\\"+ filename;
                //访问路径，存数据库路径
                String video = "http://62.66.6.163:8090/video/" + new Date().getTime() + filename;
                String photo = "http://62.66.6.163:8090/photo/" + new Date().getTime() + filename;
                String apk = "http://62.66.6.163:8090/apk/" + new Date().getTime() + filename;
                String html = "http://62.66.6.163:8090/html/"+ filename;

                System.out.println(filename.substring(filename.indexOf(".") + 1));
                if (filename.substring(filename.indexOf(".") + 1).equals("mp4")) {
                    File newFile = new File(fileUrl);
                    file[i].transferTo(newFile);
                    list.add(video);

                    return ResultUtil.setOK("上传成功", list);
                }else if(filename.substring(filename.indexOf(".") + 1).equals("apk")){
                    File newFile = new File(fileUrlTwo);
                    file[i].transferTo(newFile);
                    list.add(apk);
                }else if(filename.substring(filename.indexOf(".") + 1).equals("html")){
                    File newFile = new File(fileUrlThree);
                    file[i].transferTo(newFile);
                    list.add(html);
                } else {
                    File newFile = new File(fileUrlOne);
                    file[i].transferTo(newFile);
                    list.add(photo);
                }
            }

        }
        return ResultUtil.setOK("上传成功", list);
    }


}
