package com.helloshop.repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing members in the database.
 */
public interface MemberRepository {

  /**
   * Inserts the given member into the database.
   *
   * @param member the member to insert
   * @return an optional containing the inserted member, or empty if the insert failed
   */
  Member save(Member member);

  /**
   * Finds the member with the given ID in the database.
   *
   * @param id the ID of the member to find
   * @return an optional containing the found member, or empty if no member was found
   */
  Optional<Member> findById(Long id);

  /**
   * Finds the member with the given email address in the database.
   *
   * @param email the email address of the member to find
   * @return an optional containing the found member, or empty if no member was found
   */
  Optional<Member> findByEmail(String email);

  /**
   * Returns a list of all members in the database.
   *
   * @return a list of all members in the database
   */
  List<Member> findAll();
}