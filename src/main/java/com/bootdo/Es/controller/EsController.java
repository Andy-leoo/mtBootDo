package com.bootdo.Es.controller;

import com.bootdo.Es.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/es")
public class EsController {


    @Autowired
    private EsService esService;

    @RequestMapping("/index")
    public String jdQuery() {
        return "/es/index";
    }

    @RequestMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String , Object>> boolQuery(@RequestParam("keyword") String keyword,
                                                @RequestParam("pageNo") int pageNo,
                                                @RequestParam("pageSize") int pageSize) {
        return esService.termQuery(keyword,pageNo,pageSize);
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
