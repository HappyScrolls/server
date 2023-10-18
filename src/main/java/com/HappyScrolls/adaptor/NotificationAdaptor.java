package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.CommentEvent;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Notification;
import com.HappyScrolls.repository.NotificationRepository;
import com.HappyScrolls.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.util.List;

@Adaptor
public class NotificationAdaptor {
    @Autowired
    private NotificationRepository notificationRepository;



    public void notificationCreate(Notification request) {
        notificationRepository.save(request);
    }

    public void notificationDelete(Long notificationId) {
        notificationRepository.deleteById(notificationId); //예외처리
    }

    public List<Notification> userNotificationRetrieve(Member member) {
        return notificationRepository.findByMember(member);
    }
}
