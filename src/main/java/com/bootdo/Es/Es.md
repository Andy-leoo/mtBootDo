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