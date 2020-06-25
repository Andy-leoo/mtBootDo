package com.bootdo.train.dao;

import com.bootdo.train.pojo.LeaderSpeechUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LeaderSpeechUserDao {
    //根据leaderspeech id查询所有列表
    List<LeaderSpeechUser> selectByLeaderId(Long leaderId);
    //更改状态
    int updateStatus(LeaderSpeechUser leaderSpeechUser);
    //批量删除
    int batchRemove(Long leaderId);
    //批量插入
    void barchInsert(Long leaderId, Long[] userIds);
    //插入
    void insert(LeaderSpeechUser leaderSpeechUser);

    List<LeaderSpeechUser> selectByUserId(Long userId);

    List<LeaderSpeechUser> queryMoreSpeechByUserId(Map<String, Object> map);

    int countQueryMoreSpeechByUserId(Map<String, Object> map);
}
