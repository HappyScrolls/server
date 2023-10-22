package com.HappyScrolls.batch;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.HappyScrolls.entity.Article;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import static com.HappyScrolls.entity.QArticle.article;


public class QueryDslPagingItemReader<T> extends AbstractPagingItemReader<T> {

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private final Map<String, Object> jpaPropertyMap = new HashMap<>();

    private String queryString;

    private JpaQueryProvider queryProvider;

    private Map<String, Object> parameterValues;

    private boolean transacted = true;//default value

    private Function<JPAQueryFactory, JPAQuery<T>> queryFunction;

    private Long lastidx=0l;

    private NumberPath<Long> identifier;

    private Method method;

    public QueryDslPagingItemReader() {
        setName(ClassUtils.getShortName(org.springframework.batch.item.database.JpaPagingItemReader.class));
    }

    private JPAQuery<T> createQuery() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFunction.apply(queryFactory);
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public void setParameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }


    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }
    public void setQueryFunction( Function<JPAQueryFactory, JPAQuery<T>> queryFunction
    ) {
        this.queryFunction = queryFunction;
    }


    public void setQueryProvider(JpaQueryProvider queryProvider) {
        this.queryProvider = queryProvider;
    }

    public void setIdentifier(NumberPath<Long> identifier) {
        this.identifier = identifier;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
    @Override
    protected void doOpen() throws Exception {
        super.doOpen();

        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
        if (entityManager == null) {
            throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
        }
        // set entityManager to queryProvider, so it participates
        // in JpaPagingItemReader's managed transaction
        if (queryProvider != null) {
            queryProvider.setEntityManager(entityManager);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doReadPage()  {

        EntityTransaction tx = null;

        if (transacted) {
            tx = entityManager.getTransaction();
            tx.begin();

            entityManager.flush();
            entityManager.clear();
        }//end if

        System.out.println("idx "+lastidx );
        System.out.println(identifier);
        System.out.println(method);
        //쿼리 제로 오프셋 설정
        JPAQuery<T> query = createQuery()
                .where(identifier.gt(lastidx))
                .limit(getPageSize());



        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }


        List<T> queryResult = query.fetch();
        if (!transacted) {

            for (T entity : queryResult) {
                entityManager.detach(entity);
                results.add(entity);
            }
        } else {
            results.addAll(query.fetch());
            tx.commit();
        }

        try {
            setLastIndex(queryResult.get(queryResult.size() - 1));
        }
        catch (Exception e) {
            throw new RuntimeException();
        }

    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }

    @Override
    protected void doClose() throws Exception {
        entityManager.close();
        super.doClose();
    }

    protected void setLastIndex(T entity) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException{
        lastidx= (Long) method.invoke(entity);
    }
}
