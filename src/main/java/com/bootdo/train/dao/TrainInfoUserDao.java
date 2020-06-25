package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainInfoUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainInfoUserDao {
    //根据Info id查询所有列表
    List<TrainInfoUser> selectByInfoId(Long infoId);
    //更改状态
    int updateStatus(TrainInfoUser infoUser);
    //批量删除
    int batchRemove(Long infoId);
    //批量插入
    void barchInsert(Long infoId, Long[] userIds);
    //插入
    void insert(TrainInfoUser infoUser);

    List<TrainInfoUser> selectByUserId(Long userId);

    List<TrainInfoUser> queryMoreInfoByUserId(Map<String, Object> map);

    int countQueryMoreInfoByUserId(Map<String, Object> map);
}
