package com.library.backend_epilogo.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.backend_epilogo.models.AuditLog;
import com.library.backend_epilogo.models.User;
import com.library.backend_epilogo.repositories.AuditLogRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserAuditListener {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        UserAuditListener.applicationContext = applicationContext;
    }

    private AuditLogRepository getAuditLogRepository() {
        return applicationContext.getBean(AuditLogRepository.class);
    }

    private EntityManager getEntityManager() {
        return applicationContext.getBean(EntityManager.class);
    }

    @PrePersist
    public void prePersist(User user) {
        logAudit(user, "CREATE");
    }

    @PreUpdate
    public void preUpdate(User user) {
        logAudit(user, "UPDATE");
    }

    @PreRemove
    public void preRemove(User user) {
        logAudit(user, "DELETE");
    }

    private void logAudit(User user, String action) {
        try {
            // Inyectar dependencias si es necesario
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

            AuditLog auditLog = new AuditLog();
            auditLog.setAffectedTable("users");
            if( user.getUserId() != null ){
                auditLog.setRecordId(user.getUserId());
            } else {
                auditLog.setRecordId(1L);
            }
            auditLog.setAction(action);
            auditLog.setTimestamp(LocalDateTime.now());

            // Obtener usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                User currentUser = (User) authentication.getPrincipal();
                auditLog.setResponsibleUser(currentUser);
            }

            // Convertir cambios a JSON
            Map<String, Object> changes = new HashMap<>();
            changes.put("username", user.getUsername());
            changes.put("email", user.getEmail());
            // No incluimos la contraseña por seguridad

            ObjectMapper objectMapper = new ObjectMapper();
            auditLog.setModifiedData(objectMapper.writeValueAsString(changes));

            // Guardar el log de auditoría
            AuditLogRepository auditLogRepository = getAuditLogRepository();
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            // Log the error but don't stop the transaction
            System.err.println("Error logging audit: " + e.getMessage());
        }
    }
}