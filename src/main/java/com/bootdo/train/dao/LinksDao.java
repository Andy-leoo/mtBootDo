package com.bootdo.train.dao;

import com.bootdo.train.pojo.Links;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LinksDao {

    Links selectOne(Long id);

    List<Links> linksList();

    int save(Links links);

    int update(Links links);

    int remove(Long id);


    List<Links> selectByPage(Map<String, Object> map);

    int count(Map<String, Object> map);
}
