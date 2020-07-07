package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainEwarningUser;

import java.util.List;
import java.util.Map;

public interface TrainEwarningUserService {
    //根据files id查询所有列表
    List<TrainEwarningUser> selectByEwarningId(Long ewarningId);
    //更改状态
    int updateStatus(Long ewarningId, UserDO userDO);
    //批量删除
    int batchRemove(Long ewarningId);
    //批量插入
    void barchInsert(Long ewarningId, Long[] userIds, UserDO userDO);
    //插入
    void insert(TrainEwarningUser newsUser);

    List<TrainEwarningUser> selectByUserId(Long userId);

    List<TrainEwarningUser> queryMoreEwarningByUserId(Map<String, Object> map);

    int countQueryMoreEwarningByUserId(Map<String, Object> map);
}
