package com.library.backend_epilogo.models;

import com.library.backend_epilogo.Models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")  // Changed from "auditoria"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {  // Changed from "Auditoria"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")  // Changed from "id_auditoria"
    private Long auditId;  // Changed from "idAuditoria"

    @Column(name = "affected_table", nullable = false, length = 50)  // Changed from "tabla_afectada"
    private String affectedTable;  // Changed from "tablaAfectada"

    @Column(name = "record_id", nullable = false)  // Changed from "id_registro"
    private Long recordId;  // Changed from "idRegistro"

    @Column(name = "action", nullable = false, length = 20)  // Changed from "accion"
    private String action;  // Changed from "accion"

    @Column(name = "date")  // Changed from "fecha"
    private LocalDateTime date;  // Changed from "fecha"

    @ManyToOne
    @JoinColumn(name = "id_user")  // Changed from "usuario_responsable"
    private User idUser;  // Changed from "usuarioResponsable"

    @Column(name = "modified_data", columnDefinition = "jsonb")  // Changed from "datos_modificados"
    @JdbcTypeCode(SqlTypes.JSON)
    private String modifiedData;  // Changed from "datosModificados"
}