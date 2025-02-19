package com.helloshop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MemoryMemberRepository} class.
 */
class MemoryMemberRepositoryTest {

  MemoryMemberRepository repo = new MemoryMemberRepository();

  /**
   * Sets up the test fixture.
   */
  @BeforeEach
  void setUp() {
    repo.getStore().put(1L, new Member(1L, "test1@test.com"));
    repo.getStore().put(2L, new Member(2L, "test2@test.com"));
  }

  /**
   * Tears down the test fixture.
   */
  @AfterEach
  void tearDown() {
    repo.getStore().clear();
  }

  /**
   * Tests the {@link MemoryMemberRepository#save(Member)} method.
   */
  @Test
  void save() {
    Member member = new Member("test3@test.com");
    Member newMember = repo.save(member);

    assertThat(newMember.getEmail()).isEqualTo(member.getEmail());
    assertThat(repo.getStore().values()).contains(newMember);
  }

  /**
   * Tests the {@link MemoryMemberRepository#findById(Long)} method.
   */
  @Test
  void findById() {
    assertThat(repo.findById(1L)).isPresent();
    assertThat(repo.findById(2L)).isPresent();
    assertThat(repo.findById(3L)).isEmpty();
  }

  /**
   * Tests the {@link MemoryMemberRepository#findByEmail(String)} method.
   */
  @Test
  void findByEmail() {
    assertThat(repo.findByEmail("test1@test.com")).isPresent();
    assertThat(repo.findByEmail("test2@test.com")).isPresent();
    assertThat(repo.findByEmail("test3@test.com")).isEmpty();
  }

  /**
   * Tests the {@link MemoryMemberRepository#findAll()} method.
   */
  @Test
  void findAll() {
    List<Member> all = repo.findAll();

    assertThat(all.size()).isEqualTo(2);
    assertThat(all).contains(new Member(1L, "test1@test.com"));
    assertThat(all).contains(new Member(2L, "test2@test.com"));
    assertThat(all).doesNotContain(new Member(3L, "test3@test.com"));
  }
}