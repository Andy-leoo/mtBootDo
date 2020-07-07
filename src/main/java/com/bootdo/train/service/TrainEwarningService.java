package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainEwarning;

import java.util.List;
import java.util.Map;

public interface TrainEwarningService {
    List<TrainEwarning> selectAll(Map<String, Object> map);
    int save(TrainEwarning trainEwarning, UserDO userDO);
    int remove(int id);

    TrainEwarning selectOneById(int id);

    int count(Map<String, Object> map);

    int update(TrainEwarning params, UserDO user);
}
