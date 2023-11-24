package hello.core.member.domain;

public interface Service {

  Member signUp(Member member);

  Member findMember(String id);

  void deleteMember(String id);

  Member updateMember(Member member);
}
