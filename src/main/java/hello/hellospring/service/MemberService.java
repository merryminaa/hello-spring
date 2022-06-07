package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
//tip: 테스트 케이스 작성시 상기 클래스 ctrl+shift_t 하면 자동으로 생성
    private final MemberRepository memberRepository;


    //service 와 test에서 각각 다른 객체를 바라보고 있으므로 본 서비스에서는 새로 생성이 아닌, 외부 객체를 가져오도록 수정
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원가입 */
    public Long join(Member member) {
        //동명의 중복회원 제외
//        Optional<Member> result = memberRepository.findByName(member.getName());
        validateDuplicateMember(member);
        //tip: 결과에 null값이 있을 수 있는 경우, 예전에는 if null 등으로 체크했지만 Optional로 감싸서 반환 후 내부 함수 사용
        //tip: Optional을 바로 반환받는 것은 좋지 않으므로, 바로 받아와서 후처리
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    //tip: refactor => extract method

    /*전체 회원 조회*/
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberID) {
        return memberRepository.findById(memberID);
    }
}
