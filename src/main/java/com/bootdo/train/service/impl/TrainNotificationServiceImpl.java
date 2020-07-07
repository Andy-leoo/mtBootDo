package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainNotificationDao;
import com.bootdo.train.dao.TrainNotificationUserDao;
import com.bootdo.train.pojo.TrainNotification;
import com.bootdo.train.service.TrainNotificationService;
import com.bootdo.train.service.TrainNotificationUserService;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainNotificationServiceImpl implements TrainNotificationService {

    @Autowired
    private TrainNotificationDao trainNotifiactionMapper;

    @Autowired
    private TrainNotificationUserDao trainNotificationUserDao;

    @Autowired
    private TrainNotificationUserService trainNotificationUserService;

    @Override
    public List<TrainNotification> selectAll(Map<String, Object> map) {
        return trainNotifiactionMapper.selectAll(map);
    }

    @Transactional
    public int save(TrainNotification notification, UserDO userDO) {
        notification.setCreateTime(new Date());
        notification.setCreater(userDO.getName());
        notification.setOperateTime(new Date());
        notification.setOperate(userDO.getName());
        String detail =notification.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg= RegEx_util.getImgSrc(detail);
            notification.setMainImage(mainImg);
        }
        int notificationId = trainNotifiactionMapper.insert(notification);
        trainNotificationUserService.barchInsert(Long.valueOf(notification.getId()),notification.getUserIds(),userDO);
        return notificationId;
    }

    @Transactional
    public int remove(int id) {
        trainNotificationUserDao.batchRemove(Long.valueOf(id));
        return trainNotifiactionMapper.remove(id);
    }

    @Override
    public TrainNotification selectOneById(int id) {
        return trainNotifiactionMapper.selectByid(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return trainNotifiactionMapper.count(map);
    }

    @Transactional
    @Override
    public int update(TrainNotification params, UserDO user) {
        //先删除指定user  再添加
        params.setOperate(user.getName());
        params.setOperateTime(new Date());
        trainNotificationUserService.batchRemove(Long.valueOf(params.getId()));
        trainNotificationUserService.barchInsert(Long.valueOf(params.getId()),params.getUserIds(),user);
        return trainNotifiactionMapper.update(params);
    }
}
