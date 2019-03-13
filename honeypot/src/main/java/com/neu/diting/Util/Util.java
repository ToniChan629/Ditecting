package com.neu.diting.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Toni Chan on 2018/8/23
 * getWeekOfDate判断当前日期是周几
 * numberToEnglish数字月份转成英语
 * getStrOfSeconds把字符串转换成具体的日时分秒
 * getLastDayOfMonth获取某年某月的最后一天
 */
public class Util {
    public static String[] infors = new String[]{"","","","","","","",""};//排列顺序为：1."s7",2."Modbus",3."DNP3",4."Enip",5."atg",6."Fins",7."CodeSys",8."Fox"
//    public static String getWeekOfDate(Date date) {
//        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//        if (week < 0){
//            week = 0;
//        }
//        return weekDays[week];
//    }
//    public static String numberToEnglish(int month) {
//        String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October","November","December"};
//        return Months[month+1];
//    }


    public static  long[] getStrOfSeconds(String string) {
        long seconds = Long.parseLong(string);

        long one_day = 60 * 60 * 24;
        long one_hour  = 60 * 60;
        long one_minute = 60;
        long day,hour,minute,second = 0L;

        day = seconds / one_day;
        hour = seconds % one_day / one_hour ;
        minute = seconds % one_day % one_hour /  one_minute;
        second = seconds % one_day % one_hour %  one_minute;
        long[] data  = new long[]{day,hour,minute,second};
        return data;
    }
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        // 某年某月的最后一天
        String lastday = Integer.toString(year);
        lastday = lastday + "-"+ Integer.toString(month+1);
        String day  = Integer.toString(cal.getActualMaximum(Calendar.DATE));
        lastday = lastday +"-"+ day;
        return lastday;
    }

    public static void storeInfors(String str_receive) {
        String[] strs = str_receive.split("#");
        switch(strs[1]){
            case "s7": infors[0] = str_receive;
                break;
            case "Modbus": infors[1] = str_receive;
                break;
            case "DNP3": infors[2] = str_receive;
                break;
            case "Enip": infors[3] = str_receive;
                break;
            case "atg": infors[4] = str_receive;
                break;
            case "Fins": infors[5] = str_receive;
                break;
            case "CodeSys": infors[6] = str_receive;
                break;
            case "Fox": infors[7] = str_receive;
                break;
        }
        for(String str:infors){
            System.out.println("表中数据："+str);
        }
    }

    public static Map dealReceive(String str_receive) {
        Map deviceInfor = new HashMap<>();
        String[] strs = str_receive.split("#");

//        deviceInfor.put("Protocol", strs[1]);

        Map memory = new HashMap<>();
        String used = strs[2].substring(10, strs[2].indexOf(",") - 2);
        memory.put("Used", used);

        String total = strs[2].substring(strs[2].indexOf(":", strs[2].indexOf("M")) + 3, strs[2].indexOf("M", strs[2].indexOf("M") + 1));
        memory.put("Total", total);
        String usedPercent = strs[2].substring(strs[2].lastIndexOf(":") + 3, strs[2].indexOf("%"));
        memory.put("Usedpercent", usedPercent);
        deviceInfor.put("Memory", memory);

        String CPU = strs[3].substring(0,strs[3].length()-1);
        deviceInfor.put("CPU",CPU);

        Map nic = new HashMap<>();
        String outPs = strs[4].substring(strs[4].indexOf(":") + 3, strs[4].indexOf("K"));
        nic.put("out_ps", outPs);
        String inPs = strs[4].substring(strs[4].lastIndexOf(":") + 3, strs[4].lastIndexOf("K"));
        nic.put("in_ps", inPs);
        deviceInfor.put("NIC", nic);
        System.out.println(deviceInfor.get("NIC"));

        return deviceInfor;
    }

    public static boolean Check(String[] information) {
        //检查输入的IP、子网掩码、网关是否符合相关格式
        for(int i = 0;i<information.length;i++){
            System.out.println(information[i]);
        }

        String pattern = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
        String MASK="^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0|255\\.(254|252|248|240|224|192|128|0)\\.0\\.0|255\\.255\\.(254|252|248|240|224|192|128|0)\\.0|255\\.255\\.255\\.(254|252|248|240|224|192|128|0)$";
//        if(information[1].matches(pattern)&&information[3].matches(pattern)&&information[2].matches(MASK)) {
//            return true;
//        }
        if(information[1].matches(pattern)) {
            System.out.println("IP匹配");
        }else{
            System.out.println("IP不匹配");
            return false;
        }
        if(information[2].matches(MASK)) {
            System.out.println("子网掩码匹配");
        }else {
            System.out.println("子网掩码不匹配");
            return false;
        }
        if(information[3].matches(pattern)) {
            System.out.println("网关匹配");
        }else{
            System.out.println("网关不匹配");
            return false;
        }
        return true;
    }
}
