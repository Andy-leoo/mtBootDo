package com.bootdo.learning.java8.date;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author jiangxiao
 * @Title: LocalDateUitl
 * @Package
 * @Description: 学习 java8 全新时间API
 * @date 2020/7/1315:16
 */
public class LocalDateUtils {

    //获取今天的日期,只有日期，不包含时间。当你仅需要表示日期时就用这个类
    public static void getCurrentDate(){
        LocalDate today = LocalDate.now();
        System.out.println("Today's Local date : " + today);

        //这个是作为对比
        Date date = new Date();
        System.out.println(date);
    }

    //获取年、月、日信息
    public static void getDetailDate(){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        System.out.printf("Year : %d  Month : %d  day : %d t %n", year, month, day);
    }

    //处理特定日期
    public static void handleSpecilDate(){
        LocalDate dateOfBirth = LocalDate.of(2018, 01, 21);
        System.out.println("The specil date is : " + dateOfBirth);
    }

    //判断两个日期是否相等
    public static void compareDate(){
        LocalDate today = LocalDate.now();
        LocalDate date1 = LocalDate.of(2020, 07, 13);

        if(date1.equals(today)){
            System.out.printf("TODAY %s and DATE1 %s are same date %n", today, date1);
        }
    }

    public static void main(String[] args) {
//        getCurrentDate();
//        getDetailDate();
//        handleSpecilDate();
        compareDate();
    }



}
