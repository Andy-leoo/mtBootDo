package com.bootdo.train.service;

import com.bootdo.train.pojo.TrainNews;
import com.bootdo.train.pojo.UserDO;

import java.util.List;
import java.util.Map;

public interface TrainNewsService {
    List<TrainNews> selectAll(Map<String, Object> map);
    int save(TrainNews news, UserDO userDO);
    int remove(int id);

    TrainNews selectOneById(int id);

    int count(Map<String, Object> map);

    int update(TrainNews params, UserDO user);
}
