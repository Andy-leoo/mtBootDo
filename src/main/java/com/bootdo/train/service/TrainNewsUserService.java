package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainNewsUser;

import java.util.List;
import java.util.Map;

public interface TrainNewsUserService {
    //根据files id查询所有列表
    List<TrainNewsUser> selectByNewsId(Long newsId);
    //更改状态
    int updateStatus(Long newsId, UserDO userDO);
    //批量删除
    int batchRemove(Long newsId);
    //批量插入
    void barchInsert(Long newsId, Long[] userIds, UserDO userDO);
    //插入
    void insert(TrainNewsUser newsUser);

    List<TrainNewsUser> selectByUserId(Long userId);

    List<TrainNewsUser> queryMoreNewsByUserId(Map<String, Object> map);

    int countQueryMoreNewsByUserId(Map<String, Object> map);
}
