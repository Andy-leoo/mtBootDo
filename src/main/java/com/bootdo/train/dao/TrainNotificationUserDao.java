package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainNotificationUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainNotificationUserDao {
    //根据news id查询所有列表
    List<TrainNotificationUser> selectByNotificationId(Long notificationId);
    //更改状态
    int updateStatus(TrainNotificationUser notificationUser);
    //批量删除
    int batchRemove(Long notificationId);
    //批量插入
    void barchInsert(Long notificationId, Long[] userIds);
    //插入
    void insert(TrainNotificationUser notificationUser);

    List<TrainNotificationUser> selectByUserId(Long userId);

    List<TrainNotificationUser> queryMoreNotificationId(Map<String, Object> map);

    int countQueryMoreNotificationByUserId(Map<String, Object> map);
}
