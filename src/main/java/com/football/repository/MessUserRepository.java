package com.football.repository;

import com.football.domain.MessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lenovo on 2018-3-28.
 */
public interface MessUserRepository extends JpaRepository<MessUser, String>,JpaSpecificationExecutor<MessUser> {
}
