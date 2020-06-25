package com.bootdo.train.service.impl;


import com.bootdo.train.dao.TrainTelegramUserDao;
import com.bootdo.train.pojo.TrainTelegramUser;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.TrainTelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainTelegramUserServiceImpl implements TrainTelegramUserService {

    @Autowired
    private TrainTelegramUserDao trainTelegtramUserDao;

    @Override
    public List<TrainTelegramUser> selectByTelegramId(Long telegramId) {
        return trainTelegtramUserDao.selectByTelegramId(telegramId);
    }

    @Override
    public int updateStatus(Long telegramId, UserDO userDO) {
        TrainTelegramUser trainTelegramUser = new TrainTelegramUser();
        trainTelegramUser.setTelegramId(telegramId);
        trainTelegramUser.setUserId(userDO.getUserId());
        trainTelegramUser.setStatus(1);
        trainTelegramUser.setOperateTime(new Date());
        trainTelegramUser.setOperater(userDO.getUsername());
        return trainTelegtramUserDao.updateStatus(trainTelegramUser);
    }

    @Override
    public int batchRemove(Long telegramId) {
        return trainTelegtramUserDao.batchRemove(telegramId);
    }

    @Override
    public void barchInsert(Long telegramId, Long[] userIds, UserDO userDO) {
        TrainTelegramUser trainTelegramUser = new TrainTelegramUser();
        trainTelegramUser.setCreateTime(new Date());
        trainTelegramUser.setCreater(userDO.getUsername());
        trainTelegramUser.setOperateTime(new Date());
        trainTelegramUser.setOperater(userDO.getUsername());
        trainTelegramUser.setTelegramId(telegramId);
        trainTelegramUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            trainTelegramUser.setUserId(id);
            //执行添加
            insert(trainTelegramUser);
        }
    }

    @Override
    public void insert(TrainTelegramUser trainTelegramUser) {
        trainTelegtramUserDao.insert(trainTelegramUser);
    }

    @Override
    public List<TrainTelegramUser> selectByUserId(Long userId) {
        return trainTelegtramUserDao.selectByUserId(userId);
    }

    @Override
    public List<TrainTelegramUser> queryMoreTelegramByUserId(Map<String, Object> map) {
        return trainTelegtramUserDao.queryMoreTelegramByUserId(map);
    }

    @Override
    public int countQueryMoreTelegramByUserId(Map<String, Object> map) {
        return trainTelegtramUserDao.countQueryMoreTelegramByUserId(map);
    }
}
