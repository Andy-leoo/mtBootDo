package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainEwarningUserDao;
import com.bootdo.train.pojo.TrainEwarningUser;
import com.bootdo.train.service.TrainEwarningUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainEwarningUserServiceImpl implements TrainEwarningUserService {
   @Autowired
    private TrainEwarningUserDao trainEwarningUserDao;

    @Override
    public List<TrainEwarningUser> selectByEwarningId(Long ewarningId) {
        return trainEwarningUserDao.selectByEwarningId(ewarningId);
    }

    @Override
    public int updateStatus(Long ewarningId, UserDO userDO) {
        TrainEwarningUser rainEwarningUser = new TrainEwarningUser();
        rainEwarningUser.setEwarningId(ewarningId);
        rainEwarningUser.setUserId(userDO.getUserId());
        rainEwarningUser.setStatus(1);
        rainEwarningUser.setOperateTime(new Date());
        rainEwarningUser.setOperater(userDO.getUsername());
        return trainEwarningUserDao.updateStatus(rainEwarningUser);
    }

    @Override
    public int batchRemove(Long ewarningId) {
        return trainEwarningUserDao.batchRemove(ewarningId);
    }

    @Override
    public void barchInsert(Long ewarningId, Long[] userIds, UserDO userDO) {
        TrainEwarningUser rainEwarningUser = new TrainEwarningUser();
        rainEwarningUser.setCreateTime(new Date());
        rainEwarningUser.setCreater(userDO.getUsername());
        rainEwarningUser.setOperateTime(new Date());
        rainEwarningUser.setOperater(userDO.getUsername());
        rainEwarningUser.setEwarningId(ewarningId);
        rainEwarningUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            rainEwarningUser.setUserId(id);
            //执行添加
            insert(rainEwarningUser);
        }
    }

    @Override
    public void insert(TrainEwarningUser trainEwarningUser) {
        trainEwarningUserDao.insert(trainEwarningUser);
    }

    @Override
    public List<TrainEwarningUser> selectByUserId(Long userId) {
        return trainEwarningUserDao.selectByUserId(userId);
    }

    @Override
    public List<TrainEwarningUser> queryMoreEwarningByUserId(Map<String, Object> map) {
        return trainEwarningUserDao.queryMoreEwarningByUserId(map);
    }

    @Override
    public int countQueryMoreEwarningByUserId(Map<String, Object> map) {
        return trainEwarningUserDao.countQueryMoreEwarningByUserId(map);
    }
}
