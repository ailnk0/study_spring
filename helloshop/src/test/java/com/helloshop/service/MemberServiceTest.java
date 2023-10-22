package com.helloshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.helloshop.repository.Member;
import com.helloshop.repository.MemoryMemberRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MemberService} class.
 */
class MemberServiceTest {

  MemoryMemberRepository repo;
  MemberService service;

  /**
   * Sets up the test fixture.
   */
  @BeforeEach
  void setUp() {
    repo = new MemoryMemberRepository();
    repo.save(new Member("test1@test.com"));
    repo.save(new Member("test2@test.com"));
    service = new MemberService(repo);
  }

  /**
   * Tears down the test fixture.
   */
  @AfterEach
  void tearDown() {
  }

  /**
   * Tests the {@link MemberService#join(Member)} method.
   */
  @Test
  void join() {
    String newEmail = "test3@test.com";
    service.join(new Member(newEmail));

    assertThat(repo.findByEmail(newEmail)).isPresent();
  }

  @Test
  public void joinInDuplicate() {
    String newEmail = "test3@test.com";
    service.join(new Member(newEmail));

    assertThatThrownBy(() -> service.join(new Member(newEmail)))
        .isInstanceOf(JoinInDuplicateException.class)
        .hasMessageContaining(newEmail);
  }

  /**
   * Tests the {@link MemberService#findMembers()} method.
   */
  @Test
  void findMembers() {
    List<Member> all = service.findMembers();
    assertThat(all).hasSize(2)
        .anySatisfy(m -> assertThat(m.getEmail()).isEqualTo("test1@test.com"))
        .anySatisfy(m -> assertThat(m.getEmail()).isEqualTo("test2@test.com"))
        .noneSatisfy(m -> assertThat(m.getEmail()).isEqualTo("test3@test.com"));
  }

  /**
   * Tests the {@link MemberService#findOne(Long)} method.
   */
  @Test
  void findOne() {
    assertThat(service.findOne(1L)).isPresent();
    assertThat(service.findOne(2L)).isPresent();
    assertThat(service.findOne(3L)).isEmpty();
  }
}