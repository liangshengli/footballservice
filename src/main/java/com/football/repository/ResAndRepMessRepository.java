package com.football.repository;

import com.football.domain.ResAndRepMess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lenovo on 2018-3-23.
 */
public interface ResAndRepMessRepository  extends JpaRepository<ResAndRepMess, String>,JpaSpecificationExecutor<ResAndRepMess> {
}
