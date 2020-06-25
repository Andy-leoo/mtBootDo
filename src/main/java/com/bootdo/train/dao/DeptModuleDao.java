package com.bootdo.train.dao;

import com.bootdo.train.pojo.DeptModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptModuleDao {


    int count(Map<String, Object> map);

    List<DeptModule> list(Map<String, Object> map);

    int save(DeptModule module);

    DeptModule queryByDeptId(Long deptId);

    int remove(Long id);

    int update(DeptModule module);

    DeptModule queryById(Long id);

    DeptModule queryByModuleId(Long moduleId);

    List<DeptModule> selectByModuleIds(List<String> moduleIds);
}
