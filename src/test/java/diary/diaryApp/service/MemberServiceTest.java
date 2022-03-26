package diary.diaryApp.service;

import diary.diaryApp.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 중복확인() throws Exception {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("호윤");
        member1.setEmail("hoyun98@naver.com");
        member1.setPassword("hoyun98");

        member2.setName("호잉");
        member2.setEmail("hoyun98@naver.com");
        member2.setPassword("hoyuning");

        //when
        Long member1Id = memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, ()-> {
            memberService.join(member2);
        });
    }
}