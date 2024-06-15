package com.HappyScrolls.config.elastic;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArticleDocCustomRepositoryImpl implements ArticleDocCustomRepository {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Override
    public List<ArticleDoc> find(String  parameter) {

//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
//                .withQuery(
//                        QueryBuilders.functionScoreQuery(
//                                QueryBuilders.matchQuery("title", "부하테스트"),
//                                ScoreFunctionBuilders.fieldValueFactorFunction("view_count")
//                                        .modifier(FieldValueFactorFunctionBuilder.DEFAULT_MODIFIER.LOG1P)
//                                        .factor(1)
//                        ).boostMode(FunctionScoreQueryBuilder.DEFAULT_BOOST_MODE.MULTIPLY)
//                );

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String term : inputList(parameter)) {
            boolQueryBuilder.should(QueryBuilders.multiMatchQuery(term, "title", "body"));
        }

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                boolQueryBuilder,
                ScoreFunctionBuilders.fieldValueFactorFunction("view_count")
                        .modifier(FieldValueFactorFunctionBuilder.DEFAULT_MODIFIER.LOG1P)
                        .factor(1)
        ).boostMode(FunctionScoreQueryBuilder.DEFAULT_BOOST_MODE.MULTIPLY);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQueryBuilder)
                .build();
        System.out.println(searchQuery.getQuery().toString());
        return  elasticsearchOperations.search(searchQuery, ArticleDoc.class).getSearchHits()
                .stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

    public static List<String> inputList(String input) {
        String[] words = input.split(" ");
        List<String> combinations = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = i; j < words.length; j++) {
                    sb.append(words[j]);
                    combinations.add(sb.toString());
                    if (j != words.length) sb.append(" ");
                }
        }

        return combinations;
    }
}
