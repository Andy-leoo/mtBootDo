package com.bootdo.Es.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsServiceTest {

    @Autowired
    private EsService service;

    @Test
    public void termQuery() {
        System.out.println(service.termQuery("5cc450a5de4df60001741e86", 1, 10));
    }

    @Test
    public void boolQuery() {
        System.out.println(service.boolQuery( 1, 10));
    }
}