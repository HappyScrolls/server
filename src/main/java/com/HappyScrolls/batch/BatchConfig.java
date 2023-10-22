package com.HappyScrolls.batch;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Sticker;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.CommentRepository;
import com.HappyScrolls.repository.MemberRepository;
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
import static com.HappyScrolls.entity.QComment.comment;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
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


    @Bean
    public QueryDslPagingItemReader queryDslreader2() throws NoSuchFieldException, NoSuchMethodException ,IllegalAccessException{

        String identifierName = "id";
        Comment entity = new Comment();
        NumberPath<Long> identifier= (NumberPath<Long>)   comment.getClass().getDeclaredField(identifierName).get(comment);
        Method method = entity.getClass().getMethod("getId");
        Member member = memberRepository.findById(1l).get();
        return new QueryDslPagingItemReaderBuilder<Comment>()
                .name("QueryDslZeroOffsetPagingTest")
                .entityManagerFactory(emf)
                .pageSize(10)
                .identifier(identifier)
                .method(method)
                .queryFunction(queryFactory -> queryFactory
                        .selectFrom(comment)
                        .where(comment.member.eq(member)))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Comment, Comment> CommentProcessor(){
        return comment -> {
            comment.setIsParent(true);
            return comment;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Comment> commentWriter(){
        return new RepositoryItemWriterBuilder<Comment>()
                .repository(commentRepository)
                .build();
    }
}