package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainNewsDao;
import com.bootdo.train.dao.TrainNewsUserDao;
import com.bootdo.train.pojo.TrainNews;
import com.bootdo.train.service.TrainNewsService;
import com.bootdo.train.service.TrainNewsUserService;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainNewsServiceImpl implements TrainNewsService {

    @Autowired
    private TrainNewsDao trainNewsMapper;
    @Autowired
    private TrainNewsUserDao trainNewsUserDao;
    @Autowired
    private TrainNewsUserService trainNewsUserService;

    public List<TrainNews> selectAll(Map<String, Object> map){
        return trainNewsMapper.selectAll(map);
    }

    @Transactional
    public int save(TrainNews news, UserDO userDO){
        news.setCreateTime(new Date());
        news.setCreater(userDO.getName());
        news.setOperateTime(new Date());
        news.setOperate(userDO.getName());
        String detail =news.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg=RegEx_util.getImgSrc(detail);
            news.setMainImage(mainImg);
        }
        int newsId = trainNewsMapper.insert(news);

        trainNewsUserService.barchInsert(Long.valueOf(news.getId()),news.getUserIds(),userDO);
        return newsId;
    }

    @Transactional
    public int remove(int id) {
       trainNewsUserDao.batchRemove(Long.valueOf(id));
       return trainNewsMapper.remove(id);
    }

    @Override
    public TrainNews selectOneById(int id) {
        return trainNewsMapper.selectByid(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return trainNewsMapper.count(map);
    }

    @Transactional
    @Override
    public int update(TrainNews trainNews, UserDO user) {
        //先删除指定user  再添加
        trainNews.setOperate(user.getName());
        trainNews.setOperateTime(new Date());
        String detail =trainNews.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg=RegEx_util.getImgSrc(detail);
            trainNews.setMainImage(mainImg);
        }
        trainNewsUserService.batchRemove(Long.valueOf(trainNews.getId()));
        trainNewsUserService.barchInsert(Long.valueOf(trainNews.getId()),trainNews.getUserIds(),user);
        return trainNewsMapper.update(trainNews);
    }
}
