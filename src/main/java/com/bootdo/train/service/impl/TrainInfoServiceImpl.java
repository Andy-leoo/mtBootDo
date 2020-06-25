package com.bootdo.train.service.impl;

import com.bootdo.train.dao.TrainInfoDao;
import com.bootdo.train.dao.TrainInfoUserDao;
import com.bootdo.train.pojo.TrainInfo;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.TrainInfoService;
import com.bootdo.train.service.TrainInfoUserService;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainInfoServiceImpl implements TrainInfoService {

    @Autowired
    private TrainInfoDao trainInfoDao;
    @Autowired
    private TrainInfoUserDao trainInfoUserDao;
    @Autowired
    private TrainInfoUserService trainInfoUserService;

    public List<TrainInfo> selectAll(Map<String, Object> map){
        return trainInfoDao.selectAll(map);
    }

    @Transactional
    public int save(TrainInfo info, UserDO userDO){
        info.setCreateTime(new Date());
        info.setCreater(userDO.getUsername());
        info.setOperateTime(new Date());
        info.setOperate(userDO.getUsername());
        String detail =info.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg= RegEx_util.getImgSrc(detail);
            info.setMainImage(mainImg);
        }
        int infoId = trainInfoDao.insert(info);
        trainInfoUserService.barchInsert(Long.valueOf(info.getId()),info.getUserIds(),userDO);
        return infoId;
    }

    @Transactional
    public int remove(int id) {
        trainInfoUserDao.batchRemove(Long.valueOf(id));
       return trainInfoDao.remove(id);
    }

    @Override
    public TrainInfo selectById(int id) {
        return trainInfoDao.selectById(id);
    }

    @Transactional
    @Override
    public int update(TrainInfo trainInfo, UserDO user) {
        //先删除指定user  再添加
        trainInfo.setOperate(user.getName());
        trainInfo.setOperateTime(new Date());
        trainInfoUserService.batchRemove(Long.valueOf(trainInfo.getId()));
        trainInfoUserService.barchInsert(Long.valueOf(trainInfo.getId()),trainInfo.getUserIds(),user);
        return trainInfoDao.update(trainInfo);
    }

    @Override
    public int count(Map<String, Object> map) {
        return trainInfoDao.count(map);
    }
}
