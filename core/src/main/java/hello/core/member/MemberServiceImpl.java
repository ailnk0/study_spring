package hello.core.member;

import hello.core.dataAccess.Repository;
import hello.core.member.domain.Member;
import hello.core.member.domain.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

  private final Repository repository;

  @Autowired
  public MemberServiceImpl(Repository repository) {
    this.repository = repository;
  }

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
