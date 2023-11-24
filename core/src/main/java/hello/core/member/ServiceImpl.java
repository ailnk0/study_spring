package hello.core.member;

import hello.core.dataAccess.MemoryRepository;
import hello.core.dataAccess.Repository;
import hello.core.member.domain.Member;
import hello.core.member.domain.Service;

public class ServiceImpl implements Service {

  private final Repository repository = new MemoryRepository();

  @Override
  public Member signUp(Member member) {
    return (Member) repository.save(member);
  }

  @Override
  public Member findMember(String id) {
    return (Member) repository.findById(id);
  }

  @Override
  public void deleteMember(String id) {
    repository.deleteById(id);
  }

  @Override
  public Member updateMember(Member member) {
    return (Member) repository.save(member);
  }
}
