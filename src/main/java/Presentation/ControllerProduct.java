package Presentation;

import BusinessLogic.ProductBLL;
import DataAccess.ProductDao;
import Model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * Controller class for managing product-related interactions between the GUI and business logic.
 * Handles add, delete, and update operations for products.
 */
public class ControllerProduct {
    public ViewProduct viewProduct;
    public ProductBLL productBLL;
    private ArrayList<Product> products;

    /**
     * Creates the controller and sets up listeners for the product management window.
     * It initializes the table, loads products, and configures button actions.
     */
    public ControllerProduct() {
        viewProduct = new ViewProduct();
        productBLL = new ProductBLL();
        products = productBLL.getAllProducts();
        productBLL.populateTableFromList(viewProduct.getProductTable(), products);
        // Add new product
        viewProduct.getAddButton().addActionListener(e -> {
            String name = JOptionPane.showInputDialog(viewProduct, "Enter product name");
            if (name == null || name.trim().isEmpty()) return;
            try {
                int stock = Integer.parseInt(JOptionPane.showInputDialog(viewProduct, "Enter stock"));
                int price = Integer.parseInt(JOptionPane.showInputDialog(viewProduct, "Enter price"));
                String description = JOptionPane.showInputDialog(viewProduct, "Enter description");
                if (description == null) return;
                productBLL.insert(new Product(0, name, description, price, stock));
                products = productBLL.getAllProducts();
                productBLL.populateTableFromList(viewProduct.getProductTable(), products);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewProduct, "Invalid input for stock or price.");
            }
        });
        // Delete product by ID
        viewProduct.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(viewProduct, "Enter the id to delete:"));
                    productBLL.delete(id);
                    products = productBLL.getAllProducts();
                    productBLL.populateTableFromList(viewProduct.getProductTable(), products);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(viewProduct, "Invalid id entered");
                }

            }
        });
        // Save all edited products from the table
        viewProduct.getSaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable table = viewProduct.getProductTable();
                int rowCount = table.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    try {
                        int id = Integer.parseInt(table.getValueAt(i, 0).toString());
                        String name = table.getValueAt(i, 1).toString();
                        String description = table.getValueAt(i, 2).toString();
                        int price  = Integer.parseInt(table.getValueAt(i, 3).toString());
                        int stock= Integer.parseInt(table.getValueAt(i, 4).toString());
                        Product product = new Product(id, name, description,  price,stock);
                        productBLL.update(product);
                    } catch (Exception ex) {
                       // ex.printStackTrace();
                        JOptionPane.showMessageDialog(viewProduct, "Error while saving row " + (i + 1));
                    }
                }
                products = productBLL.getAllProducts();
                productBLL.populateTableFromList(table, products);
                JOptionPane.showMessageDialog(viewProduct, "Changes saved successfully!");
            }
        });
    }
}
