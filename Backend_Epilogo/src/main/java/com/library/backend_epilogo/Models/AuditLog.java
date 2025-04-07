package com.library.backend_epilogo.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id", nullable = false)
    private Long auditId;

    @Column(name = "affected_table", nullable = false, length = 50)
    private String affectedTable;

    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @Column(nullable = false, length = 20)
    private String action;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_user_id")
    private User responsibleUser;

    @Column(name = "modified_data")
    @JdbcTypeCode(SqlTypes.JSON)
    private String modifiedData;
}