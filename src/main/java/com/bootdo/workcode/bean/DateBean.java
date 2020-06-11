package com.bootdo.workcode.bean;

/**
 * @author jiangxiao
 * @Title: DateBean
 * @Package
 * @Description: 按周或月时间段统计
 * @date 2020/6/1117:53
 */
public class DateBean {
    /**
     * 年份
     */
    private String yearStr;
    /**
     * 对应的年份第几周
     */
    private String weekStr;
    private String startTime;
    private String endTime;

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "年份： "+ yearStr +"周 ：" +weekStr +"开始时间 ："+startTime + "结束时间 ：" + endTime;
    }
}
