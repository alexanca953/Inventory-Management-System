package Model;
/**
 * Represents a product with details like id, name, description, price, and stock quantity.
 */
public class Product {
 private    int id;
    private  String name;
    private  String description;
    private int price;
    private  int stockQuantity;
public Product(int id, String name, String description, int price, int stockQuantity) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;

}
public  Product( ) {

}
public int getId() {
    return id;

}
public void setId(int id) {
    this.id = id;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getDescription() {
    return description;
}
public void setDescription(String description) {
    this.description = description;
}
public int getPrice() {
    return price;
}
public void setPrice(int price) {
    this.price = price;
}
public int getStockQuantity() {
    return stockQuantity;
}
public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
}

}
