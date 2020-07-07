package com.bootdo.train.service.impl;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.dao.RollImgDao;
import com.bootdo.train.pojo.ScrollGraph;
import com.bootdo.train.service.RollImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RollImgServiceImpl implements RollImgService {

    @Autowired
    private RollImgDao rollImgDao;

    public List<ScrollGraph> selectAll(){
        return rollImgDao.selectAll();
    }

    public int save(ScrollGraph files, UserDO userDO){
        files.setCreateTime(new Date());
        files.setCreater(userDO.getUsername());
        files.setOperateTime(new Date());
        files.setOperate(userDO.getUsername());
        return rollImgDao.insert(files);
    }

    public ScrollGraph selectOne(){
        return rollImgDao.selectAll().get(0);
    }

    public int remove(int id) {
       return rollImgDao.remove(id);
    }
}
