package com.helloshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.helloshop.repository.Member;
import com.helloshop.repository.MemberRepository;
import com.helloshop.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

  @Autowired
  MemberService service;

  @Autowired
  MemberRepository repo;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
    if (repo instanceof MemoryMemberRepository memRepo) {
      // TODO: Transactional MemoryMemberRepository
      memRepo.clear();
    }
  }

  @Test
  public void join() {
    Member member = new Member("test@test.com");
    Long id = service.join(member);
    repo.findById(id).map(Member::getEmail).ifPresent(email ->
        assertThat(email).isEqualTo(member.getEmail())
    );
  }

  @Test
  public void joinInDuplicate() {
    Member member = new Member("test@test.com");
    service.join(member);
    assertThatThrownBy(() -> service.join(member))
        .isInstanceOf(JoinInDuplicateException.class)
        .hasMessageContaining(member.getEmail());
  }
}
