package com.bootdo.train.dao;

import com.bootdo.train.pojo.LeaderSpeech;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LeaderSpeechDao {

    int insert(LeaderSpeech record);

    List<LeaderSpeech> selectAll(Map<String, Object> map);

    int remove(int id);

    LeaderSpeech selectById(int id);

    int count(Map<String, Object> map);

    int update(LeaderSpeech leaderSpeech);

}