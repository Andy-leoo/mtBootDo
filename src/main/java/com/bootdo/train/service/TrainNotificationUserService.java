package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainNotificationUser;

import java.util.List;
import java.util.Map;

//通知通告
public interface TrainNotificationUserService {
    //根据files id查询所有列表
    List<TrainNotificationUser> selectByNotificationId(Long notificationId);
    //更改状态
    int updateStatus(Long notificationId, UserDO userDO);
    //批量删除
    int batchRemove(Long notificationId);
    //批量插入
    void barchInsert(Long notificationId, Long[] userIds, UserDO userDO);
    //插入
    void insert(TrainNotificationUser notificationUser);

    List<TrainNotificationUser> selectByUserId(Long userId);

    List<TrainNotificationUser> queryMoreNotificationId(Map<String, Object> map);

    int countQueryMoreNotificationByUserId(Map<String, Object> map);
}
