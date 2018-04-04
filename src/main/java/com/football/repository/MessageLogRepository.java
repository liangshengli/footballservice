package com.football.repository;

import com.football.domain.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lenovo on 2018-3-28.
 */
public interface MessageLogRepository extends JpaRepository<MessageLog, String>,JpaSpecificationExecutor<MessageLog> {
}
