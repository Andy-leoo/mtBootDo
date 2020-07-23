package com.bootdo.learning.dao;

import com.bootdo.learning.entity.Position;
import org.apache.ibatis.annotations.Mapper;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/21 10:00 <br>
 * @  shardingsphere  分库分表 ，测试dao
 * @see com.bootdo.learning.dao <br>
 */
@Mapper
public interface PositionDao {

    int save(Position dict);
}
