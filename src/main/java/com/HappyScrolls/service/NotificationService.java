package com.HappyScrolls.service;


import com.HappyScrolls.dto.NotificationDTO;
import com.HappyScrolls.entity.CommentEvent;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Notification;
import com.HappyScrolls.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberService memberService;


    @EventListener
    public void notificationCreate(CommentEvent event) {
        Notification notification = Notification.builder().msg("포인트가 지급되었습니다.").category("포인트").member(event.getParent().getMember()).build();

        Notification notification2 = Notification.builder().msg("포인트 추가되었음!").member(event.getChild().getMember()).build();

        notificationRepository.save(notification);
        notificationRepository.save(notification2);
    }

    public void notificationDelete(Long notificationId) {
        notificationRepository.deleteById(notificationId); //예외처리
    }

    public List<Notification> userNotificationRetrieve(Member member) {
        return notificationRepository.findByMember(member);
    }
}
