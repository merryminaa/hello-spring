package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach  //각 메서드 종료시마다 실행
    public void afterEach() {
        repository.clearStore(); //각 테스트는 서로 의존되지 않아야하므로 스토어 비워주는 함수 필요
    }

    @Test
    public void save() {

        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        //검증
        Member result = repository.findById(member.getId()).get();
//        Assertions.assertEquals(member, result); //jnuit; 기대값, 실제값
        Assertions.assertThat(member).isEqualTo(result);
//        System.out.println("result = " + (result == member));
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); //shift+f6으로 한번에 수정
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
