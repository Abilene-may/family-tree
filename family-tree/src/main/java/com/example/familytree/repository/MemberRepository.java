package com.example.familytree.repository;

import com.example.familytree.domain.Member;
import java.time.LocalDate;
import java.util.List;import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository
    extends JpaRepository<Member, Long>,
    JpaSpecificationExecutor<Member> {
  @Query(value = "select * from members where role_search = :role", nativeQuery = true)
  Optional<Member> checkExistRole(String role);
  @Query(value = "select * from members where username = :userName", nativeQuery = true)
  Optional<Member> findByUserName(String userName);

  @Query(
      value =
          "update members "
              + "set marital_status = :maritalStatus, marital_search = :maritalSearch, id = :id "
              + "where id = :id",
      nativeQuery = true)
  @Transactional
  @Modifying
  void updateMaritalStatus(String maritalStatus, String maritalSearch, Long id);

  @Query(
      value =
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(name_search) LIKE LOWER(CONCAT('%', :fullName, '%'))",
      nativeQuery = true)
  List<Member> findAllByFullName(String fullName);
  @Query(
      value =
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(gender_search) LIKE LOWER(CONCAT('%', :gender, '%'))",
      nativeQuery = true)
  List<Member> findAllByGender(String gender);

  @Query(
      value =
          "SELECT *\n"
              + "FROM members\n"
              + "WHERE date_of_birth <= :endDate \n"
              + "  AND date_of_birth >= :startDate",
      nativeQuery = true)
  List<Member> findAllAgeInTheRange(LocalDate endDate, LocalDate startDate);
}
