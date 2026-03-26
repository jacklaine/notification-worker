CREATE TABLE low_stock_alert (
    id               BIGSERIAL PRIMARY KEY,
    sku              VARCHAR(100) NOT NULL,
    current_quantity INT NOT NULL,
    threshold        INT NOT NULL,
    received_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_low_stock_alert_sku ON low_stock_alert (sku);
CREATE INDEX idx_low_stock_alert_received_at ON low_stock_alert (received_at);
