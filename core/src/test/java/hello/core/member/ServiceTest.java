package hello.core.member;

import hello.core.member.domain.Member;
import hello.core.member.domain.Service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {

  Service service = new ServiceImpl();

  @Test
  void signUp() {
    // given
    Member member = new Member("test@test.com");

    // when
    service.signUp(member);
    Member findMember = service.findMember(member.getId());

    // then
    Assertions.assertThat(member).isEqualTo(findMember);
  }
}
