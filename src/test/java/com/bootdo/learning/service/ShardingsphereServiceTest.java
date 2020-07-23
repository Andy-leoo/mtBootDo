package com.bootdo.learning.service;

import com.bootdo.learning.dao.PositionDao;
import com.bootdo.learning.entity.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingsphereServiceTest {

    @Autowired
    private PositionDao positionDao;

    @Test
    public void insertSelf() {
        for (int i=1;i<=20;i++){
            Position position = new Position();
//            position.setId(i);
            position.setName("lagou"+i);
            position.setSalary("1000000");
            position.setCity("beijing");
            positionDao.save(position);
        }

    }
}