package hello.core.member;

import hello.core.AppConfig;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberLevel;
import hello.core.member.domain.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberServiceTest {

  MemberService service;

  @BeforeEach
  public void beforeEach() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    service = ac.getBean(MemberService.class);
  }

  @Test
  void signUp() {
    Member member = service.create(new Member("test@test.com", MemberLevel.BASIC));
    Member findMember = service.findById(member.getId());

    Assertions.assertThat(member).isEqualTo(findMember);
  }
}
