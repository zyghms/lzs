package com.zygh.lz.vo;

public class pgs {
    public static void main(String[] args) {
        String gps="113.78769064388113 34.811117979241466,113.78766502479641 34.81105105090869,113.7876868775029 34.81100896404953";
        System.out.println("++++++"+gps.substring(0,1));
        if(gps.substring(0,1).equals(",")){
            System.out.println("11");
            gps.substring(1,gps.length());
        }
        System.out.println(gps.replaceAll(" ",","));
    }
    public static String replace(String gps){
        String s = gps.replaceAll(" ", ",");
        System.out.println(s.substring(0,1));
        if(s.substring(0,1).equals(",")){
            s=s.substring(1,s.length());
        }
        return s;
    }
}
