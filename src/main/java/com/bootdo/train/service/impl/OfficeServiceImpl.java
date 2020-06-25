package com.bootdo.train.service.impl;

import com.bootdo.train.dao.OfficeDao;
import com.bootdo.train.pojo.Office;
import com.bootdo.train.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OfficeServiceImpl implements OfficeService {
    @Autowired
    private OfficeDao officeDao;
    @Override
    public Office selectOne(Long id) {
        return officeDao.selectOne(id);
    }

    @Override
    public List<Office> officeList() {
        return officeDao.officeList();
    }

    @Override
    public int save(Office office) {
        return officeDao.save(office);
    }

    @Override
    public int update(Office office) {
        return officeDao.update(office);
    }

    @Override
    public int remove(Long id) {
        return officeDao.remove(id);
    }

    @Override
    public int count(Map<String, Object> map) {
        return officeDao.count(map);
    }

    @Override
    public List<Office> selectByPage(Map<String, Object> map) {
        return officeDao.selectByPage(map);
    }
}
