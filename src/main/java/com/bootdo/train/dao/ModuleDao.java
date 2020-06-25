package com.bootdo.train.dao;

import com.bootdo.train.pojo.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModuleDao {


    int count(Map<String, Object> map);

    List<Module> list(Map<String, Object> map);

    int save(Module module);

    Module queryById(Long id);

    int remove(Long id);

    int update(Module module);

    List<Module> selectModulesByMenuId(@Param("module") Long module);
}
