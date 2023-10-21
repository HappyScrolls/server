package com.HappyScrolls.adaptor;

import com.HappyScrolls.adaptor.NotificationAdaptor;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Notification;
import com.HappyScrolls.repository.NotificationRepository;
import com.HappyScrolls.service.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationAdaptorTest {

    @InjectMocks
    private NotificationAdaptor notificationAdaptor;

    @Mock
    private NotificationRepository notificationRepository;


    @Test
    @DisplayName("알림 생성 테스트")
    void 알림_생성() {
        Notification notification = new Notification();
        notificationAdaptor.notificationCreate(notification);

        verify(notificationRepository).save(notification);
    }

    @Test
    @DisplayName("알림 삭제 테스트")
    void 알림_삭제() {
        doNothing().when(notificationRepository).deleteById(any());

        notificationAdaptor.notificationDelete(1l);
        verify(notificationRepository).deleteById(1l);
    }

    @Test
    @DisplayName("사용자 알림 조회 테스트")
    void userNotificationRetrieveTest() {
        Member member = new Member();

        Notification notification1 = new Notification();
        notification1.setMember(member);
        Notification notification2 = new Notification();
        notification2.setMember(member);

        when(notificationRepository.findByMember(member))
                .thenReturn(List.of(notification1, notification2));

        List<Notification> result = notificationAdaptor.userNotificationRetrieve(member);

        assertEquals(2, result.size());
        assertEquals(result,List.of(notification1, notification2));
    }
}
