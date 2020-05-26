package com.zygh.lz.controller;

import com.google.gson.GsonBuilder;
import com.zygh.lz.admin.Video;
import com.zygh.lz.mapper.VideoMapper;
import com.zygh.lz.util.ViLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@RestController
public class CMD {

    @Autowired
    private VideoMapper videoMapper;

    /**
     * PC端流媒体推流
     * 控制CMD
     *
     * @return
     */
    @PostMapping("rtmp")
    @ViLog(logType = "6",module = "流媒体>PC端流媒体推流")
    public String rtmp(HttpServletRequest request) {
        List<Video> list = videoMapper.selectAllVideode();
        System.out.println(list);
        System.out.println(list.size());
        try {
            /** 以下可以输入自己想输入的cmd命令 */
            for (int i = 0; i < list.size(); i++) {
                String[] command = {"cmd",};
                Process p = null;
                p = Runtime.getRuntime().exec(command);

                new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
                new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
                PrintWriter stdin = new PrintWriter(p.getOutputStream());
                String streamClass = null;
                String user = list.get(i).getAccout();
                String pass = list.get(i).getPassword();
                String ip = list.get(i).getBasicip();
                String host = list.get(i).getHost();
                streamClass = "ffmpeg -re  -rtsp_transport tcp -i rtsp://" + user + ":" + pass + "@" + ip + ":554/h264/ch1/main/av_stream -vcodec copy -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 1920x1080 -q 10 -f flv rtmp://192.168.100.124:1935/live/" + host;
                stdin.println("cd /d E:/RTMP/ffmpeg-20190312-d227ed5-win64-static/bin");
                request.setAttribute("result",streamClass);
                stdin.println(streamClass);
                stdin.close();
            }

        } catch (Exception e) {
            throw new RuntimeException("编译出现错误：" + e.getMessage());
        }
        return "111";
    }

    /**
     * 查询所有摄像头信息
     * @return
     */
    @GetMapping("host")
    @ViLog(logType = "1",module = "流媒体>查询所有摄像头信息")
    public String selectCMD(HttpServletRequest request) {
        List<Video> list = videoMapper.selectAllVideo();
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        //定义data用来接收使用gb.create().toJson将list转换为json的数据
        String data = gb.create().toJson(list);
        return data;
    }

    @GetMapping("ASD")
    public String selectASD(Video video) {
        int bianhao = 1935;
        for (int i = 0; i < 400; i++) {
            String s = String.valueOf(bianhao);
            bianhao = Integer.parseInt(s);
            video.setId(i);
            video.setHost(s);
            videoMapper.updateByPrimaryKeySelective(video);
            bianhao++;
        }
        return "1";
    }


    //手机端流媒体推流
    /*@PostMapping("hls")
    public void hls() {
        List<Video> list = videoMapper.selectAllVideo();
        String filepath = "E:\\RTMP\\nginx-rtmp-win32-dev\\hls";
        File file = new File(filepath);//File类型可以是文件也可以是文件夹
        File[] fileArray = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
        if (fileArray.length == 0) {
            System.out.println("m3u8文件不存在");
            for (int i = 0; i < list.size(); i++) {
                try {
                    String[] command = {"cmd",};
                    Process p = null;
                    p = Runtime.getRuntime().exec(command);
                    new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
                    new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
                    PrintWriter stdin = new PrintWriter(p.getOutputStream());
                    String streamClass = null;
                    String user = list.get(i).getUser();
                    String pass = list.get(i).getPassword();
                    String ip = list.get(i).getIp();
                    String host = list.get(i).getHost();
                    //System.out.println(host);
                    streamClass = "ffmpeg -re  -rtsp_transport tcp -i rtsp://" + user + ":" + pass + "@" + ip + ":554/h264/ch1/main/av_stream -fflags flush_packets -max_delay 1 -an -flags -global_header -hls_time 4 -hls_list_size 0 -hls_wrap 5 -vcodec copy -y E:\\RTMP\\nginx-rtmp-win32-dev\\hls\\" + host + ".m3u8";
                    stdin.println("cd /d E:/RTMP/ffmpeg-20190321-6dc1da4-win64-static/bin");

                    stdin.println(streamClass);
                    stdin.close();

                } catch (Exception e) {
                    throw new RuntimeException("编译出现错误：" + e.getMessage());
                }
            }
            if (MyFileListenerAdaptor.filePath(filepath)==1){

            }else {
                System.out.println("没有发生创建");
            }
        } else {
            String host = null;

            for (int n = 0; n < fileArray.length; n++) {

                if (fileArray[n].isFile()) {//判断是否为文件，如果是就...
                    String filePathB = fileArray[n].getAbsolutePath();//获取绝对路径
                    for (int i = 0; i < list.size(); i++){
                        host = list.get(i).getHost();
                        if (filePathB.equals("E:\\RTMP\\nginx-rtmp-win32-dev\\hls\\"+host+".m3u8")){
                            System.out.println("ELSE 下的 if  监m3u8");

                            if (MyFileListenerAdaptor.filePath(filepath)!=2 || MyFileListenerAdaptor.filePath(filepath) == 3){
                                System.out.println("删除 执行推流");

                                for (int j = 0; j < list.size(); j++) {

                                    try {

                                        String[] command = {"cmd",};
                                        Process p = null;
                                        p = Runtime.getRuntime().exec(command);
                                        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
                                        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
                                        PrintWriter stdin = new PrintWriter(p.getOutputStream());
                                        String streamClass = null;
                                        String user = list.get(j).getUser();
                                        String pass = list.get(j).getPassword();
                                        String ip = list.get(j).getIp();
                                        String hos = list.get(j).getHost();
                                        System.out.println(hos);
                                        streamClass = "ffmpeg -re  -rtsp_transport tcp -i rtsp://" + user + ":" + pass + "@" + ip + ":554/h264/ch1/main/av_stream -fflags flush_packets -max_delay 1 -an -flags -global_header -hls_time 4 -hls_list_size 0 -hls_wrap 5 -vcodec copy -y E:\\RTMP\\nginx-rtmp-win32-dev\\hls\\" + hos + ".m3u8";
                                        stdin.println("cd /d E:/RTMP/ffmpeg-20190321-6dc1da4-win64-static/bin");

                                        stdin.println(streamClass);
                                        stdin.close();

                                    } catch (Exception e) {
                                        throw new RuntimeException("编译出现错误：" + e.getMessage());
                                    }

                                }

                            }else{
                                System.out.println("执行修改 不做任何操作");
                            }
                        }
                    }

                }
            }
        }


    }*/

}

