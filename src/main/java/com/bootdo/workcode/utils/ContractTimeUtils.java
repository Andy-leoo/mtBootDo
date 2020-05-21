package com.bootdo.workcode.utils;

import com.bootdo.common.utils.DateUtils;
import com.bootdo.workcode.bean.ContractTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author jiangxiao
 * @Title: ContractTimeUtils
 * @Package
 * @Description: 传入多个时间段，输出一个时间段或者多个不关联的时间段
 * @date 2020/5/2016:35
 */
public class ContractTimeUtils {

    public static  List<ContractTime> factoryList(List<ContractTime> list){
        if (list.size() == 0 || list == null) {
            return null;
        }
        listSort(list);
        for (int i = 0; i < list.size(); i++) {
            if ((list.size() >1) && (i < list.size()-1)){
                ContractTime do1 = list.get(i);
                ContractTime do2 = list.get(i+1);
                /**
                 * 按照结束时间排序，判断相邻的起始日期对比
                 * 1. 如果后面的起始日期比签一份起始日期早，后一份的合约时间覆盖前一份
                 * 2. 如果后面的起始日期在前一个时间有限期内，时间合并成一个时间段
                 */
                if (DateUtils.getMargin(do2.getStartTime() , do1.getStartTime()) < 0){
                    list.remove(i);
                    listSort(list);
                    i--;
                }else {
                    list.remove(i);
                    list.remove(i);
                    ContractTime contractTime = new ContractTime();
                    contractTime.setStartTime(do1.getStartTime());
                    contractTime.setEndTime(do2.getEndTime());
                    listSort(list);
                    i--;
                }
            }
        }
        return list;
    }


    public static List<ContractTime> listSort(List<ContractTime> list){
        if (list.size() == 0 || list == null) {
            return null;
        }
        Collections.sort(list, new Comparator<ContractTime>() {
            //按照 结束时间 排序
            @Override
            public int compare(ContractTime o1, ContractTime o2) {
                return (o1.getEndTime()).compareTo(o2.getEndTime());
            }
        });
        return list;
    }
}
