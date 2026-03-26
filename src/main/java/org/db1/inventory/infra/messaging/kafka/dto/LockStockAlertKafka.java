package org.db1.inventory.infra.messaging.kafka.dto;

public class LockStockAlertKafka {

    private String sku;
    private String productName;
    private int currentQuantity;
    private int threshold;
    
    public LockStockAlertKafka(String sku, String productName, int currentQuantity, int threshold) {
        this.sku = sku;
        this.productName = productName;
        this.currentQuantity = currentQuantity;
        this.threshold = threshold;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
}
