package com.bootdo.train.service;

import com.bootdo.train.pojo.LeaderSpeechUser;
import com.bootdo.train.pojo.UserDO;

import java.util.List;
import java.util.Map;

public interface LeaderSpeechUserService {
    //根据files id查询所有列表
    List<LeaderSpeechUser> selectByLeaderId(Long leaderId);
    //更改状态
    int updateStatus(Long leaderId, UserDO userDO);
    //批量删除
    int batchRemove(Long leaderId);
    //批量插入
    void barchInsert(Long leaderId, Long[] userIds, UserDO userDO);
    //插入
    void insert(LeaderSpeechUser leaderSpeechUser);

    List<LeaderSpeechUser> selectByUserId(Long userId);

    List<LeaderSpeechUser> queryMoreSpeechByUserId(Map<String, Object> map);

    int countQueryMoreSpeechByUserId(Map<String, Object> map);
}
