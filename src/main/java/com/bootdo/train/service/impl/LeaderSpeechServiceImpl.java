package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.LeaderSpeechDao;
import com.bootdo.train.pojo.LeaderSpeech;
import com.bootdo.train.service.LeaderSpeechService;
import com.bootdo.train.service.LeaderSpeechUserService;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LeaderSpeechServiceImpl implements LeaderSpeechService {

    @Autowired
    private LeaderSpeechDao LeaderSpeechDao;
    @Autowired
    private LeaderSpeechUserService leaderSpeechUserService;

    public List<LeaderSpeech> selectAll(Map<String, Object> map){
        return LeaderSpeechDao.selectAll(map);
    }

    @Transactional
    public int save(LeaderSpeech leaderSpeech, UserDO userDO){
        leaderSpeech.setCreateTime(new Date());
        leaderSpeech.setCreater(userDO.getUsername());
        leaderSpeech.setOperateTime(new Date());
        leaderSpeech.setOperate(userDO.getUsername());
        String detail =leaderSpeech.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg= RegEx_util.getImgSrc(detail);
            leaderSpeech.setMainImage(mainImg);
        }
        int insert = LeaderSpeechDao.insert(leaderSpeech);
        leaderSpeechUserService.barchInsert(Long.valueOf(leaderSpeech.getId()),leaderSpeech.getUserIds(),userDO);
        return insert;
    }

    @Transactional
    public int remove(int id) {
       leaderSpeechUserService.batchRemove(Long.valueOf(id));
       return LeaderSpeechDao.remove(id);
    }

    @Override
    public LeaderSpeech selectById(int id) {
        return LeaderSpeechDao.selectById(id);
    }

    @Transactional
    @Override
    public int update(LeaderSpeech leaderSpeech, UserDO user) {
        //先删除指定user  再添加
        leaderSpeech.setOperate(user.getName());
        leaderSpeech.setOperateTime(new Date());
        leaderSpeechUserService.batchRemove(Long.valueOf(leaderSpeech.getId()));
        leaderSpeechUserService.barchInsert(Long.valueOf(leaderSpeech.getId()),leaderSpeech.getUserIds(),user);
        return LeaderSpeechDao.update(leaderSpeech);
    }

    @Override
    public int count(Map<String, Object> map) {
        return LeaderSpeechDao.count(map);
    }
}
