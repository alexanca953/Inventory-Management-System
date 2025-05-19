package Model;

import java.sql.Date;
/**
 * Represents an order placed by a client.
 * Contains information about the order ID, client ID, order date,
 * total price, shipping address, and any additional notes.
 */
public class Orders {
private int id;
private int clientId;
private String orderDate;
private int price;
private String shippingAddress;
private String notes;
public Orders(int id, int clientId, String orderDate, int price, String shippingAddress, String notes) {
    this.id = id;
    this.clientId = clientId;
    this.orderDate = orderDate;
    this.price = price;
    this.shippingAddress = shippingAddress;
    this.notes = notes;
}
public Orders()
{
    this.id = 0;
    this.clientId = 0;
    this.orderDate = null;
    this.price = 0;
    this.shippingAddress = "";
    this.notes = "";

}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public int getClientId() {
    return clientId;
}
public void setClientId(int clientId) {
    this.clientId = clientId;
}
public String getOrderDate() {
    return orderDate;
}
public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
}
public int getPrice() {
    return price;

}
public void setPrice(int price) {
    this.price = price;
}
public String getShippingAddress() {
    return shippingAddress;
}
public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
}
public String getNotes() {
    return notes;

}
public void setNotes(String notes) {
    this.notes = notes;
}
}
