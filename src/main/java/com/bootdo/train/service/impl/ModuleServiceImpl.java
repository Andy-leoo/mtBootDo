package com.bootdo.train.service.impl;

import com.bootdo.train.dao.ModuleDao;
import com.bootdo.train.pojo.Module;
import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public int count(Map<String, Object> map) {
        return moduleDao.count(map);
    }

    @Override
    public List<Module> list(Map<String, Object> map) {
        return moduleDao.list(map);
    }

    @Override
    public int save(Module module, UserDO user) {
        module.setCreater(user.getUsername());
        module.setCreateTime(new Date());
        module.setOperatoTime(new Date());
        module.setOperator(user.getUsername());
        return moduleDao.save(module);
    }

    @Override
    public Module queryById(Long moduleId) {
        return moduleDao.queryById(moduleId);
    }

    @Override
    public int remove(Long id) {
        return moduleDao.remove(id);
    }

    @Override
    public int update(Module module, UserDO user) {
        module.setOperator(user.getName());
        module.setOperatoTime(new Date());
        return moduleDao.update(module);
    }

    @Override
    public List<Module> selectModulesByMenuId(Long menuId) {
        return moduleDao.selectModulesByMenuId(menuId);
    }
}
