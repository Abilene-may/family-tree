package com.example.familytree.repository;

import com.example.familytree.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository
    extends JpaRepository<Member, Long>,
    JpaSpecificationExecutor<Member> {
  @Query(value = "select * from members where role = :role", nativeQuery = true)
  Optional<Member> checkExistRole(String role);
  @Query(value = "select * from members where username = :userName", nativeQuery = true)
  Optional<Member> findByUserName(String userName);
}
