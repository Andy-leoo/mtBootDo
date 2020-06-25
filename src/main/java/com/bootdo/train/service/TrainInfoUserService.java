package com.bootdo.train.service;

import com.bootdo.train.pojo.TrainInfoUser;
import com.bootdo.train.pojo.UserDO;

import java.util.List;
import java.util.Map;

public interface TrainInfoUserService {
    //根据files id查询所有列表
    List<TrainInfoUser> selectByInfoId(Long infoId);
    //更改状态
    int updateStatus(Long infoId, UserDO userDO);
    //批量删除
    int batchRemove(Long infoId);
    //批量插入
    void barchInsert(Long infoId, Long[] userIds, UserDO userDO);
    //插入
    void insert(TrainInfoUser infoUser);

    List<TrainInfoUser> selectByUserId(Long userId);

    List<TrainInfoUser> queryMoreInfoByUserId(Map<String, Object> map);

    int countQueryMoreInfoByUserId(Map<String, Object> map);
}
