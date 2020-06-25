package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainEwarning;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//安全预警
@Mapper
public interface TrainEwarningDao {

    int insert(TrainEwarning record);

    List<TrainEwarning> selectAll(Map<String, Object> map);

    int remove(int id);

    TrainEwarning selectByid(int id);

    int count(Map<String, Object> map);

    int update(TrainEwarning trainEwarning);
}
