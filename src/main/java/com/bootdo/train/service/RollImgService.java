package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.ScrollGraph;

import java.util.List;

public interface RollImgService {
    List<ScrollGraph> selectAll();
    int save(ScrollGraph scrollGraph, UserDO userDO);
    ScrollGraph selectOne();
    int remove(int id);
}
