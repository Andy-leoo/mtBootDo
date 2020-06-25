package com.bootdo.train.service.impl;

import com.bootdo.train.dao.LeaderSpeechUserDao;
import com.bootdo.train.pojo.LeaderSpeechUser;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.LeaderSpeechUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LeaderSpeechUserServiceImpl implements LeaderSpeechUserService {
    @Autowired
    private LeaderSpeechUserDao leaderSpeechUserDao;

    @Override
    public List<LeaderSpeechUser> selectByLeaderId(Long leaderId) {
        return leaderSpeechUserDao.selectByLeaderId(leaderId);
    }

    @Override
    public int updateStatus(Long leaderId, UserDO userDO) {
        LeaderSpeechUser leaderSpeechUser = new LeaderSpeechUser();
        leaderSpeechUser.setLeaderId(leaderId);
        leaderSpeechUser.setUserId(userDO.getUserId());
        leaderSpeechUser.setStatus(1);
        leaderSpeechUser.setOperateTime(new Date());
        leaderSpeechUser.setOperater(userDO.getUsername());
        return leaderSpeechUserDao.updateStatus(leaderSpeechUser);
    }

    @Override
    public int batchRemove(Long leaderId) {
        return leaderSpeechUserDao.batchRemove(leaderId);
    }

    @Override
    public void barchInsert(Long leaderId, Long[] userIds, UserDO userDO) {
        LeaderSpeechUser leaderSpeechUser = new LeaderSpeechUser();
        leaderSpeechUser.setCreateTime(new Date());
        leaderSpeechUser.setCreater(userDO.getUsername());
        leaderSpeechUser.setOperateTime(new Date());
        leaderSpeechUser.setOperater(userDO.getUsername());
        leaderSpeechUser.setLeaderId(leaderId);
        leaderSpeechUser.setStatus(0);//全部未查看
        for (int userId = 0; userId < userIds.length; userId++) {
            Long id = userIds[userId];
            leaderSpeechUser.setUserId(id);
            //执行添加
            insert(leaderSpeechUser);
        }
    }

    @Override
    public void insert(LeaderSpeechUser leaderSpeechUser) {
        leaderSpeechUserDao.insert(leaderSpeechUser);
    }

    @Override
    public List<LeaderSpeechUser> selectByUserId(Long userId) {
        return leaderSpeechUserDao.selectByUserId(userId);
    }

    @Override
    public List<LeaderSpeechUser> queryMoreSpeechByUserId(Map<String, Object> map) {
        return leaderSpeechUserDao.queryMoreSpeechByUserId(map);
    }

    @Override
    public int countQueryMoreSpeechByUserId(Map<String, Object> map) {
        return leaderSpeechUserDao.countQueryMoreSpeechByUserId(map);
    }
}
