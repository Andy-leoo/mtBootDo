package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainFilesDao {

    int insert(TrainFiles record);

    List<TrainFiles> selectAll(Map<String, Object> map);

    int remove(int id);

    TrainFiles selectById(@Param("id") int id);

    TrainFiles selectByIdAndUserId(@Param("id") int id, @Param("userId") Long userId);
    int count(Map<String, Object> map);

    int update(TrainFiles params);
}