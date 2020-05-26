package com.bootdo.workcode.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangxiao
 * @Title: MsgLabel
 * @Package
 * @Description: 三层标签类
 * @date 2020/5/2214:26
 */
public class MsgLabel {

    private String lv1Label;

    private String lv2Label;

    private String lv3Label;

    private List<MsgLabel> list11 = new ArrayList<>();

    private List<MsgLabel> list111 = new ArrayList<>();

    public String getLv1Label() {
        return lv1Label;
    }

    public void setLv1Label(String lv1Label) {
        this.lv1Label = lv1Label;
    }

    public String getLv2Label() {
        return lv2Label;
    }

    public void setLv2Label(String lv2Label) {
        this.lv2Label = lv2Label;
    }

    public String getLv3Label() {
        return lv3Label;
    }

    public void setLv3Label(String lv3Label) {
        this.lv3Label = lv3Label;
    }

    public List<MsgLabel> getList11() {
        return list11;
    }

    public void setList11(List<MsgLabel> list11) {
        this.list11 = list11;
    }

    public List<MsgLabel> getList111() {
        return list111;
    }

    public void setList111(List<MsgLabel> list111) {
        this.list111 = list111;
    }
}
