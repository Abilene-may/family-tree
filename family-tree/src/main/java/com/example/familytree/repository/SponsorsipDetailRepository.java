package com.example.familytree.repository;

import com.example.familytree.domain.FinancialSponsorship;
import com.example.familytree.domain.SponsorsipDetail;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SponsorsipDetailRepository extends JpaRepository<SponsorsipDetail, Long>,
    JpaSpecificationExecutor<SponsorsipDetail> {

  List<SponsorsipDetail> findAllByFinancialSponsorshipId(Long financialSponsorshipId);

  @Query(
      value =
          "SELECT *\n"
              + "FROM sponsorship_detail\n"
              + "WHERE (date <= :effectiveEndDate)\n"
              + "    AND (date >= :effectiveStartDate)\n"
              + "order by date ASC",
      nativeQuery = true)
  List<SponsorsipDetail> findAllByStartDateAndEndDate(
      LocalDate effectiveStartDate, LocalDate effectiveEndDate);

  @Query(
      value =
          "SELECT *\n"
              + "FROM sponsorship_detail\n"
              + "WHERE (date >= :effectiveStartDate)\n"
              + "order by date ASC",
      nativeQuery = true)
  List<SponsorsipDetail> findAllByStartDate(LocalDate effectiveStartDate);

  @Query(
      value =
          "SELECT *\n"
              + "FROM sponsorship_detail\n"
              + "WHERE (date <= :effectiveEndDate)\n"
              + "order by date ASC",
      nativeQuery = true)
  List<SponsorsipDetail> findAllByEndDate(LocalDate effectiveEndDate);
}
