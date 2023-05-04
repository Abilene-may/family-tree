package com.example.familytree.repository;

import com.example.familytree.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository
    extends JpaRepository<User, Long>,
        PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User> {

  @Query(value = "select * from users where user_name = :userName", nativeQuery = true)
  Optional<User> findByUserName(String userName);
}
