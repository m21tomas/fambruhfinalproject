package coms.repository;

public enum OrderStatus {
    PLACED,
    PROCESSING,
    CONFIRMED,
    ON_THE_WAY,
    OUT_OF_DELIVERY,
    SHIPPED,
    DELIVERED,
    RETURN_REQUESTED,
    RETURN_APPROVED,
    RETURN_REJECTED,
    EXCHANGE_REQUESTED,
    EXCHANGE_APPROVED,
    EXCHANGE_REJECTED,
    CANCELLED;
}