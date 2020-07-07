package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.TrainEwarningDao;
import com.bootdo.train.dao.TrainEwarningUserDao;
import com.bootdo.train.pojo.TrainEwarning;
import com.bootdo.train.service.TrainEwarningService;
import com.bootdo.train.service.TrainEwarningUserService;
import com.bootdo.train.utils.RegEx_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainEwarningServiceImpl implements TrainEwarningService {

    @Autowired
    private TrainEwarningDao trainEwarningMapper;
    @Autowired
    private TrainEwarningUserDao trainEwarningUserDao;
    @Autowired
    private TrainEwarningUserService trainEwarningUserService;

    @Override
    public List<TrainEwarning> selectAll(Map<String, Object> map) {
        return trainEwarningMapper.selectAll(map);
    }

    @Transactional
    public int save(TrainEwarning trainEwarning, UserDO userDO) {
        trainEwarning.setCreateTime(new Date());
        trainEwarning.setCreater(userDO.getName());
        trainEwarning.setOperateTime(new Date());
        trainEwarning.setOperate(userDO.getName());
        String detail =trainEwarning.getDetail();
        if(null!=detail){//保存主图 默认第一张图
            String mainImg= RegEx_util.getImgSrc(detail);
            trainEwarning.setMainImage(mainImg);
        }
        int ewarningId = trainEwarningMapper.insert(trainEwarning);

        trainEwarningUserService.barchInsert(Long.valueOf(trainEwarning.getId()),trainEwarning.getUserIds(),userDO);
        return ewarningId;
    }

    @Transactional
    public int remove(int id) {
        trainEwarningUserDao.batchRemove(Long.valueOf(id));
        return trainEwarningMapper.remove(id);
    }

    @Override
    public TrainEwarning selectOneById(int id) {
        return trainEwarningMapper.selectByid(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return trainEwarningMapper.count(map);
    }

    @Transactional
    @Override
    public int update(TrainEwarning params, UserDO user) {
        //先删除指定user  再添加
        params.setOperate(user.getName());
        params.setOperateTime(new Date());
        trainEwarningUserService.batchRemove(Long.valueOf(params.getId()));
        trainEwarningUserService.barchInsert(Long.valueOf(params.getId()),params.getUserIds(),user);
        return trainEwarningMapper.update(params);
    }
}
