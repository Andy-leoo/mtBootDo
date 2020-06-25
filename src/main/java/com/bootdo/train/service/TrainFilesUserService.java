package com.bootdo.train.service;

import com.bootdo.train.pojo.TrainFilesUser;
import com.bootdo.train.pojo.UserDO;

import java.util.List;
import java.util.Map;

public interface TrainFilesUserService {
    //根据files id查询所有列表
    List<TrainFilesUser> selectByFilesId(Long filesId);
    //更改状态
    int updateStatus(Long filesId, UserDO userDO, int status);
    //批量删除
    int batchRemove(Long filesId);
    //批量插入
    void barchInsert(Long filesId, Long[] userIds, UserDO userDO);
    //插入
    void insert(TrainFilesUser filesUser);

    List<TrainFilesUser> selectByUserId(Long userId);

    int checkFileByFileIdAndUserId(int filesId, Long userId);

    List<TrainFilesUser> queryMoreFileByUserId(Map<String, Object> map);

    int countQueryMoreFileByUserId(Map<String, Object> map);
}
