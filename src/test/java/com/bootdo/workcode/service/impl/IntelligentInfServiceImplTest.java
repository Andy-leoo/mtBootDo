package com.bootdo.workcode.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntelligentInfServiceImplTest {

    @Autowired
    IntelligentInfServiceImpl intelligentInfService;

    @Test
    public void readJsonFileInputDB() {
        String path1 = "D:/project/ELK/20190401to20190427.json";
        String path2 = "D:/project/ELK/20190401to20190620.json";
        String path3 = "D:/project/ELK/20190427to201906200.json";

//        String s1 = intelligentInfService.readJsonFileInputDB(path1);
//        String s2 = intelligentInfService.readJsonFileInputDB(path2);
        String s3 = intelligentInfService.readJsonFileInputDB(path3);

    }
}