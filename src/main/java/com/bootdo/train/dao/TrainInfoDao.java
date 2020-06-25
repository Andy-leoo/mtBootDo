package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainInfoDao {

    int insert(TrainInfo record);

    List<TrainInfo> selectAll(Map<String, Object> map);

    int remove(int id);

    TrainInfo selectById(int id);

    int update(TrainInfo trainInfo);

    int count(Map<String, Object> map);
}