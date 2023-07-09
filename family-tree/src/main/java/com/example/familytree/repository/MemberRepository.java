package com.example.familytree.repository;

import com.example.familytree.domain.Member;
import java.time.LocalDate;
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
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(full_name) LIKE LOWER(CONCAT('%', :fullName, '%'))",
      nativeQuery = true)
  List<Member> findAllByFullName(String fullName);
  @Query(
      value =
          "SELECT *\n"
              + "FROM members \n"
              + "WHERE LOWER(gender) LIKE LOWER(CONCAT('%', :gender, '%'))"
              + " order by date_of_birth ",
      nativeQuery = true)
  List<Member> findAllByGender(String gender);

  @Query(
      value =
          "SELECT *\n"
              + "FROM members\n"
              + "WHERE ((date_of_birth <= :endDate AND date_of_birth >= :startDate)"
              + "AND status is distinct from :status)"
              + " order by date_of_birth ",
      nativeQuery = true)
  List<Member> findAllAgeInTheRange(LocalDate endDate, LocalDate startDate, String status);

  @Query(
      value =
          "select * from members\n"
              + "where (date_of_death is distinct from null or status like :status) "
              + "and LOWER(gender) LIKE LOWER(CONCAT('%', :gender, '%'))",
      nativeQuery = true)
  List<Member> findAllByDateOfDeathOrStatusAndGender(String status, String gender);

  @Query(
      value = "select distinct generation from members order by generation ASC",
      nativeQuery = true)
  List<Integer> findAllByGenderation();

  @Query(
      value =
          "SELECT *\n"
              + "FROM members\n"
              + "WHERE status is distinct from :status "
              + "order by id ASC"
              + " order by date_of_birth ",
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
              + "  and status is distinct from :status"
              + " order by date_of_birth ",
      nativeQuery = true)
  List<Member> findAllByDateOfBirthAndGender(
      LocalDate startDate, LocalDate endDate, String gender, String status);

  @Query(
      value = "select * from members where username is not null",
      nativeQuery = true)
  List<Member> findAllByUserName();

  @Query(
      value =
          "select * from members\n"
              + "where LOWER(role) LIKE LOWER(CONCAT('%', :role, '%'))",
      nativeQuery = true)
  List<Member> findAllByRole(String role);
  @Query(value = "SELECT MAX(last_value + 1) FROM member_seq", nativeQuery = true)
  Long findMaxSeq();

  @Query(value = "select * from members where role = :role and id is distinct from :id", nativeQuery = true)
  Optional<Member> checkExistRoleUpdate(String truongHo, Long id);

  @Query(value = "select * from members where username = :userName and id is distinct from :memberId"
      , nativeQuery = true)
  Optional<Member> findByUserNameUpdate(String userName, Long memberId);
}
