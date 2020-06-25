package com.bootdo.train.service.impl;

import com.bootdo.train.dao.LinksDao;
import com.bootdo.train.pojo.Links;
import com.bootdo.train.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LinksServiceImpl implements LinksService {
    @Autowired
    private LinksDao linksDao;


    @Override
    public Links selectOne(Long id) {
        return linksDao.selectOne(id);
    }

    @Override
    public List<Links> linksList() {
        return linksDao.linksList();
    }

    @Override
    public int save(Links links) {
        return linksDao.save(links);
    }

    @Override
    public int update(Links links) {
        return linksDao.update(links);
    }

    @Override
    public int remove(Long id) {
        return linksDao.remove(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return linksDao.count(map);
    }

    @Override
    public List<Links> selectByPage(Map<String, Object> map) {
        return linksDao.selectByPage(map);
    }
}
