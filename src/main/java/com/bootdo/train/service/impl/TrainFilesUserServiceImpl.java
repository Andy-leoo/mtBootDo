package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainFilesUserDao;
import com.bootdo.train.pojo.TrainFilesUser;
import com.bootdo.train.service.TrainFilesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainFilesUserServiceImpl implements TrainFilesUserService {

    @Autowired
    private TrainFilesUserDao trainFilesUserDao;


    @Override
    public List<TrainFilesUser> selectByFilesId(Long filesId) {
        return trainFilesUserDao.selectByFilesId(filesId);
    }

    @Override
    public int updateStatus(Long filesId, UserDO userDO, int status) {
        TrainFilesUser filesUser = new TrainFilesUser();
        filesUser.setFilesId(filesId);
        filesUser.setUserId(userDO.getUserId());
        filesUser.setStatus(status);
        filesUser.setOperateTime(new Date());
        filesUser.setOperater(userDO.getName());
        return trainFilesUserDao.updateStatus(filesUser);
    }

    @Override
    public int batchRemove(Long filesId) {
        return trainFilesUserDao.batchRemove(filesId);
    }

    @Override
    public void barchInsert(Long filesId, Long[] userIds,UserDO userDO) {
        TrainFilesUser filesUser = new TrainFilesUser();
        filesUser.setCreateTime(new Date());
        filesUser.setCreater(userDO.getUsername());
        filesUser.setOperateTime(new Date());
        filesUser.setOperater(userDO.getUsername());
        filesUser.setFilesId(filesId);
        filesUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            filesUser.setUserId(id);
            //执行添加
            insert(filesUser);
        }
    }

    @Override
    public void insert(TrainFilesUser filesUser) {
        trainFilesUserDao.insert(filesUser);
    }

    @Override
    public List<TrainFilesUser> selectByUserId(Long userId) {
        return trainFilesUserDao.selectByUserId(userId);
    }

    @Override
    public int checkFileByFileIdAndUserId(int id, Long userId) {
        int count = trainFilesUserDao.checkFileByFileIdAndUserId(id,userId);
        return count;
    }

    @Override
    public List<TrainFilesUser> queryMoreFileByUserId(Map<String, Object> map) {
        return trainFilesUserDao.queryMoreFileByUserId(map);
    }

    @Override
    public int countQueryMoreFileByUserId(Map<String, Object> map) {
        return trainFilesUserDao.countQueryMoreFileByUserId(map);
    }
}
