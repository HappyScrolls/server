package com.HappyScrolls.batch;

import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NotificationTasklet notificationTasklet;
    private final MemberRepository memberRepository;

    //Batch Job 생성
    @Bean
    public Job notificationJob() {
        return jobBuilderFactory.get("notificationJob")
                .start(notificationStep())
                .build();
    }

    //Batch Step 생성
    @Bean
    @JobScope //Job 실행시점에 Bean이 생성됨
    public Step notificationStep() {
        return stepBuilderFactory.get("oldBoardJobStep")
                .<Member, Member>chunk(10)
                .reader(memberReader()) //구현해야함 게시물을 올린지 1주일 이상된 보드를 읽어온다
                .processor(memberProcessor()) //구현해야함 판매완료가 아닐경우 ~~~을 수행한다
                .writer(memberWriter()) //구현해야함 처리된 게시물을 DB에 저장한다
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Member> memberReader() {
        return new RepositoryItemReaderBuilder<Member>()
                .repository(memberRepository)
                .methodName("findPage")
                .pageSize(10)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .name("memberReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Member, Member> memberProcessor(){
        return member -> {
            member.setPoint(member.getPoint()+30);
            return member;
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Member> memberWriter(){
        return new RepositoryItemWriterBuilder<Member>()
                .repository(memberRepository)
                .build();
    }
}