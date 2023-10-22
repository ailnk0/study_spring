package com.helloshop.service;

import com.helloshop.repository.Member;
import com.helloshop.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for managing members.
 */
@Service
public class MemberService {

  private final MemberRepository repo;

  /**
   * Creates a new member service with the given repository.
   *
   * @param repo the repository to use for managing members
   */
  public MemberService(MemberRepository repo) {
    this.repo = repo;
  }

  /**
   * Registers a new member with the given information.
   *
   * @param member the member to register
   * @return the ID of the registered member
   * @throws IllegalStateException if a member with the same email address already exists
   */
  public Long join(Member member) {
    validateDuplicateMember(member);
    repo.insert(member);
    return member.getId();
  }

  /**
   * Validates that the given member is not a duplicate.
   *
   * @param member the member to validate
   * @throws IllegalStateException if a member with the same email address already exists
   */
  private void validateDuplicateMember(Member member) {
    repo.findByEmail(member.getEmail()).ifPresent(m -> {
      throw new IllegalStateException(
          "This email has already been registered. Please use a different email.");
    });
  }

  /**
   * Returns a list of all members.
   *
   * @return a list of all members
   */
  public List<Member> findMembers() {
    return repo.findAll();
  }

  /**
   * Finds the member with the given ID.
   *
   * @param memberId the ID of the member to find
   * @return an optional containing the found member, or empty if no member was found
   */
  public Optional<Member> findOne(Long memberId) {
    return repo.findById(memberId);
  }
}