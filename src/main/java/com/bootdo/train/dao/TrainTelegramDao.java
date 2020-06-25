package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainTelegram;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//转发电报
@Mapper
public interface TrainTelegramDao {

    int insert(TrainTelegram record);

    List<TrainTelegram> selectAll(Map<String, Object> map);

    int remove(int id);

    TrainTelegram selectByid(int id);

    int count(Map<String, Object> map);

    int update(TrainTelegram trainTelegram);
}
