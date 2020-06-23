
一 .了解 使用restHighLevelClient 进行es 搜索
    
    1. 构建匹配条件
         
            QueryBuilder queryBuilder = 
                            QueryBuilders.termsQuery("model_classification.keyword","需要人工查看","直接推送");
             QueryBuilder queryBuilder_title = 
                                QueryBuilders.matchQuery("title",keyword).boost(10);
             构建了两条匹配条件，
             第一条匹配doc中的model_classification字段，要求其内容需要为"需要人工查看","直接推送"中的任意一条；
             第二条匹配doc的title字段，要求其与keyword进行比较，并设置权重10.
    
    2. 组合匹配条件
             
             BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()；
             boolQueryBuilder.must(queryBuilder);
             boolQueryBuilder.should(queryBuilder_title);
             之前构造的匹配条件添加到bool条件中，
             用must去设定必须满足的条件，
             should去设置加分项。
    
    3. 创建查询
             
             SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
             sourceBuilder.fetchSource(new String[]{"id","title","abstract","source","date"},null);
             sourceBuilder.query(boolQueryBuilder);
             …
             sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
             sourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.DESC));
             创建的查询中可以设置需要获取的字段，需要匹配的条件等。
             同时可以设置排序字段，为了避免同分造成的排序振荡现象，建议设置两个字段。
    
    4. 创建搜索Request
             
             SearchRequest request = new SearchRequest(indexName);
             request.types(typeName);
             request.searchType("dfs_query_then_fetch");
             request.source(sourceBuilder);
             创建Request，
             设置request针对的index和doc类型，同时设定searchType并将上文构建的查询添加其中。
    
    5. 解析反馈结果
             
             SearchResponse response = restHighLevelClient.search(request);
             SearchHits hits = response.getHits();
             for (SearchHit hit:hits) {
             Map tempSource = hit.getSourceAsMap();
             …
             }
             
