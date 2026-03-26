package org.db1.inventory.application.usecase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.db1.inventory.infra.messaging.kafka.dto.LockStockAlertKafka;
import org.db1.inventory.infra.persistence.entity.LowStockAlertEntity;
import org.db1.inventory.infra.persistence.repository.LowStockAlertRepository;

@ApplicationScoped
public class ProcessLowStockAlertUseCase {

    @Inject
    LowStockAlertRepository alertRepository;

    @Transactional
    public void process(LockStockAlertKafka alert) {

        LowStockAlertEntity entity = new LowStockAlertEntity();
        entity.setSku(alert.getSku());
        entity.setCurrentQuantity(alert.getCurrentQuantity());
        entity.setThreshold(alert.getThreshold());

        alertRepository.persist(entity);
    }
}
