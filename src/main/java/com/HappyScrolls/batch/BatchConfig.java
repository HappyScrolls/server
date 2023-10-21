package com.HappyScrolls.batch;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.article.entity.Sticker;
import com.HappyScrolls.domain.article.repository.ArticleRepository;
import com.HappyScrolls.domain.comment.repository.CommentRepository;
import com.HappyScrolls.domain.member.repository.MemberRepository;
import com.querydsl.core.types.dsl.NumberPath;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.HappyScrolls.entity.QArticle.article;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;
    long idx=0;
    private final EntityManagerFactory emf;

    Article article1;

    @Bean
    public Job job() throws  NoSuchFieldException, NoSuchMethodException,IllegalAccessException{
        return jobBuilderFactory.get("job")
                .start(hitStep())
                .build();
    }



    @Bean
    public QueryDslPagingItemReader queryDslreader() throws NoSuchFieldException, NoSuchMethodException ,IllegalAccessException{

        String identifierName = "id";
        String methodName = "getId";


        NumberPath<Long> identifier= (NumberPath<Long>)   article.getClass().getDeclaredField(identifierName).get(article);
        Method method = Article.class.getMethod(methodName);

        return new QueryDslPagingItemReaderBuilder<Article>()
                .name("QueryDslZeroOffsetPagingTest")
                .entityManagerFactory(emf)
                .pageSize(10)
                .identifier(identifier)
                .method(method)
                .queryFunction(queryFactory -> queryFactory
                        .selectFrom(article)
                        .where(article.createDate.eq(LocalDate.now().plusDays(2)), article.viewCount.gt(100)))
                .build();
    }

    @Bean
    @JobScope
    public Step hitStep() throws NoSuchFieldException, NoSuchMethodException ,IllegalAccessException {
        return stepBuilderFactory.get("hitStep")
                .<Article,Article>chunk(1000)
                .reader(queryDslreader())
                .processor(ArticleProcessor())
                .writer(articleWriter())
                .build();
    }

    @Bean
    public ItemReader<Article> customerItemReader() {
        String date= LocalDate.now().toString();
        return new JpaPagingItemReaderBuilder<Article>()
                .name("JpaPagingItemReaderJob")
                .entityManagerFactory(emf)
                .pageSize(1000)
                .queryString("select a from Article a where a.viewCount > 500 and a.createDate= '2023-10-10' ")
                .build();
    }


    @Bean
    @StepScope
    public ItemProcessor<Article, Article> ArticleProcessor(){
        return article -> {
            article.setSticker(Sticker.NONE);
            return article;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Article> articleWriter(){
        return new RepositoryItemWriterBuilder<Article>()
                .repository(articleRepository)
                .build();
    }


}