二. 了解 QueryBuilders
    
        
             * match query 单个匹配
             return QueryBuilders.matchQuery("name", "葫芦4032娃");
            
         
       
             * multimatch  query
             * 创建一个匹配查询的布尔型提供字段名称和文本。
                //现住址和家乡在【山西省太原市7429街道】的人
             return QueryBuilders.multiMatchQuery(
                        "山西省太原市7429街道",     // Text you are looking for
                        "home", "now_home"       // Fields you query on
                );
           
         
             * boolean query and 条件组合查询
             
                return QueryBuilders
                        .boolQuery()
                        .must(QueryBuilders.termQuery("name", "葫芦3033娃"))
                        .must(QueryBuilders.termQuery("home", "山西省太原市7967街道"))
                        .mustNot(QueryBuilders.termQuery("isRealMen", false))
                        .should(QueryBuilders.termQuery("now_home", "山西省太原市"));
            }
         
             * ids query
             * 构造一个只会匹配的特定数据 id 的查询。
             
             return QueryBuilders.idsQuery().ids("CHszwWRURyK08j01p0Mmug", "ojGrYKMEQCCPvh75lHJm3A");
            }
         
             * constant score query
             * 另一个查询和查询,包裹查询只返回一个常数分数等于提高每个文档的查询。
             
                /*return // Using with Filters
                        QueryBuilders.constantScoreQuery(FilterBuilders.termFilter("name", "kimchy"))
                                .boost(2.0f);*/
         
                // With Queries
                return QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("name", "葫芦3033娃"))
                        .boost(2.0f);
            
         
             * disjunction max query
             * 一个生成的子查询文件产生的联合查询，
             * 而且每个分数的文件具有最高得分文件的任何子查询产生的，
             * 再加上打破平手的增加任何额外的匹配的子查询。
             
                return QueryBuilders.disMaxQuery()
                        .add(QueryBuilders.termQuery("name", "kimchy"))          // Your queries
                        .add(QueryBuilders.termQuery("name", "elasticsearch"))   // Your queries
                        .boost(1.2f)
                        .tieBreaker(0.7f);
            }
         
             * fuzzy query
             * 使用模糊查询匹配文档查询。
                return QueryBuilders.fuzzyQuery("name", "葫芦3582");
            
         
             * has child / has parent
             * 父或者子的文档查询
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return // Has Child
                        QueryBuilders.hasChildQuery("blog_tag",
                                QueryBuilders.termQuery("tag", "something"));
         
                // Has Parent
                /*return QueryBuilders.hasParentQuery("blog",
                        QueryBuilders.termQuery("tag","something"));*/
            
         
             * matchall query
             * 查询匹配所有文件。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.matchAllQuery();
            
         
             * more like this (field) query (mlt and mlt_field)
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // mlt Query
                QueryBuilders.moreLikeThisQuery("home", "now_home") // Fields
                        .likeText("山西省太原市7429街道")                 // Text
                        .minTermFreq(1)                                 // Ignore Threshold
                        .maxQueryTerms(12);                             // Max num of Terms
                // in generated queries
         
                // mlt_field Query
                return QueryBuilders.moreLikeThisFieldQuery("home")              // Only on single field
                        .likeText("山西省太原市7429街道")
                        .minTermFreq(1)
                        .maxQueryTerms(12);
            
         
             * prefix query
             * 包含与查询相匹配的文档指定的前缀。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.prefixQuery("name", "葫芦31");
            
         
             * querystring query
             * 　　查询解析查询字符串,并运行它。有两种模式,这种经营。
             * 第一,当没有添加字段(使用{ @link QueryStringQueryBuilder #字段(String)},将运行查询一次,非字段前缀
             * 　　将使用{ @link QueryStringQueryBuilder # defaultField(字符串)}。
             * 第二,当一个或多个字段
             * 　　(使用{ @link QueryStringQueryBuilder #字段(字符串)}),将运行提供的解析查询字段,并结合
             * 　　他们使用DisMax或者一个普通的布尔查询(参见{ @link QueryStringQueryBuilder # useDisMax(布尔)})。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.queryString("+kimchy -elasticsearch");
            
         
             * range query
             * 查询相匹配的文档在一个范围。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders
                        .rangeQuery("name")
                        .from("葫芦1000娃")
                        .to("葫芦3000娃")
                        .includeLower(true)     //包括下界
                        .includeUpper(false); //包括上界
            
         
             * span queries (first, near, not, or, term)
             * 跨度查询
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // Span First
                QueryBuilders.spanFirstQuery(
                        QueryBuilders.spanTermQuery("name", "葫芦580娃"),  // Query
                        30000                                             // Max查询范围的结束位置
               
         
                // Span Near TODO NotSolved
                QueryBuilders.spanNearQuery()
                        .clause(QueryBuilders.spanTermQuery("name", "葫芦580娃")) // Span Term Queries
                        .clause(QueryBuilders.spanTermQuery("name", "葫芦3812娃"))
                        .clause(QueryBuilders.spanTermQuery("name", "葫芦7139娃"))
                        .slop(30000)                                               // Slop factor
                        .inOrder(false)
                        .collectPayloads(false);
         
                // Span Not TODO NotSolved
                QueryBuilders.spanNotQuery()
                        .include(QueryBuilders.spanTermQuery("name", "葫芦580娃"))
                        .exclude(QueryBuilders.spanTermQuery("home", "山西省太原市2552街道"));
         
                // Span Or TODO NotSolved
                return QueryBuilders.spanOrQuery()
                        .clause(QueryBuilders.spanTermQuery("name", "葫芦580娃"))
                        .clause(QueryBuilders.spanTermQuery("name", "葫芦3812娃"))
                        .clause(QueryBuilders.spanTermQuery("name", "葫芦7139娃"));
         
                // Span Term
                //return QueryBuilders.spanTermQuery("name", "葫芦580娃");
            
         
             * term query
             * 一个查询相匹配的文件包含一个术语。。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.termQuery("name", "葫芦580娃");
            }
         
         
             * terms query
             * 一个查询相匹配的多个value
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.termsQuery("name", // field
                        "葫芦580娃", "葫芦3812娃")                 // values
                        .minimumMatch(1);               // 设置最小数量的匹配提供了条件。默认为1。
            
         
             * top children  query
             * 构建了一种新的评分的子查询，与子类型和运行在子文档查询。这个查询的结果是，那些子父文档文件匹配。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.topChildrenQuery(
                        "blog_tag",                                 // field
                        QueryBuilders.termQuery("name", "葫芦3812娃") // Query
                )
                        .score("max")                               // max, sum or avg
                        .factor(5)
                        .incrementalFactor(2);
            
         
             * wildcard query
             * 　　实现了通配符搜索查询。支持通配符* < /tt>,<tt>
             * 　　匹配任何字符序列(包括空),<tt> ? < /tt>,
             * 　　匹配任何单个的字符。注意该查询可以缓慢,因为它
             * 　　许多方面需要遍历。为了防止WildcardQueries极其缓慢。
             * 　　一个通配符词不应该从一个通配符* < /tt>或<tt>
             * 　　< /tt> <tt> ?。
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.wildcardQuery("name", "葫芦*2娃");
            
         
             * nested query
             * 嵌套查询
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                return QueryBuilders.nestedQuery("location",               // Path
                        QueryBuilders.boolQuery()                      // Your query
                                .must(QueryBuilders.matchQuery("location.lat", 0.962590433140581))
                                .must(QueryBuilders.rangeQuery("location.lon").lt(0.00000000000000000003))
                )
                        .scoreMode("total");                  // max, total, avg or none
            
         
             * indices query
             * 索引查询
             * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                // Using another query when no match for the main one
                QueryBuilders.indicesQuery(
                        QueryBuilders.termQuery("name", "葫芦3812娃"),
                        Es_Utils.INDEX_DEMO_01, "index2"
                )       //设置查询索引上执行时使用不匹配指数
                        .noMatchQuery(QueryBuilders.termQuery("age", "葫芦3812娃"));
         
         
                // Using all (match all) or none (match no documents)
                return QueryBuilders.indicesQuery(
                        QueryBuilders.termQuery("name", "葫芦3812娃"),
                        Es_Utils.INDEX_DEMO_01, "index2"
                )      // 设置不匹配查询,可以是 all 或者 none
                        .noMatchQuery("none");
            }
         
         