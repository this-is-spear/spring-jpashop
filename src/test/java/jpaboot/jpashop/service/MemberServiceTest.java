package jpaboot.jpashop.service;

import jpaboot.jpashop.domain.Member;
import jpaboot.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    //회원 가입

    @Test
//    @Rollback(value = false)
    @DisplayName("MemberService 에서 회원 가입 테스트")
    void 회원가입() {
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);

        //같은 트랜잭션안에서 저장되고 조회되면 영속 컨텍스트 안에서 같은 값이 나온다.
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    // 회원 가입할 때, 이름이 같으면 예외

    @Test
    @DisplayName("MemberService 에서 회원 가입할때 이름 중복시 예외 출력")
    void 중복_회원_예외() {
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        memberService.join(member1);
        assertThrows(IllegalStateException.class, ()->{
            memberService.join(member1);
        });
    }
}