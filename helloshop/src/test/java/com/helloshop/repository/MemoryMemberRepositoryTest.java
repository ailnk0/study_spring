package com.helloshop.repository;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MemoryMemberRepository} class.
 */
class MemoryMemberRepositoryTest {

  Map<Long, Member> store;
  MemoryMemberRepository repo;

  /**
   * Sets up the test fixture.
   */
  @BeforeEach
  void setUp() {
    Member member1 = new Member(1L, "test1@test.com");
    Member member2 = new Member(2L, "test2@test.com");
    store = new HashMap<>();
    store.put(member1.getId(), member1);
    store.put(member2.getId(), member2);

    repo = new MemoryMemberRepository(store);
  }

  /**
   * Tears down the test fixture.
   */
  @AfterEach
  void tearDown() {
  }

  /**
   * Tests the {@link MemoryMemberRepository#insert(Member)} method.
   */
  @Test
  void insert() {
    Member newMember = new Member(3L, "test3@test.com");

    Assertions.assertThat(repo.insert(newMember)).isPresent();
    Assertions.assertThat(store.containsValue(newMember)).isTrue();
  }

  /**
   * Tests the {@link MemoryMemberRepository#findById(Long)} method.
   */
  @Test
  void findById() {
    Assertions.assertThat(repo.findById(1L)).isPresent();
    Assertions.assertThat(repo.findById(2L)).isPresent();
    Assertions.assertThat(repo.findById(3L)).isEmpty();
  }

  /**
   * Tests the {@link MemoryMemberRepository#findByEmail(String)} method.
   */
  @Test
  void findByEmail() {
    Assertions.assertThat(repo.findByEmail("test1@test.com")).isPresent();
    Assertions.assertThat(repo.findByEmail("test2@test.com")).isPresent();
    Assertions.assertThat(repo.findByEmail("test3@test.com")).isEmpty();
  }

  /**
   * Tests the {@link MemoryMemberRepository#findAll()} method.
   */
  @Test
  void findAll() {
    Assertions.assertThat(repo.findAll().size()).isEqualTo(2);
  }
}