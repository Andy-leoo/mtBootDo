package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.LeaderSpeech;

import java.util.List;
import java.util.Map;

public interface LeaderSpeechService {
    List<LeaderSpeech> selectAll(Map<String, Object> map);
    int save(LeaderSpeech news, UserDO userDO);
    int remove(int id);

    LeaderSpeech selectById(int id);

    int update(LeaderSpeech leaderSpeech, UserDO user);

    int count(Map<String, Object> map);
}
