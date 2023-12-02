package hello.core.member.domain;

import hello.core.dataAccess.Item;

public class Member extends Item {

  String email;
  MemberLevel level;

  public Member(String email, MemberLevel level) {
    this.email = email;
    this.level = level;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public MemberLevel getLevel() {
    return level;
  }

  public void setLevel(MemberLevel level) {
    this.level = level;
  }
}
