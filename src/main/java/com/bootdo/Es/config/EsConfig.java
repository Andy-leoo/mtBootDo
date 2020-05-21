package com.bootdo.Es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    @Bean
    public TransportClient client() throws UnknownHostException {
        // 创建节点
        TransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName(hostName),
                Integer.valueOf(port)
        );

        //构建settings
        Settings setting = Settings.builder()
                .put("cluster.name",clusterName)//es名称
//                .put("cluster.trasport.sniff",true)// --  什么意思？
                .build();

        TransportClient client = new PreBuiltTransportClient(setting);
        client.addTransportAddress(node);
//        client.addTransportAddress(node1);  //放集群地址  多个node 节点
//        client.addTransportAddress(node2);
        return client;

    }

    /**
     * 处理完成需要将客户端关闭， finally 中去关闭
     */
}
