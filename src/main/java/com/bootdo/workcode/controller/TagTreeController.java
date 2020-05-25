package com.bootdo.workcode.controller;

import com.bootdo.workcode.bean.MsgLabel;
import com.bootdo.workcode.dao.MsgLabelDao;
import com.bootdo.workcode.service.impl.TagTreeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiangxiao
 * @Title: TagTreeController
 * @Package
 * @Description: 3层标签树
 * @date 2020/5/1117:32
 */
@RestController
@RequestMapping("/tagTree")
public class TagTreeController {

    @Autowired
    private MsgLabelDao msgLabelDao;

    @Autowired
    private TagTreeServiceImpl tagTreeService;

    @RequestMapping("/list")
    public List<MsgLabel> getTagTree(){
        List<MsgLabel> list = msgLabelDao.list();

        List<MsgLabel> msgLabelByHierarchy = tagTreeService.getMsgLabelByHierarchy(list);
        return msgLabelByHierarchy;
    }

}
