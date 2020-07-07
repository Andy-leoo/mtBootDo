package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainNewsUserDao;
import com.bootdo.train.pojo.TrainNewsUser;
import com.bootdo.train.service.TrainNewsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainNewsUserServiceImpl implements TrainNewsUserService {

    @Autowired
    private TrainNewsUserDao TrainNewsUserDao;


    @Override
    public List<TrainNewsUser> selectByNewsId(Long newsId) {
        return TrainNewsUserDao.selectByNewsId(newsId);
    }

    @Override
    public int updateStatus(Long newsId, UserDO userDO) {
        TrainNewsUser newsUser = new TrainNewsUser();
        newsUser.setNewsId(newsId);
        newsUser.setUserId(userDO.getUserId());
        newsUser.setStatus(1);
        newsUser.setOperateTime(new Date());
        newsUser.setOperater(userDO.getUsername());
        return TrainNewsUserDao.updateStatus(newsUser);
    }

    @Override
    public int batchRemove(Long newsId) {
        return TrainNewsUserDao.batchRemove(newsId);
    }

    @Override
    public void barchInsert(Long newsId, Long[] userIds,UserDO userDO) {
        TrainNewsUser newsUser = new TrainNewsUser();
        newsUser.setCreateTime(new Date());
        newsUser.setCreater(userDO.getUsername());
        newsUser.setOperateTime(new Date());
        newsUser.setOperater(userDO.getUsername());
        newsUser.setNewsId(newsId);
        newsUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            newsUser.setUserId(id);
            //执行添加
            insert(newsUser);
        }
    }

    @Override
    public void insert(TrainNewsUser newsUser) {
        TrainNewsUserDao.insert(newsUser);
    }

    @Override
    public List<TrainNewsUser> selectByUserId(Long userId) {
        return TrainNewsUserDao.selectByUserId(userId);
    }

    @Override
    public List<TrainNewsUser> queryMoreNewsByUserId(Map<String, Object> map) {
        return TrainNewsUserDao.queryMoreNewsByUserId(map);
    }

    @Override
    public int countQueryMoreNewsByUserId(Map<String, Object> map) {
        return TrainNewsUserDao.countQueryMoreNewsByUserId(map);
    }
}
