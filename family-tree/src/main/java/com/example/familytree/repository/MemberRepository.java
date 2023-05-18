package com.example.familytree.repository;

import com.example.familytree.domain.Member;
import java.util.List;import java.util.Optional;
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

  @Query(
      value =
          "update members "
              + "set marital_status = :maritalStatus, marital_search = :maritalSearch "
              + "where id = :id",
      nativeQuery = true)
  void updateMaritalStatus(String maritalStatus, String maritalSearch, Long id);

  @Query(
      value =
          "SELECT mb.*\n"
              + "FROM members mb\n"
              + "WHERE LOWER(mb.name_search) LIKE LOWER(CONCAT('%', :fullName, '%'))",
      nativeQuery = true)
  List<Member> findAllByFullName(String fullName);
}
