package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainNotification;

import java.util.List;
import java.util.Map;

//通知通告
public interface TrainNotificationService {
    List<TrainNotification> selectAll(Map<String, Object> map);
    int save(TrainNotification notification, UserDO userDO);
    int remove(int id);
    TrainNotification selectOneById(int id);

    int count(Map<String, Object> map);

    int update(TrainNotification params, UserDO user);
}
