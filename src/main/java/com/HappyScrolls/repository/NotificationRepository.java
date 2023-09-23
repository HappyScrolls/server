package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMember(Member member);
}
