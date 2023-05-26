package com.example.familytree.repository;

import com.example.familytree.domain.RevenueDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RevenueDetailRepository extends JpaRepository<RevenueDetail, Long>,
    JpaSpecificationExecutor<RevenueDetail> {

  @Query(
      value =
          "select * from revenue_detail\n"
              + "where year = :year and "
              + "(LOWER(type_of_revenue_search) LIKE LOWER(CONCAT('%', :typeOfRevenueSearch , '%')))",
      nativeQuery = true)
  List<RevenueDetail> getAllByTypeOfRevenue(Integer year, String typeOfRevenueSearch);
  @Query(
      value = "select * from revenue_detail where id_revenue_management = :idRevenueManagement",
      nativeQuery = true)
  List<RevenueDetail> findAllByIdManagement(Long idRevenueManagement);
}
