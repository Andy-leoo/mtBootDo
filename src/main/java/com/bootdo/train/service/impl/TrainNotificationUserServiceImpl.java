package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainNotificationUserDao;
import com.bootdo.train.pojo.TrainNotificationUser;
import com.bootdo.train.service.TrainNotificationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainNotificationUserServiceImpl implements TrainNotificationUserService {
    @Autowired
    private TrainNotificationUserDao trainNotificationUserDao;

    @Override
    public List<TrainNotificationUser> selectByNotificationId(Long notificationId) {
        return trainNotificationUserDao.selectByNotificationId(notificationId);
    }

    @Override
    public int updateStatus(Long notificationId, UserDO userDO) {
        TrainNotificationUser trainNotificationUser = new TrainNotificationUser();
        trainNotificationUser.setNotificationId(notificationId);
        trainNotificationUser.setUserId(userDO.getUserId());
        trainNotificationUser.setStatus(1);
        trainNotificationUser.setOperateTime(new Date());
        trainNotificationUser.setOperater(userDO.getUsername());
        return trainNotificationUserDao.updateStatus(trainNotificationUser);
    }

    @Override
    public int batchRemove(Long notificationId) {
        return trainNotificationUserDao.batchRemove(notificationId);
    }

    @Override
    public void barchInsert(Long notificationId, Long[] userIds, UserDO userDO) {
        TrainNotificationUser trainNotificationUser = new TrainNotificationUser();
        trainNotificationUser.setCreateTime(new Date());
        trainNotificationUser.setCreater(userDO.getUsername());
        trainNotificationUser.setOperateTime(new Date());
        trainNotificationUser.setOperater(userDO.getUsername());
        trainNotificationUser.setNotificationId(notificationId);
        trainNotificationUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            trainNotificationUser.setUserId(id);
            //执行添加
            insert(trainNotificationUser);
        }
    }

    @Override
    public void insert(TrainNotificationUser notificationUser) {
        trainNotificationUserDao.insert(notificationUser);
    }

    @Override
    public List<TrainNotificationUser> selectByUserId(Long userId) {
        return trainNotificationUserDao.selectByUserId(userId);
    }

    @Override
    public List<TrainNotificationUser> queryMoreNotificationId(Map<String, Object> map) {
        return trainNotificationUserDao.queryMoreNotificationId(map);
    }

    @Override
    public int countQueryMoreNotificationByUserId(Map<String, Object> map) {
        return trainNotificationUserDao.countQueryMoreNotificationByUserId(map);
    }
}
