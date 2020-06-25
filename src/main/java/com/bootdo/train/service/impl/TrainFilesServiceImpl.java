package com.bootdo.train.service.impl;

import com.bootdo.train.dao.TrainFilesDao;
import com.bootdo.train.dao.TrainFilesUserDao;
import com.bootdo.train.pojo.TrainFiles;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.TrainFilesService;
import com.bootdo.train.service.TrainFilesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainFilesServiceImpl implements TrainFilesService {

    @Autowired
    private TrainFilesDao trainFilesDao;
    @Autowired
    private TrainFilesUserService filesUserService;
    @Autowired
    private TrainFilesUserDao trainFilesUserDao;

    @Override
    public List<TrainFiles> selectAll(Map<String, Object> map) {
        return trainFilesDao.selectAll(map);
    }

    @Transactional
    public int save(TrainFiles files, UserDO userDO){
        files.setCreateTime(new Date());
        files.setCreater(userDO.getUsername());
        files.setOperateTime(new Date());
        files.setOperator(userDO.getName());
        int fileId = trainFilesDao.insert(files);
        // 需要查看得人入表
        filesUserService.barchInsert(Long.valueOf(files.getId()),files.getUserIds(),userDO);
        return fileId;
    }


    @Transactional
    public int remove(int id) {
        trainFilesUserDao.batchRemove(Long.valueOf(id));
       return trainFilesDao.remove(id);
    }

    @Override
    public TrainFiles selectByIdAndUserId(int id, Long userId) {
        return trainFilesDao.selectByIdAndUserId(id,userId);
    }

    @Override
    public TrainFiles selectOneById(int id) {
        return trainFilesDao.selectById(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return trainFilesDao.count(map);
    }

    @Transactional
    @Override
    public int update(TrainFiles trainFiles, UserDO user) {
        //先删除指定user  再添加
        trainFiles.setOperator(user.getName());
        trainFiles.setOperateTime(new Date());
        filesUserService.batchRemove(Long.valueOf(trainFiles.getId()));
        filesUserService.barchInsert(Long.valueOf(trainFiles.getId()),trainFiles.getUserIds(),user);
        return trainFilesDao.update(trainFiles);
    }


}
