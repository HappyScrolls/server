package com.HappyScrolls.domain.notification.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends Repository<Notification, Long> {

    List<Notification> findByMember(Member member);

    Member save(Notification entity);

    void deleteById(Long id);
}
