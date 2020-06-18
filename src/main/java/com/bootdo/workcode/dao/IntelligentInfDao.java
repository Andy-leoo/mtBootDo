package com.bootdo.workcode.dao;

import com.bootdo.workcode.bean.IntelligentInfDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IntelligentInfDao {

    int batchSave(List<IntelligentInfDo> list);
}
