package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainNews;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainNewsDao {

    int insert(TrainNews record);

    List<TrainNews> selectAll(Map<String, Object> map);

    int remove(int id);

    TrainNews selectByid(int id);

    int count(Map<String, Object> map);

    int update(TrainNews trainNews);
}