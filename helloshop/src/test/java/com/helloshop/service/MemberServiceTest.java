package com.helloshop.service;

import com.helloshop.repository.Member;
import com.helloshop.repository.MemoryMemberRepository;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MemberService} class.
 */
class MemberServiceTest {

  Map<Long, Member> store;
  MemoryMemberRepository repo;
  MemberService service;

  Member member1;
  Member member2;

  /**
   * Sets up the test fixture.
   */
  @BeforeEach
  void setUp() {
    member1 = new Member(1L, "test1@test.com");
    member2 = new Member(2L, "test2@test.com");
    store = new HashMap<>();
    store.put(member1.getId(), member1);
    store.put(member2.getId(), member2);

    repo = new MemoryMemberRepository(store);
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
    Member newMember = new Member(3L, "test3@test.com");

    Assertions.assertThat(service.join(newMember)).isEqualTo(newMember.getId());
    Assertions.assertThat(store.containsValue(newMember)).isTrue();
    Assertions.assertThatThrownBy(() -> service.join(newMember))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining(
            "This email has already been registered. Please use a different email.");
  }

  /**
   * Tests the {@link MemberService#findMembers()} method.
   */
  @Test
  void findMembers() {
    Assertions.assertThat(service.findMembers().size()).isEqualTo(2);
  }

  /**
   * Tests the {@link MemberService#findOne(Long)} method.
   */
  @Test
  void findOne() {
    Assertions.assertThat(service.findOne(1L)).contains(member1);
    Assertions.assertThat(service.findOne(2L)).contains(member2);
    Assertions.assertThat(service.findOne(3L)).isEmpty();
  }
}