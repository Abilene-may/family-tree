package com.example.familytree.repository;

import com.example.familytree.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  @Query(value = "select user_name from user where user_name = :userName", nativeQuery = true)
  Optional<User> findByUserName(String userName);
}
