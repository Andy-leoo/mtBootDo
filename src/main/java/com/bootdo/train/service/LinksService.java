package com.bootdo.train.service;

import com.bootdo.train.pojo.Links;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LinksService {

    Links selectOne(Long id);

    List<Links> linksList();

    int save(Links links);

    int update(Links links);

    int remove(Long id);

    int count(Map<String, Object> map);

    List<Links> selectByPage(Map<String, Object> map);
}
