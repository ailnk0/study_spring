package com.helloshop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository for managing members in memory.
 */
public class MemoryMemberRepository implements MemberRepository {

  private final Map<Long, Member> store;
  private static long sequence = 0L;

  /**
   * Creates a new memory member repository with the given store.
   *
   * @param store the store to use for storing members
   */
  public MemoryMemberRepository(Map<Long, Member> store) {
    this.store = store;
  }

  /**
   * Inserts the given member into the store.
   *
   * @param member the member to insert
   * @return an optional containing the inserted member, or empty if the insert failed
   */
  @Override
  public Optional<Member> insert(Member member) {
    return Optional.of(member).map(m -> {
      m.setId(++sequence);
      store.put(m.getId(), m);
      return m;
    });
  }

  /**
   * Finds the member with the given ID in the store.
   *
   * @param id the ID of the member to find
   * @return an optional containing the found member, or empty if no member was found
   */
  @Override
  public Optional<Member> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  /**
   * Finds the member with the given email address in the store.
   *
   * @param email the email address of the member to find
   * @return an optional containing the found member, or empty if no member was found
   */
  @Override
  public Optional<Member> findByEmail(String email) {
    return store.values().stream().filter(member -> member.getEmail().equals(email)).findAny();
  }

  /**
   * Returns a list of all members in the store.
   *
   * @return a list of all members in the store
   */
  @Override
  public List<Member> findAll() {
    return new ArrayList<>(store.values());
  }
}