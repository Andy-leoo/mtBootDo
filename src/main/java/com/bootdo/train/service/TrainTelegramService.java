package com.bootdo.train.service;

import com.bootdo.train.pojo.TrainTelegram;
import com.bootdo.train.pojo.UserDO;

import java.util.List;
import java.util.Map;

public interface TrainTelegramService {
    List<TrainTelegram> selectAll(Map<String, Object> map);
    int save(TrainTelegram telegram, UserDO userDO);
    int remove(int id);

    TrainTelegram selectOneById(int id);

    int count(Map<String, Object> map);

    int update(TrainTelegram params, UserDO user);
}
