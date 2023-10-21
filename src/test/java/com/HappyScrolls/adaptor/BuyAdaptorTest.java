package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.BuyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class BuyAdaptorTest {

    @InjectMocks
    private BuyAdaptor buyAdaptor;

    @Mock
    private BuyRepository buyRepository;


    @Test
    @DisplayName("엔티티 저장 테스트")
    public void testSaveEntity() {
        Buy buy = new Buy();
        buy.setId(1L);

        when(buyRepository.save(any())).thenReturn(buy);

        Long resultId = buyAdaptor.saveEntity(buy);
        assertEquals(1L, resultId);
    }

    @Test
    @DisplayName("사용자 구매내역 조회 테스트")
    public void testBuyRetrieveUser() {
        Member member = new Member();
        Buy buy = new Buy();
        buy.setMember(member);
        when(buyRepository.findAllByMember(any())).thenReturn(List.of(buy));

        List<Buy> resultList = buyAdaptor.buyRetrieveUser(member);
        assertEquals(1, resultList.size());
        assertEquals(buy, resultList.get(0));
    }
}
