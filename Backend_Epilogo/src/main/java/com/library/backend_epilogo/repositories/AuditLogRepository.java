package com.library.backend_epilogo.repositories;

import com.library.backend_epilogo.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByAffectedTableAndRecordId(String affectedTable, Long recordId);
}
