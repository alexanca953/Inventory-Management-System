package Model;
/**
 * Represents a billing record for an order.
 */
public record Bill(int id, int orderId, String clientName, String products,int price, String date) {}