package Presentation;

import javax.swing.*;
import java.awt.*;
/**
 * Main window of the warehouse application.
 * Provides navigation buttons to access clients, products, and orders management.
 */
public class View extends JFrame {
    private JButton clientsButton;
    private JButton productsButton;
    private JButton ordersButton;
    /**
     * Creates the main application window with a title and navigation buttons.
     */
    public View() {
        setTitle("Warehouse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Warehouse", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        clientsButton = new JButton("Clients");
        productsButton = new JButton("Products");
        ordersButton = new JButton("Orders");

        buttonPanel.add(clientsButton);
        buttonPanel.add(productsButton);
        buttonPanel.add(ordersButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public JButton getClientsButton() {
        return clientsButton;
    }

    public JButton getProductsButton() {
        return productsButton;
    }

    public JButton getOrdersButton() {
        return ordersButton;
    }
}
