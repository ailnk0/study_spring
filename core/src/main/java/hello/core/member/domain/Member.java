package hello.core.member.domain;

import hello.core.dataAccess.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Member extends Item {

  final String email;
  final MemberLevel level;
}
