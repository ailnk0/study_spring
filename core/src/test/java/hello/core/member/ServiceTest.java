package hello.core.member;

import hello.core.AppConfig;
import hello.core.member.domain.Level;
import hello.core.member.domain.Member;
import hello.core.member.domain.Service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServiceTest {

  Service service;

  @BeforeEach
  public void beforeEach() {
    AppConfig config = new AppConfig();
    service = config.memberService();
  }

  @Test
  void signUp() {
    Member member = service.create(new Member("test@test.com", Level.BASIC));
    Member findMember = service.findById(member.getId());

    Assertions.assertThat(member).isEqualTo(findMember);
  }
}
