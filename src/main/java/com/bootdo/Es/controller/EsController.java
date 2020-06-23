package com.bootdo.Es.controller;

import com.bootdo.Es.config.EsConfig;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @RequestMapping("/query/id")
    public String boolQuery() {
        // 搜索请求对象
        SearchRequest inf = new SearchRequest("inf");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

       //定义一个termQuery
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("inf_id", "5cc450a5de4df60001741e86");

        //定义一个boolQuery
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(termQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);

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

        for (SearchHit searchHit : searchHits) {
            //文档id
            String id = searchHit.getId();
            //源文档内容
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

            String inf_ID = (String) sourceAsMap.get("inf_id");
            String title = (String) sourceAsMap.get("title");
            String content = (String) sourceAsMap.get("content");
            System.out.println("inf_ID: " + inf_ID + "; title: " + title + "; content: " + content);


        }
        return "1";
    }




    /**
     * @author Andy-J<br>
     * @version 1.0<br>
     * @createDate 2020/6/20 20:25 <br>
     * @desc es 5.5.0

    @Autowired
    private TransportClient client;

    //根据id 查询
    @GetMapping("/get/book/novel")
    public String get (@RequestParam(name = "id", defaultValue = "") String id){
        GetResponse request = this.client.prepareGet("book","novel",id).get();

        Map<String, Object> source = request.getSource();
        String index = request.getIndex();
        return "index :"+ index + ";source:"+ source ;
    }

    //添加一个
    @PostMapping("/add/book/novel")
    @ResponseBody
    public String add(@RequestParam(name = "title") String title,
                      @RequestParam(name = "author") String author ,
                      @RequestParam(name = "word_count") int wordCount ,
                      @RequestParam(name = "publish_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publishDate ){
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("title",title)
                    .field("author",author)
                    .field("word_count",wordCount)
                    .field("publish_date",publishDate.getTime())
                    .endObject();
            System.out.println(title+author+wordCount+publishDate);
            IndexResponse response = this.client.prepareIndex("book","novel").setSource(content).get();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "添加成功";
    }

    @GetMapping("/delete/book/novel")
    public String delete(@RequestParam(name = "id") String id){
        DeleteResponse deleteResponse = this.client.prepareDelete("book", "novel", id).get();

        return deleteResponse.getResult().toString();
    }

    @PutMapping("/update/book/novel")
    public String update(@RequestParam(name = "id") String id,
                        @RequestParam(name = "title") String title,
                         @RequestParam(name = "author") String author ){
        UpdateRequest u = new UpdateRequest("book","novel",id);

        XContentBuilder xContentBuilder = null;
        try {
            xContentBuilder = XContentFactory.jsonBuilder().startObject();
            xContentBuilder.field("title",title);
            xContentBuilder.field("author",author);
            //一定要关闭
            xContentBuilder.endObject();
            u.doc(xContentBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            UpdateResponse updateResponse = this.client.update(u).get();
            return updateResponse.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/query/book/novel")
    public String query(@RequestParam(name = "title",required = false) String title,
                        @RequestParam(name = "author",required = false) String author ,
                        @RequestParam(name = "gt_word_count",defaultValue = "0") int gtwordCount ){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (title != null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",title));
        }
        if (author != null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("author",author));
        }

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("word_count").from(gtwordCount);

//        if ( ltwordCount > 0){
//            rangeQueryBuilder.to(ltwordCount);
//        }
        //将 boolquery rangequery 结合起来

        boolQueryBuilder.filter(rangeQueryBuilder);

        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setFrom(0)
                .setSize(10);

        System.out.println(searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.get();

        return searchResponse.toString();

    }
     */



}
