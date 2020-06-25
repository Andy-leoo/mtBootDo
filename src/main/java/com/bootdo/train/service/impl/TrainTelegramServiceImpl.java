package com.bootdo.train.service.impl;

import com.bootdo.train.dao.TrainTelegramDao;
import com.bootdo.train.dao.TrainTelegramUserDao;
import com.bootdo.train.pojo.TrainTelegram;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.TrainTelegramService;
import com.bootdo.train.service.TrainTelegramUserService;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainTelegramServiceImpl implements TrainTelegramService {

    @Autowired
    private TrainTelegramDao trainTelegramMapper;
    @Autowired
    private TrainTelegramUserDao trainTelegramUserDao;
    @Autowired
    private TrainTelegramUserService trainTelegramUserService;

    @Override
    public List<TrainTelegram> selectAll(Map<String, Object> map) {
        return trainTelegramMapper.selectAll(map);
    }

    @Transactional
    public int save(TrainTelegram telegram, UserDO userDO) {
        telegram.setCreateTime(new Date());
        telegram.setCreater(userDO.getName());
        telegram.setOperateTime(new Date());
        telegram.setOperate(userDO.getName());
        String detail =telegram.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg= RegEx_util.getImgSrc(detail);
            telegram.setMainImage(mainImg);
        }
        int newsId = trainTelegramMapper.insert(telegram);

        trainTelegramUserService.barchInsert(Long.valueOf(telegram.getId()),telegram.getUserIds(),userDO);
        return newsId;
    }

    @Transactional
    public int remove(int id) {
        trainTelegramUserDao.batchRemove(Long.valueOf(id));
        return trainTelegramMapper.remove(id);
    }

    @Override
    public TrainTelegram selectOneById(int id) {
        return trainTelegramMapper.selectByid(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return trainTelegramMapper.count(map);
    }

    @Transactional
    public int update(TrainTelegram params, UserDO user) {
        //先删除指定user  再添加
        params.setOperate(user.getName());
        params.setOperateTime(new Date());
        trainTelegramUserService.batchRemove(Long.valueOf(params.getId()));
        trainTelegramUserService.barchInsert(Long.valueOf(params.getId()),params.getUserIds(),user);
        return trainTelegramMapper.update(params);
    }
}
