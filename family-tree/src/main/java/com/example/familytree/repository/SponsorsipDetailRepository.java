package com.example.familytree.repository;

import com.example.familytree.domain.SponsorsipDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SponsorsipDetailRepository extends JpaRepository<SponsorsipDetail, Long>,
    JpaSpecificationExecutor<SponsorsipDetail> {

  List<SponsorsipDetail> findAllByFinancialSponsorshipId(Long financialSponsorshipId);
}
