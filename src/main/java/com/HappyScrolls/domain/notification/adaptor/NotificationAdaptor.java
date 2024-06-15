package com.HappyScrolls.domain.notification.adaptor;

import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.notification.entity.Notification;
import com.HappyScrolls.domain.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
