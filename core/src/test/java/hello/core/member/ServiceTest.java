package hello.core.member;

import hello.core.member.domain.Level;
import hello.core.member.domain.Member;
import hello.core.member.domain.Service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {

  Service service = new ServiceImpl();

  @Test
  void signUp() {
    Member member = service.create(new Member("test@test.com", Level.BASIC));
    Member findMember = service.findById(member.getId());

    Assertions.assertThat(member).isEqualTo(findMember);
  }
}
