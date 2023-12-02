package hello.core.member;

import hello.core.dataAccess.Repository;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final Repository repository;

  @Override
  public Member create(Member member) {
    return (Member) repository.save(member);
  }

  @Override
  public Member update(Member member) {
    return (Member) repository.save(member);
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }

  @Override
  public Member findById(String id) {
    return (Member) repository.findById(id);
  }
}
