package com.example.familytree.repository;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.domain.RevenueManagement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface RevenueDetailRepository extends JpaRepository<RevenueDetail, Long>,
    JpaSpecificationExecutor<RevenueDetail> {
  @Query(value = "select max(id) from revenue_detail", nativeQuery = true)
  Long getId();

  List<RevenueDetail> findAllByIdRevenueManagement(Long idRevenueManagemnet);

  @Query(
      value =
          "SELECT *\n"
              + "FROM revenue_detail\n"
              + "WHERE (date <= :effectiveEndDate\n"
              + "    AND date >= :effectiveStartDate) AND status is true\n"
              + "order by date ASC",
      nativeQuery = true)
  List<RevenueDetail> findAllByStartDateAndEndDate(
      LocalDate effectiveStartDate, LocalDate effectiveEndDate);
}
