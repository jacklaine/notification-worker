package org.db1.inventory.entrypoint.consumer;

import org.db1.inventory.application.usecase.ProcessLowStockAlertUseCase;
import org.db1.inventory.infra.messaging.kafka.dto.LockStockAlertKafka;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KafkaConsumer {

    @Inject
    ProcessLowStockAlertUseCase processLowStockAlertUseCase;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Logger logger;

    @Incoming("low-stock-in")
    public void consumeLowStockAlert(String message) {
        try {
            LockStockAlertKafka alert = objectMapper.readValue(message, LockStockAlertKafka.class);
            processLowStockAlertUseCase.process(alert);
        } catch (Exception e) {
            logger.error("Falha ao processar mensagem de alerta de baixo estoque: " + e.getMessage(), e);
        }
    }
}
