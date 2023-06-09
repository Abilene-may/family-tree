package com.example.familytree.repository;

import com.example.familytree.domain.FinancialSponsorship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FinancialSponsorshipRepository
    extends JpaRepository<FinancialSponsorship, Long>,
        JpaSpecificationExecutor<FinancialSponsorship> {


}
