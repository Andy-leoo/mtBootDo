package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainTelegramUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//转发电报
@Mapper
public interface TrainTelegramUserDao {

    //根据news id查询所有列表
    List<TrainTelegramUser> selectByTelegramId(Long telegramId);
    //更改状态
    int updateStatus(TrainTelegramUser telegramUser);
    //批量删除
    int batchRemove(Long telegramId);
    //批量插入
    void barchInsert(Long telegramId, Long[] userIds);
    //插入
    void insert(TrainTelegramUser telegramUser);

    List<TrainTelegramUser> selectByUserId(Long userId);

    List<TrainTelegramUser> queryMoreTelegramByUserId(Map<String, Object> map);

    int countQueryMoreTelegramByUserId(Map<String, Object> map);
}
