package com.HappyScrolls.batch;

import com.HappyScrolls.entity.Article;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.util.Assert;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

public class QueryDslPagingItemReaderBuilder<T>  {

    private int pageSize = 10;

    private EntityManagerFactory entityManagerFactory;

    private Map<String, Object> parameterValues;

    private boolean transacted = true;

    private Function<JPAQueryFactory, JPAQuery<T>> queryFunction;

    private JpaQueryProvider queryProvider;

    private boolean saveState = true;

    private String name;

    private int maxItemCount = Integer.MAX_VALUE;

    private int currentItemCount;

    private Method method;

    private NumberPath<Long> identifier;


    public QueryDslPagingItemReaderBuilder<T> saveState(boolean saveState) {
        this.saveState = saveState;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> name(String name) {
        this.name = name;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> maxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> currentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> pageSize(int pageSize) {
        this.pageSize = pageSize;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> parameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> queryProvider(JpaQueryProvider queryProvider) {
        this.queryProvider = queryProvider;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> queryFunction(Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        this.queryFunction = queryFunction;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> transacted(boolean transacted) {
        this.transacted = transacted;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> identifier(NumberPath<Long> identifier) {
        this.identifier = identifier;

        return this;
    }

    public QueryDslPagingItemReaderBuilder<T> method(Method method) {
        this.method = method;

        return this;
    }



    public QueryDslPagingItemReaderBuilder<T> entityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

        return this;
    }


    public QueryDslPagingItemReader build() {
        Assert.notNull(this.entityManagerFactory, "An EntityManagerFactory is required");

        if(this.saveState) {
            Assert.hasText(this.name,
                    "A name is required when saveState is set to true");
        }



        QueryDslPagingItemReader<T> reader = new QueryDslPagingItemReader<T>();

        reader.setPageSize(this.pageSize);
        reader.setParameterValues(this.parameterValues);
        reader.setEntityManagerFactory(this.entityManagerFactory);
        reader.setQueryProvider(this.queryProvider);
        reader.setTransacted(this.transacted);
        reader.setCurrentItemCount(this.currentItemCount);
        reader.setMaxItemCount(this.maxItemCount);
        reader.setSaveState(this.saveState);
        reader.setName(this.name);
        reader.setIdentifier(this.identifier);
        reader.setQueryFunction(this.queryFunction);
        reader.setMethod(this.method);



        return reader;
    }
}
