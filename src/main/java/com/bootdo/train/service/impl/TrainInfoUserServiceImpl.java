package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainInfoUserDao;
import com.bootdo.train.pojo.TrainInfoUser;
import com.bootdo.train.service.TrainInfoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainInfoUserServiceImpl implements TrainInfoUserService {

    @Autowired
    private TrainInfoUserDao trainInfoUserDao;


    @Override
    public List<TrainInfoUser> selectByInfoId(Long infoId) {
        return trainInfoUserDao.selectByInfoId(infoId);
    }

    @Override
    public int updateStatus(Long infoId, UserDO userDO) {
        TrainInfoUser infoUser = new TrainInfoUser();
        infoUser.setInfoId(infoId);
        infoUser.setUserId(userDO.getUserId());
        infoUser.setStatus(1);
        infoUser.setOperateTime(new Date());
        infoUser.setOperater(userDO.getUsername());
        return trainInfoUserDao.updateStatus(infoUser);
    }

    @Override
    public int batchRemove(Long infoId) {
        return trainInfoUserDao.batchRemove(infoId);
    }

    @Override
    public void barchInsert(Long infoId, Long[] userIds,UserDO userDO) {
        TrainInfoUser infoUser = new TrainInfoUser();
        infoUser.setCreateTime(new Date());
        infoUser.setCreater(userDO.getUsername());
        infoUser.setOperateTime(new Date());
        infoUser.setOperater(userDO.getUsername());
        infoUser.setInfoId(infoId);
        infoUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            infoUser.setUserId(id);
            //执行添加
            insert(infoUser);
        }
    }

    @Override
    public void insert(TrainInfoUser infoUser) {
        trainInfoUserDao.insert(infoUser);
    }

    @Override
    public List<TrainInfoUser> selectByUserId(Long userId) {
        return trainInfoUserDao.selectByUserId(userId);
    }

    @Override
    public List<TrainInfoUser> queryMoreInfoByUserId(Map<String, Object> map) {
        return trainInfoUserDao.queryMoreInfoByUserId(map);
    }

    @Override
    public int countQueryMoreInfoByUserId(Map<String, Object> map) {
        return trainInfoUserDao.countQueryMoreInfoByUserId(map);
    }
}
