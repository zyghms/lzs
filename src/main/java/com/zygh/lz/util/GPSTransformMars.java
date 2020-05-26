package com.zygh.lz.util;

import com.zygh.lz.mapper.XareaMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class GPSTransformMars {
    /*
     * GPS坐标转换为高德地图坐标
     * 输入GPS坐标，单位度，数据类型double，参数一为Lat,参数二为Lng
     * 输出高德地图坐标，单位度，数据类型double[]，参数一为Lat,参数二为Lng
     *
     * */
    double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
    double PI = 3.1415926535897932384626;
    double a = 6378245.0;
    double ee = 0.00669342162296594323;


    @Autowired
    private XareaMapper xareaMapper;

    /**
     * WGS84转GCj02
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public double[] transLatLng(double lng, double lat) {
        double[] point = new double[2];
        double dlat = transformlat(lng - 105.0, lat - 35.0);
        double dlng = transformlng(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        point[0] = lng + dlng;
        point[1] = lat + dlat;
        return point;
    }

    private double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    //坐标转换，
    public static String GCj2TOWGS(String gps) {
        String[] split = gps.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        String loastr = "";
        String loc = "";
        for (int k = 0; k < split.length; k++) {
            if (k % 2 == 0) {
                double lat = Double.valueOf(split[k + 1]); //标记纬度
                double lng = Double.valueOf(split[k]);     //标记经度
                double[] doubles = GCJ2WGSUtils.gcj02towgs84(lng, lat);
                loastr = String.valueOf(doubles[0]) + "," + String.valueOf(doubles[1]) + ",";
                loc += loastr;
            }
        }

        return loc.substring(0, loc.length() - 1);
    }

    /*public static void main(String[] args) {
        String gps="113.045021,34.795809,113.045288,34.790804,113.048582,34.782292,113.049665,34.779005,113.048088,34.767152,113.04929,34.759678,113.048024,34.752644,113.043283,34.744904,113.041995,34.737393,113.033841,34.718734,113.023112,34.70988,113.011525,34.703,113.008864,34.698555,113.00925,34.694532,113.012362,34.68878,113.013585,34.684298,113.01955,34.670393,113.0353,34.649567,113.054183,34.639964,113.066714,34.639328,113.071349,34.634455,113.08182,34.62598,113.094008,34.619765,113.100016,34.608887,113.112805,34.602105,113.127697,34.583946,113.135314,34.579176,113.138812,34.570165,113.136494,34.556877,113.14276,34.545849,113.149626,34.534113,113.151042,34.523011,113.143532,34.510564,113.13113,34.499601,113.126913,34.493031,113.126001,34.485965,113.128469,34.476998";
        String[] split = gps.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        String loastr = "";
        String loc = "";
        for (int k = 0; k < split.length; k++) {
            if (k % 2 == 0) {
                double lat = Double.valueOf(split[k + 1]); //标记纬度
                double lng = Double.valueOf(split[k]);     //标记经度
                double[] doubles = GCJ2WGSUtils.gcj02towgs84(lng, lat);
                loastr = String.valueOf(doubles[0]) + "," + String.valueOf(doubles[1])+",";
                loc += loastr;
            }
        }

        System.out.println(loc.substring(0,loc.length()-1));
    }*/


}
