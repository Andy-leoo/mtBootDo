package com.bootdo.Es.service;

import com.alibaba.fastjson.JSON;
import com.bootdo.workcode.bean.IntelligentInfDo;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
@Service
public class EsService {

    //面向对象操作
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 精准查询
    public List<Map<String , Object>> termQuery(String infId , int pageNo,int pageSize) {

        System.out.println(infId + pageNo + pageSize);
        // 搜索请求对象
        SearchRequest inf = new SearchRequest("inf");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);
        //定义一个termQuery  精准查询 ,可以查询一个归类列表 ，或者 某个固定的 id
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("inf_id", infId);

        searchSourceBuilder.query(termQueryBuilder);


        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");// 指定要高亮的 地段
        highlightBuilder.requireFieldMatch(false); // 多个高亮显示  ，false 不需要多个高亮
        //配置高亮显示的样式
        highlightBuilder.preTags("<span style= 'color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //向搜索请求对象设置搜索源
        inf.source(searchSourceBuilder);

        //执行搜索，向ES发起http请求
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(inf, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Map<String , Object>> list = new ArrayList<>();
        //解析结果
        for (SearchHit hit : response.getHits().getHits()) {
//            list.add(documentFields.getSourceAsMap());

            // 如果需要高亮 则需要将高亮的字段 ，进行替换
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");// 找到 高亮的字段
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();// 获取原来的结果

            //解析高亮的字段，将原来的字段 替换为 高亮字段
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments) {
                    n_title += text;
                }
                sourceAsMap.put("title",n_title);//高亮字段替换为原来的字段
            }
            list.add(sourceAsMap);
        }
//
//        // 搜索结果
//        SearchHits hits = response.getHits();
//        // 匹配到的总计录
//        TotalHits totalHits = hits.getTotalHits();
//        // 得到文档
//        SearchHit[] searchHits = hits.getHits();
//
//        System.out.println("总数：" + totalHits.value);
//
//        List<IntelligentInfDo> infDos = new ArrayList<>();
//
//        for (SearchHit searchHit : searchHits) {
//            //文档id
//            String id = searchHit.getId();
//            //源文档内容
//            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
//
//            String inf_ID = (String) sourceAsMap.get("inf_id");
//            String title = (String) sourceAsMap.get("title");
//            String content = (String) sourceAsMap.get("content");
//            String publishAt = (String) sourceAsMap.get("publish_at");
//            System.out.println("inf_ID: " + inf_ID + "; title: " + title + "; content: " + content);
//            infDos.add(new IntelligentInfDo(Long.valueOf(id),inf_ID,content,publishAt,title));
//
//        }
        return list;
    }



    //
    public List<IntelligentInfDo> boolQuery(int pageNo,int pageSize) {
        // 搜索请求对象
        SearchRequest inf = new SearchRequest("inf");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);
        //定义一个termQuery  精准查询 ,可以查询一个归类列表 ，或者 某个固定的 id
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("inf_id", "5cc450a5de4df60001741e86");
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        //定义一个boolQuery
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(matchAllQueryBuilder);

        searchSourceBuilder.query(termQueryBuilder);

        //向搜索请求对象设置搜索源
        inf.source(searchSourceBuilder);

        //执行搜索，向ES发起http请求
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(inf, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                restHighLevelClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 搜索结果
        SearchHits hits = response.getHits();
        // 匹配到的总计录
        TotalHits totalHits = hits.getTotalHits();
        // 得到文档
        SearchHit[] searchHits = hits.getHits();

        System.out.println("总数：" + totalHits.value);

        List<IntelligentInfDo> infDos = new ArrayList<>();

        for (SearchHit searchHit : searchHits) {
            //文档id
            String id = searchHit.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

            String inf_ID = (String) sourceAsMap.get("inf_id");
            String title = (String) sourceAsMap.get("title");
            String content = (String) sourceAsMap.get("content");
            String publishAt = (String) sourceAsMap.get("publish_at");
            System.out.println("inf_ID: " + inf_ID + "; title: " + title + "; content: " + content);
            infDos.add(new IntelligentInfDo(Long.valueOf(id),inf_ID,content,publishAt,title));

        }
        return infDos;
    }

    //测试索引创建 request  相当于 PUT
    public void createIndex() throws IOException {
        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("jx_index");
        //2. 客户端执行请求 indexClient,请求后获得响应
        CreateIndexResponse createIndexResponse =
                restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        System.out.println(createIndexResponse);
    }

    //测试获取索引   索引 是一个 库  只能去看存不存在
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("jx_index");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 测试删除索引
    void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("jx_index");
        //删除
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        //获取状态
        System.out.println(delete.isAcknowledged());
    }

    //测试 添加文档
    void testAddDocument() throws IOException {
        //创建对象
        IntelligentInfDo intelligentInfDo =
                new IntelligentInfDo(1,
                        UUID.randomUUID().toString().replaceAll("-",""),
                        "测试添加文档",
                        "20200804",
                        "测试");

        //创建请求
        IndexRequest request = new IndexRequest("jx_index");

        //规则 put /jx_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));

        //将数据放入请求  JSON
        request.source(JSON.toJSONString(intelligentInfDo), XContentType.JSON);

        //客户端发送请求  ， 获取响应结果
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.status());

    }

    //获取文档  是否存在  get  /index/doc/1
    void testExists() throws IOException {
        GetRequest getRequest = new GetRequest("jx_index", "1");

        //不获取返回的 _source 上下文 ，可不写
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档信息
    void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("jx_index", "1");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        //获取对应的数据
        System.out.println(getResponse.getSourceAsString());
        System.out.println(getResponse);
    }

    //更新文档信息
    void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("jx_index", "1");
        updateRequest.timeout("1s");

        IntelligentInfDo intelligentInfDo = new IntelligentInfDo(1,
                UUID.randomUUID().toString().replaceAll("-", ""),
                "测试添加文档2",
                "20200804",
                "测试2");
        updateRequest.doc(JSON.toJSONString(intelligentInfDo), XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        //获取对应的数据
        System.out.println(update);
    }

    //删除文档信息
    void testdeleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("jx_index", "1");
        deleteRequest.timeout("1s");

        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete);

    }


    //真是项目 一般会 批量插入
    void testBulkRequest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        ArrayList<IntelligentInfDo> arrayList = new ArrayList();
        //这里进行数据的添加

        //批量处理请求
        arrayList.forEach(infDo ->
                //这里可以看下 bulkRequest 下面的add 方法， 里面有很多 批量删除 批量增加的方法
                bulkRequest.add(new IndexRequest("jx_index")
                                .id(String.valueOf(infDo.getId()))
                                .source(JSON.toJSONString(infDo),XContentType.JSON))

                );

        //执行
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());// 是否失败， 返回fasle 是成共
    }


    //查询
    //SearchRequest  搜索请求
    //SearchSourceBuilder 条件构造
    //highlightBuilder 构建高亮
    //TermQueryBuilder 精确查找
    //MatchAllQueryBuilder 匹配所有查询
    // xxxQueryBuilder 对应我们刚才看到的命令
    void testSearch() throws IOException {

        SearchRequest searchRequest = new SearchRequest("jx_index");

        //构造搜索条件
        SearchSourceBuilder sourceBuilder  = new SearchSourceBuilder();

        //查询条件 ，我们可以使用QueryBuilders 工具来实现，这里包含很多
        //QueryBuilders.termQuery 精确查询
        //QueryBuilders.matchAllQuery() 匹配所有
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "测试");

        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //开始查询
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //所有数据都在里面
        SearchHits hits = search.getHits();
        System.out.println(hits);
    }

















}
