package com.example.devrel.repository;

import com.example.devrel.model.GitUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GitUserRepository extends JpaRepository<GitUser, String> {

    List<GitUser> findAllByFollowersGreaterThan(int n);
}
