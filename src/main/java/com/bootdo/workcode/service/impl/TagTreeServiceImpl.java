package com.bootdo.workcode.service.impl;

import com.bootdo.workcode.bean.MsgLabel;
import com.bootdo.workcode.service.TagTreeService;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jiangxiao
 * @Title: TagTreeServiceImpl
 * @Package
 * @Description: 3层标签实现
 * @date 2020/5/1117:33
 */
public class TagTreeServiceImpl implements TagTreeService {

    public static List<MsgLabel> getMsgLabelByHierarchy(List<MsgLabel> labelList){

        List<MsgLabel> lv2labelList = new ArrayList<>();
        List<MsgLabel> lv3labelList = new ArrayList<>();

        labelList.forEach(labelEntity ->{
            if (StringUtils.isNotEmpty(labelEntity.getLv1Label())){

                // 二级末
                if (StringUtils.isEmpty(labelEntity.getLv3Label()) && StringUtils.isNotEmpty(labelEntity.getLv2Label())) {
                    lv2labelList.add(labelEntity);
                }else if (StringUtils.isNotEmpty(labelEntity.getLv3Label()) && StringUtils.isNotEmpty(labelEntity.getLv2Label())){
                    lv3labelList.add(labelEntity);
                }

            }else {
                System.out.println("不存在 一级为空的数据，过滤");
            }
        });
        System.out.println("这里获取到了 只有二级标签的list  和  只有三级标签的list");
        //遍历二级标签都存在的集合，根据一级标签去重
        List<MsgLabel> lv1labelList = ridRepeatByLv1Label(lv2labelList);



        return null;

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
