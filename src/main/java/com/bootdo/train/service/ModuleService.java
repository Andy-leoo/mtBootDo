package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.Module;

import java.util.List;
import java.util.Map;

public interface ModuleService {
    int count(Map<String, Object> map);

    List<Module> list(Map<String, Object> map);

    int save(Module module, UserDO user);

    Module queryById(Long moduleId);

    int remove(Long id);

    int update(Module module, UserDO user);

    List<Module> selectModulesByMenuId(Long menuId);
}
