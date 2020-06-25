package com.bootdo.train.dao;

import com.bootdo.train.pojo.ScrollGraph;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RollImgDao {

    int insert(ScrollGraph record);

    List<ScrollGraph> selectAll();

    int remove(int id);
}