package com.example.familytree.repository;

import com.example.familytree.domain.RevenueDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface RevenueDetailRepository extends JpaRepository<RevenueDetail, Long>,
    JpaSpecificationExecutor<RevenueDetail> {
  @Query(value = "select max(id) from revenue_detail", nativeQuery = true)
  Long getId();

  List<RevenueDetail> findAllByIdRevenueManagement(Long idRevenueManagemnet);
}
