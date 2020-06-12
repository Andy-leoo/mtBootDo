package com.bootdo.workcode.bean;

import org.apache.velocity.runtime.directive.Foreach;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jiangxiao
 * @Title: DataUtils
 * @Package
 * @Description: 日期操作类
 * @date 2020/6/1117:13
 */
public class DataUtils {

    /**
     *
     *   //设置周一是一周的开始
     *   calendar.setFirstDayOfWeek(Calendar.MONDAY);
     *   // 每年的第一周最少有几天   odps函数  weekofyear  4天以上
     *   calendar.setMinimalDaysInFirstWeek(4);
     *
     */

    /**
     *  根据日期字符串获取是当年的第几周
     * @param date_str  格式 yyyy-MM-dd
     * @return
     */
    public static Integer getWeekOfYear(String date_str) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(date_str);
            Calendar calendar = Calendar.getInstance();
            //        设置周一是一周的开始
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            //        每年的第一周最少有几天   odps函数  weekofyear  4天以上
            calendar.setMinimalDaysInFirstWeek(4);
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     *  根据日期字符串获取是当年的第几周
     * @param date_str  格式 yyyy-MM-dd
     * @return
     */
    public static Integer getWeekOfYear2(String date_str) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(date_str);
            Calendar calendar = Calendar.getInstance();
            //        设置周一是一周的开始
//            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            //        每年的第一周最少有几天   odps函数  weekofyear  4天以上
//            calendar.setMinimalDaysInFirstWeek(4);
            calendar.setTime(date);
            System.out.println(Calendar.YEAR);
            System.out.println(Calendar.WEEK_OF_YEAR);
            System.out.println(calendar.get(Calendar.DAY_OF_WEEK)==1);
            System.out.println(calendar.get(Calendar.DAY_OF_WEEK)==2);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


//    public static List getWeek(String startTime, String endTime){
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd" );
//        String startDate = format.format(startTime);
//        String endDate = format.format(endTime);
//        //判断开始日期是周几
//        //开始时间
//        Calendar c= Calendar.getInstance();
//        c.setTime(startDate);//开始时间
//        //结束时间
//        Calendar d= Calendar.getInstance();
//        if (hmCourseCase.getExtendTime()!=null){
//            d.setTime(hmCourseCase.getExtendTime());
//        }else {
//            d.setTime(hmCourseCase.getEndTime());//结束时间
//        }
//
//        //计算开始时间为周几
//        int dayForWeek=0 ;//开始时间为周几
//        if (c.get(Calendar.DAY_OF_WEEK)==1 ){
//            dayForWeek=7 ;
//        }else {
//            dayForWeek=c.get(Calendar.DAY_OF_WEEK)-1;
//        }
//        //计算结束时间为周几
//        int dayEndWeek=0 ;//结束时间为周几
//        if (d.get(Calendar.DAY_OF_WEEK)==1 ){
//            dayEndWeek=7 ;
//        }else {
//            dayEndWeek=d.get(Calendar.DAY_OF_WEEK)-1;
//        }
//        Date start=c.getTime();
//        Date end=d.getTime();
//        String aa=format.format(start);
//
//        //计算两个时间之间相差多少天,再计算出两个日期之间相差的天数
//        long startDay = c.getTime().getTime();//把时间转换为毫秒
//        long endDay = d.getTime().getTime();//把时间转换为毫秒
//        long days=(endDay-startDay)/86400000;//相差天数 86400000:一天时间的毫秒数
//        long days2=days+dayForWeek+(7-dayEndWeek);
//        long weeks=days2/7;//计算出一共分几周
//        String beginweeks="";
//        String endweeks="";
//        long beginweeksMill=0;
//        long endweeksMill=0;
//        Calendar calendarbegin = Calendar.getInstance();
//        //第一周结束时间
//        String OneWeeksEnd="";
//        for (int i=1;i<=weeks;i++) {
//            if (i == 1) {//判断当前为第一周时
//                //第一周
//                // startDay 开始时间 然后再转化为yyyy-mm-dd HH:mm:ss
//                beginweeks = format.format(start);//第一周开始时间
//                //计算本周结束时间
//                endweeksMill = startDay + (7 - dayForWeek) * 86400000;//得到毫秒数
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(endweeksMill);//Date日期
//                //结束时间
//                endweeks = format.format(endweeksMill);//转化为yyyy-mm-dd HH:mm:ss
//                //根据本周开始时间和结束时间
//
//            } else if (i == weeks) {//判断当前为最后一周时
//                beginweeksMill = endweeksMill + 86400000;//当前周开始时间
//                calendarbegin.setTimeInMillis(beginweeksMill);
//                Date datebegin = calendarbegin.getTime();
//                //当前周开始时间转化为yyyy-mm-dd HH:mm:ss
//                beginweeks = format.format(datebegin);
//                endweeks = format.format(d.getTime());
//            } else {//非第一周和最后一周时
//                beginweeksMill = endweeksMill + 86400000;
//                calendarbegin.setTimeInMillis(beginweeksMill);
//                Date datebegin = calendarbegin.getTime();
//                //当前周开始时间转化为yyyy-mm-dd HH:mm:ss
//                beginweeks = format.format(datebegin);
//                endweeksMill = beginweeksMill + 6 * 86400000;
//                calendarbegin.setTimeInMillis(endweeksMill);
//                //当前周结束时间转化为yyyy-mm-dd HH:mm:ss
//                Date dateend = calendarbegin.getTime();
//                endweeks = format.format(dateend);
//            }
//        }
//    }

