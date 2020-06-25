package com.bootdo.train.service;

import com.bootdo.train.pojo.Office;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OfficeService {

    Office selectOne(Long id);

    List<Office> officeList();

    int save(Office office);

    int update(Office office);

    int remove(Long id);

    int count(Map<String, Object> map);

    List<Office> selectByPage(Map<String, Object> map);
}
