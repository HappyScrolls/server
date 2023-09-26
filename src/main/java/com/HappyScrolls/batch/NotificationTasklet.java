package com.HappyScrolls.batch;

import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@StepScope // Step 시점에 Bean이 생성
public class NotificationTasklet implements Tasklet {

    private final MemberRepository memberRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            member.setPoint(member.getPoint()+10);
        }
        return RepeatStatus.FINISHED;
    }

//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){
//
//        log.info(contribution.toString());
//        log.info(chunkContext.toString());
//        log.info(">>>>> Delete Notification");
//
//        //읽음 상태인 알림 일괄삭제
//        notificationRepository.deleteNotificationIsRead();
//        //생성일로부터 3일이 지난 알림 일괄삭제
//        notificationRepository.deleteNotificationIsNotRead();
//
//
//    }
}