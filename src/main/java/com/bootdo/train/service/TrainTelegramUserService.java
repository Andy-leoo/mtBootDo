package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainTelegramUser;

import java.util.List;
import java.util.Map;

public interface TrainTelegramUserService {
    //根据id查询所有列表
    List<TrainTelegramUser> selectByTelegramId(Long telegramId);
    //更改状态
    int updateStatus(Long telegramId, UserDO userDO);
    //批量删除
    int batchRemove(Long telegramId);
    //批量插入
    void barchInsert(Long telegramId, Long[] userIds, UserDO userDO);
    //插入
    void insert(TrainTelegramUser newsUser);

    List<TrainTelegramUser> selectByUserId(Long userId);

    List<TrainTelegramUser> queryMoreTelegramByUserId(Map<String, Object> map);

    int countQueryMoreTelegramByUserId(Map<String, Object> map);
}
