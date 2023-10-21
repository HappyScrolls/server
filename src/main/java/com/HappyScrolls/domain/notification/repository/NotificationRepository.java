package com.HappyScrolls.domain.notification.repository;

import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMember(Member member);
}
