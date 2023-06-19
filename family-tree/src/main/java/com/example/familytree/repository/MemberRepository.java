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
  @Query(value = "select * from members where role = :role", nativeQuery = true)
  Optional<Member> checkExistRole(String role);
  @Query(value = "select * from members where username = :userName", nativeQuery = true)
  Optional<Member> findByUserName(String userName);

  @Query(
      value =
          "update members "
              + "set marital_status = :maritalStatus, id = :id "
              + "where id = :id",
      nativeQuery = true)
  @Transactional
  @Modifying
  void updateMaritalStatus(String maritalStatus, Long id);

  @Query(
      value =
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(full_name) LIKE LOWER(CONCAT('%', :fullName, '%'))",
      nativeQuery = true)
  List<Member> findAllByFullName(String fullName);
  @Query(
      value =
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(gender) LIKE LOWER(CONCAT('%', :gender, '%'))",
      nativeQuery = true)
  List<Member> findAllByGender(String gender);

  @Query(
      value =
          "SELECT *\n"
              + "FROM members\n"
              + "WHERE ((date_of_birth <= :endDate AND date_of_birth >= :startDate)"
              + "AND status is distinct from :status)"
              + "order by id ASC",
      nativeQuery = true)
  List<Member> findAllAgeInTheRange(LocalDate endDate, LocalDate startDate, String status);

  @Query(
      value =
          "select * from members\n"
              + "where date_of_death is distinct from null "
              + "and LOWER(gender) LIKE LOWER(CONCAT('%', :gender, '%'))",
      nativeQuery = true)
  List<Member> findAllByDateOfDeathAndGender(String gender);

  @Query(
      value = "select distinct generation from members order by generation ASC",
      nativeQuery = true)
  List<Integer> findAllByGenderation();

  @Query(
      value =
          "SELECT *\n"
              + "FROM members\n"
              + "WHERE status is distinct from :status "
              + "order by id ASC",
      nativeQuery = true)
  List<Member> findAllByStatus(String status);

  @Query(
      value =
          "select min(date_of_birth) \n"
              + "from members \n"
              + "where status is distinct from :status ",
      nativeQuery = true)
  LocalDate findByDateOfBirth(String status);

  @Query(
      value =
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(gender) LIKE LOWER(CONCAT('%', :gender, '%'))"
              + " and status is distinct from :status "
              + " order by date_of_birth ",
      nativeQuery = true)
  List<Member> findAllGuestByGender(String gender, String status);

  @Query(
      value =
          "select *\n"
              + "from members\n"
              + "where (date_of_birth >= :startDate and date_of_birth <= :endDate)\n"
              + "  and gender like :gender \n"
              + "  and status is distinct from :status",
      nativeQuery = true)
  List<Member> findAllByDateOfBirthAndGender(
      LocalDate startDate, LocalDate endDate, String gender, String status);
}
