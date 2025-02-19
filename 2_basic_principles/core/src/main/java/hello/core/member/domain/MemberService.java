package hello.core.member.domain;

public interface MemberService {

  Member create(Member member);

  Member update(Member member);

  void deleteById(String id);

  Member findById(String id);
}
