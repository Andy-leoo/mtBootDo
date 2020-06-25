package com.bootdo.train.service;

import com.bootdo.train.pojo.TrainInfo;
import com.bootdo.train.pojo.UserDO;

import java.util.List;
import java.util.Map;

public interface TrainInfoService {
    List<TrainInfo> selectAll(Map<String, Object> map);
    int save(TrainInfo news, UserDO userDO);
    int remove(int id);

    TrainInfo selectById(int id);

    int update(TrainInfo params, UserDO user);

    int count(Map<String, Object> map);
}
