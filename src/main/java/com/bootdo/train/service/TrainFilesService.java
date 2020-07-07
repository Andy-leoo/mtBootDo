package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.TrainFiles;

import java.util.List;
import java.util.Map;

public interface TrainFilesService {
    List<TrainFiles> selectAll(Map<String, Object> map);
    int save(TrainFiles news, UserDO userDO);
    int remove(int id);
    TrainFiles selectOneById(int id);

    TrainFiles selectByIdAndUserId(int id, Long userId);

    int count(Map<String, Object> map);

    int update(TrainFiles params, UserDO user);
}
