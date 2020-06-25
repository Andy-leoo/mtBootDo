package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainNewsUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainNewsUserDao {
    //根据news id查询所有列表
    List<TrainNewsUser> selectByNewsId(Long newsId);
    //更改状态
    int updateStatus(TrainNewsUser newsUser);
    //批量删除
    int batchRemove(Long newsId);
    //批量插入
    void barchInsert(Long newsId, Long[] userIds);
    //插入
    void insert(TrainNewsUser newsUser);

    List<TrainNewsUser> selectByUserId(Long userId);

    List<TrainNewsUser> queryMoreNewsByUserId(Map<String, Object> map);

    int countQueryMoreNewsByUserId(Map<String, Object> map);
}
