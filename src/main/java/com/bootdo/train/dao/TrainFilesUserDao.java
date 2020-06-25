package com.bootdo.train.dao;

import com.bootdo.train.pojo.TrainFilesUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TrainFilesUserDao {
    //根据files id查询所有列表
    List<TrainFilesUser> selectByFilesId(Long filesId);
    //更改状态
    int updateStatus(TrainFilesUser filesUser);
    //批量删除
    int batchRemove(Long filesId);
    //批量插入
    void barchInsert(Long filesId, Long[] userIds);
    //插入
    void insert(TrainFilesUser filesUser);

    List<TrainFilesUser> selectByUserId(Long userId);

    int checkFileByFileIdAndUserId(@Param("id") int id, @Param("userId") Long userId);

    List<TrainFilesUser> queryMoreFileByUserId(Map<String, Object> map);

    int countQueryMoreFileByUserId(Map<String, Object> map);
}
