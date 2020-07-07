package com.bootdo.Es.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/06/26 21:33 <br>
 * @ 业务逻辑
 * @see com.bootdo.Es.service <br>
 */
public class EsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public String createIndex(){
//        restHighLevelClient.index();
    return null;
    }
}
