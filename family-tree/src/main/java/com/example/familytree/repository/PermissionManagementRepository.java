package com.example.familytree.repository;

import com.example.familytree.domain.PermissionManagement;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PermissionManagementRepository extends JpaRepository<PermissionManagement, Long>,
    JpaSpecificationExecutor<PermissionManagement> {

  @Query(
      value =
          "select *\n"
              + "from permission_management\n"
              + "where permission_group_name like :role",
      nativeQuery = true)
  Optional<PermissionManagement> findByPermissionGroupName(String role);
}
