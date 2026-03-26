package org.db1.inventory.infra.persistence.entity;

import java.time.Instant;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "low_stock_alert")
public class LowStockAlertEntity extends PanacheEntity {

    @Column(nullable = false)
    private String sku;

    @Column(name = "current_quantity", nullable = false)
    private int currentQuantity;

    @Column(nullable = false)
    private int threshold;

    @Column(name = "received_at", nullable = false, updatable = false)
    private Instant receivedAt;

    public LowStockAlertEntity() {
        this.receivedAt = Instant.now();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }
}