    /**
     * 根据传入的参数，来对日期区间进行拆分，返回拆分后的日期List
     * @param statisticsType
     * @param map
     * @return
     * @throws ParseException
     * @author lihq 2019-6-24
     * @editor
     * @editcont
     */
    public List<String> doDateByStatisticsType(String statisticsType, Map<String, Object> map) throws ParseException {
        List<String> listWeekOrMonth = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = (String)map.get("startDate");
        String endDate = (String)map.get("endDate");
        Date sDate = dateFormat.parse(startDate);
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        sCalendar.setTime(sDate);

        Date eDate = dateFormat.parse(endDate);
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        eCalendar.setTime(eDate);
        boolean bool =true;
        if(statisticsType.equals("week")){
            while(sCalendar.getTime().getTime()<eCalendar.getTime().getTime()){
                if(bool||sCalendar.get(Calendar.DAY_OF_WEEK)==2||sCalendar.get(Calendar.DAY_OF_WEEK)==1){
                    listWeekOrMonth.add(dateFormat.format(sCalendar.getTime()));
                    bool = false;
                }
                sCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            if(listWeekOrMonth.size()%2!=0){
                listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            }
        }else{
            while(sCalendar.getTime().getTime()<eCalendar.getTime().getTime()){
                if(bool||sCalendar.get(Calendar.DAY_OF_MONTH)==1||sCalendar.get(Calendar.DAY_OF_MONTH)==sCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
                    listWeekOrMonth.add(dateFormat.format(sCalendar.getTime()));
                    bool = false;
                }
                sCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            if(listWeekOrMonth.size()%2!=0){
                listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            }
        }

        return listWeekOrMonth;
    }


    public static List<DateBean> doDateByWeek( String startDate, String endDate) throws ParseException {
        List<DateBean> listWeek = new ArrayList<DateBean>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //开始时间
        Date sDate = dateFormat.parse(startDate);
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        sCalendar.setTime(sDate);
        //结束时间
        Date eDate = dateFormat.parse(endDate);
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        eCalendar.setTime(eDate);
        DateBean dateBean = null;

        // 首次需要进入
        boolean bool = true;
        int flag = 0;

        while(sCalendar.getTime().getTime()<eCalendar.getTime().getTime()){
            //Calendar.DAY_OF_WEEK 一周中的第几天  sun 下标1  mon 下标2  为 周一 或周日 都进入
            if(bool||sCalendar.get(Calendar.DAY_OF_WEEK)==2||sCalendar.get(Calendar.DAY_OF_WEEK)==1){

                if (bool||sCalendar.get(Calendar.DAY_OF_WEEK)==2){
                    dateBean = new DateBean();
                    System.out.println("周一----： "+dateFormat.format(sCalendar.getTime()));
                    dateBean.setStartTime(dateFormat.format(sCalendar.getTime()));
                    bool = false;
                    // 证明有 开始时间
                    flag = 1;
                }
                if (flag == 1 &&sCalendar.get(Calendar.DAY_OF_WEEK)==1){
                    System.out.println("周日----： "+dateFormat.format(sCalendar.getTime()));
                    dateBean.setYearStr(String.valueOf(sCalendar.get(Calendar.YEAR))+"-"+String.valueOf(sCalendar.get(Calendar.WEEK_OF_YEAR)));
//                    dateBean.setWeekStr(String.valueOf(sCalendar.get(Calendar.WEEK_OF_YEAR)));
                    dateBean.setEndTime(dateFormat.format(sCalendar.getTime()));
                    // 证明 结束时间 已存
                    flag = 2;
                }

                bool = false;
                if (flag ==2) {
                    listWeek.add(dateBean);
                    dateBean = new DateBean();
                    System.out.println(listWeek);
                    flag = 0;
                }
            }
            // 往后加一天
            sCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        // 放入 给的最后日期
        dateBean.setYearStr(String.valueOf(eCalendar.get(Calendar.YEAR))+"-"+String.valueOf(eCalendar.get(Calendar.WEEK_OF_YEAR)));
        dateBean.setEndTime(dateFormat.format(eCalendar.getTime()));
        if (sCalendar.get(Calendar.DAY_OF_WEEK)==2){
            dateBean.setStartTime(dateFormat.format(eCalendar.getTime()));
        }
        listWeek.add(dateBean);

        System.out.println(listWeek);
        return listWeek;
    }

    public static void main(String[] args) throws ParseException {
//        Integer weekOfYear = getWeekOfYear2("2020-06-11");
//        System.out.println(weekOfYear);
        List<DateBean> week = doDateByWeek("2020-06-14", "2020-06-22");
    }


}
