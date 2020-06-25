package com.bootdo.train.dao;

import com.bootdo.train.pojo.Office;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OfficeDao {

    Office selectOne(Long id);

    List<Office> officeList();

    int save(Office office);

    int update(Office office);

    int remove(Long id);


    List<Office> selectByPage(Map<String, Object> map);

    int count(Map<String, Object> map);
}
