package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainNotification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//通知通报
@Mapper
public interface TrainNotificationDao {

    int insert(TrainNotification record);

    List<TrainNotification> selectAll(Map<String, Object> map);

    int remove(int id);

    TrainNotification selectByid(int id);

    int count(Map<String, Object> map);

    int update(TrainNotification trainNotification);
}
