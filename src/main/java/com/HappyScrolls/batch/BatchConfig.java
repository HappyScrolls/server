package com.HappyScrolls.batch;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Sticker;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import static com.HappyScrolls.entity.QArticle.article;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NotificationTasklet notificationTasklet;
    private final MemberRepository memberRepository;

    private final ArticleRepository articleRepository;

    long idx=0;
    private final EntityManagerFactory emf;


//    @Bean
//    public Job job() {
//        return jobBuilderFactory.get("job")
//                .start(step())
//                .build();
//    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("querydslPagingReaderStep")
                .<Article, Article>chunk(10)
                .reader(reader())
                .processor(ArticleProcessor())
                .writer(articleWriter())
                .build();
    }

    @Bean
    public QuerydslPagingItemReader reader() {
        return new QuerydslPagingItemReader(emf, 10, queryFactory -> queryFactory
                .selectFrom(article)
                .where(article.createDate.eq(LocalDate.now().minusDays(1)), article.viewCount.gt(100)));
    }



    //Batch Job 생성
//    @Bean
//    public Job notificationJob() {
//        return jobBuilderFactory.get("notificationJob2")
//                .start(notificationStep())
//                .build();
//    }




    @Bean
    @JobScope //Job 실행시점에 Bean이 생성됨
    public Step notificationStep() {
        return stepBuilderFactory.get("1")
                .<Article,Article>chunk(20)
                .reader(new ProductRepositoryItemReader(articleRepository))
                .processor(ArticleProcessor())
                .writer(articleWriter())
                .build();
    }

    @Bean
    @JobScope //Job 실행시점에 Bean이 생성됨
    public Step notificationStep2() {
        return stepBuilderFactory.get("1")
                .<Member,Member>chunk(20)
                .reader(customerItemReader())
                .processor(memberProcessor())
                .writer(memberWriter())
                .build();
    }
    @Bean
    public ItemReader<Member> customerItemReader() {

        return new JpaPagingItemReaderBuilder<Member>()
                .name("JpaPagingItemReaderJob")
                .entityManagerFactory(emf)
                .pageSize(1000)
                .queryString("select c from Member c where c.point > 100")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Member, Member> memberProcessor(){

        return member -> {
            idx = member.getId();
            member.setEmail("newEmail");
            return member;
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<Article, Article> ArticleProcessor(){

        System.out.println("process!");
        return article -> {
            idx = article.getId();
            article.setSticker(Sticker.NEWHIT);
            System.out.println(article.getId());
            return article;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Member> memberWriter(){
        return new RepositoryItemWriterBuilder<Member>()
                .repository(memberRepository)
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Article> articleWriter(){
        return new RepositoryItemWriterBuilder<Article>()
                .repository(articleRepository)
                .build();
    }
}