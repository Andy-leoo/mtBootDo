package com.bootdo.Es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {
    /**
     * elk集群地址
     */
    @Value("${elasticsearch.ip}")
    private String hostName;

    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    @Value("${elasticsearch.timeout}")
    private int timeout;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestClientBuilder builder = RestClient.builder(new HttpHost(hostName, Integer.valueOf(port), "http"));

        builder.setRequestConfigCallback(requestConfigBuilder  -> {
            //设置超时
            return requestConfigBuilder.setSocketTimeout(timeout);
        });
        return new RestHighLevelClient(builder);
    }

//    @Bean   es 5.5.0 使用transportClient
//    public TransportClient client() throws UnknownHostException {
//        // 创建节点
//        TransportAddress node = new InetSocketTransportAddress(
//                InetAddress.getByName(hostName),
//                Integer.valueOf(port)
//        );
//
//        //构建settings
//        Settings setting = Settings.builder()
//                .put("cluster.name",clusterName)//es名称
////                .put("cluster.trasport.sniff",true)// -- 启用嗅探
//                .build();
//
//        TransportClient client = new PreBuiltTransportClient(setting);
//        client.addTransportAddress(node);
////        client.addTransportAddress(node1);  //放集群地址  多个node 节点
////        client.addTransportAddress(node2);
//        return client;
//
//    }

    /**
     * 处理完成需要将客户端关闭， finally 中去关闭
     */
}
