package diary.diaryApp.service;

import diary.diaryApp.domain.Member;
import diary.diaryApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원가입 시 회원 email 중복 확인
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(!findMember.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 한명 조회
     */
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 정보 업데이트
     */
    @Transactional
    public void update(Long memberId, String name){
        Member member = memberRepository.findOne(memberId); //영속성 컨텍스트에 객체를 가져온다.
        member.setName(name); // 변경 감지를 이용한 업데이트
    }

}
