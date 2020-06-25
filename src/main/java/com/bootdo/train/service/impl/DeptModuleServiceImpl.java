package com.bootdo.train.service.impl;

import com.bootdo.train.dao.DeptModuleDao;
import com.bootdo.train.pojo.DeptModule;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.DeptModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeptModuleServiceImpl implements DeptModuleService {

    @Autowired
    private DeptModuleDao deptModuleDao;

    @Override
    public List<DeptModule> list(Map<String, Object> map) {
        return deptModuleDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return deptModuleDao.count(map);
    }

    @Override
    public int save(DeptModule module, UserDO user) {
        module.setCreater(user.getUsername());
        module.setCreateTime(new Date());
        module.setOperateTime(new Date());
        module.setOperator(user.getUsername());
        return deptModuleDao.save(module);
    }

    @Override
    public DeptModule queryByDeptId(Long deptId) {
        return deptModuleDao.queryByDeptId(deptId);
    }

    @Override
    public int remove(Long id) {
        return deptModuleDao.remove(id);
    }

    @Override
    public int update(DeptModule module, UserDO user) {
        module.setOperator(user.getName());
        module.setOperateTime(new Date());
        return deptModuleDao.update(module);
    }

    @Override
    public DeptModule queryById(Long id) {
        return deptModuleDao.queryById(id);
    }

    @Override
    public DeptModule queryByModuleId(Long moduleId) {
        return deptModuleDao.queryByModuleId(moduleId);
    }

}
