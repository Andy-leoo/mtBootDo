package com.bootdo.train.service;

import com.bootdo.system.domain.UserDO;
import com.bootdo.train.pojo.DeptModule;

import java.util.List;
import java.util.Map;

public interface DeptModuleService {
    List<DeptModule> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DeptModule module, UserDO user);

    DeptModule queryByDeptId(Long deptId);

    int remove(Long id);

    int update(DeptModule module, UserDO user);

    DeptModule queryById(Long id);


    DeptModule queryByModuleId(Long moduleId);
}
