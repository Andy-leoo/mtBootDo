package com.bootdo.workcode.service.impl;

import com.bootdo.workcode.bean.MsgLabel;
import com.bootdo.workcode.service.ITagTreeService;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author jiangxiao
 * @Title: TagTreeServiceImpl
 * @Package
 * @Description: 3层标签实现
 * @date 2020/5/1117:33
 */
public class TagTreeServiceImpl implements ITagTreeService {

    public List<MsgLabel> getMsgLabelByHierarchy(List<MsgLabel> labelList){

        List<MsgLabel> resultList = new ArrayList<>();
        List<MsgLabel> lv12labelList = new ArrayList<>();
        List<MsgLabel> lv123labelList = new ArrayList<>();

        labelList.forEach(labelEntity ->{
            if (StringUtils.isNotEmpty(labelEntity.getLv1Label())){

                // 二级末
                if (StringUtils.isEmpty(labelEntity.getLv3Label()) && StringUtils.isNotEmpty(labelEntity.getLv2Label())) {
                    lv12labelList.add(labelEntity);
                }else if (StringUtils.isNotEmpty(labelEntity.getLv3Label()) && StringUtils.isNotEmpty(labelEntity.getLv2Label())){
                    lv123labelList.add(labelEntity);
                }

            }else {
                System.out.println("不存在 一级为空的数据，过滤");
            }
        });
        System.out.println("这里获取到了 只有二级标签的list  和  只有三级标签的list");
        //遍历二级标签都存在的集合，根据一级标签去重
        List<MsgLabel> lv1labelList = ridRepeatByLv1Label(lv12labelList);
        //遍历二级标签都存在的集合（不包括三级标签集合）
        lv12labelList.forEach(label -> {
            //遍历根据一集标签去重后的标签集合，原集合相同一集标签的二级标签存入去重后的一集标签中
            lv1labelList.forEach(label1 ->{

                if (StringUtils.equals(label1.getLv1Label(),label.getLv1Label())) {
                    MsgLabel msgLabel2 = new MsgLabel();
                    msgLabel2.setLv1Label(label.getLv1Label());
                    msgLabel2.setLv2Label(label.getLv2Label());
                    label1.getList11().add(msgLabel2);
                }
            });
        });
        //将只有12级标签的存入结果集合
        resultList.addAll(lv1labelList);
        //遍历三级标签都存在的集合根据相同的 一 二级标签去重
        //TODO 学习stream 1.8jdk新特性
        List<MsgLabel> lv123RideLabelList = lv123labelList.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(
                                comparing(o -> ((MsgLabel) o).getLv1Label() + ";" + ((MsgLabel) o).getLv2Label())
                        )), ArrayList::new
                )
        );
        // 遍历三级标签都存在的集合
        lv123labelList.forEach(label12-> {
            //遍历根据 一 二级标签去重后的三级标签集合
            lv123RideLabelList.forEach(label123->{
                if (StringUtils.equals(label12.getLv1Label(),label123.getLv1Label())
                        && StringUtils.equals(label12.getLv2Label(),label123.getLv2Label())) {
                    MsgLabel label3  = new MsgLabel();
                    label3.setLv2Label(label12.getLv2Label());
                    label3.setLv3Label(label12.getLv3Label());
                    label12.getList111().add(label3);
                }
            });
        });

        //lv123RideLabelList 是装有  不重复的 一二级标签对象  及对应的三级标签集合
        //遍历二级标签都存在的集合  根据相同一集标签去重
        List<MsgLabel> lv1BlabelList = ridRepeatByLv1Label(lv123RideLabelList);
        //遍历二级标签集合（包括三级标签子集合）
        lv123RideLabelList.forEach(label -> {
            //遍历根据一集标签去重后的集合，将原集合相同的一集标签的二级标签存入一集标签对象的子集合中
            lv1BlabelList.forEach(label1 ->{
                if (StringUtils.equals(label.getLv1Label(),label1.getLv1Label())) {
                    MsgLabel label2 = new MsgLabel();
                    label2.setLv1Label(label.getLv1Label());
                    label2.setLv2Label(label.getLv2Label());
                    label2.setLv3Label(label.getLv3Label());
                    label2.setList111(label.getList111());
                    label1.getList11().add(label2);
                }
            });
        });
        resultList.addAll(lv1BlabelList);

        return resultList;

    }

    //根据一级标签编码将标签集合去重
    private static List<MsgLabel> ridRepeatByLv1Label(List<MsgLabel> lv2labelList) {
        List<MsgLabel> newList = new ArrayList<>();
        Set<String> newSet = new HashSet<>();
        lv2labelList.forEach(msgLabel -> {
            if (newSet.add(msgLabel.getLv1Label())) {
                MsgLabel msgLabel1 = new MsgLabel();
                msgLabel1.setLv1Label(msgLabel.getLv1Label());
                newList.add(msgLabel1);
            }
        });
        return newList;
    }

}
