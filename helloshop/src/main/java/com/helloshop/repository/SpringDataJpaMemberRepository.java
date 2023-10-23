package com.helloshop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>,
    MemberRepository {

  @Override
  Optional<Member> findByEmail(String email);
}
