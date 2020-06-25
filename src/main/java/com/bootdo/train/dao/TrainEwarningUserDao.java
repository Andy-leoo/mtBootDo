package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainEwarningUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//安全预警
@Mapper
public interface TrainEwarningUserDao {

    //根据news id查询所有列表
    List<TrainEwarningUser> selectByEwarningId(Long ewarningId);
    //更改状态
    int updateStatus(TrainEwarningUser ewarningUser);
    //批量删除
    int batchRemove(Long ewarningId);
    //批量插入
    void barchInsert(Long ewarningId, Long[] userIds);
    //插入
    void insert(TrainEwarningUser ewarningUser);

    List<TrainEwarningUser> selectByUserId(Long userId);

    List<TrainEwarningUser> queryMoreEwarningByUserId(Map<String, Object> map);

    int countQueryMoreEwarningByUserId(Map<String, Object> map);
}
