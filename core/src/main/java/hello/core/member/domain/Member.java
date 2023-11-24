package hello.core.member.domain;

import hello.core.dataAccess.Item;

public class Member extends Item {

  String email;
  Level level;

  public Member(String email) {
    this.email = email;
    this.level = Level.GENERAL;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(Level level) {
    this.level = level;
  }
}
