package Presentation;

import javax.swing.*;
import java.awt.*;
/**
 * Represents the GUI window for managing products.
 * It includes a table to display products and buttons to add, delete, or save changes.
 */
public class ViewProduct extends JFrame {
    private JTable productTable = new JTable();
    private JButton addButton = new JButton("Add");
    private JButton deleteButton = new JButton("Delete");
    private JButton saveButton = new JButton("Save");
    /**
     * Creates a new product management window with all UI components initialized.
     */
    public ViewProduct() {
        setTitle("Product Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(saveButton);

        JScrollPane tableScrollPane = new JScrollPane(productTable);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.WEST);
        add(tableScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTable getProductTable() {
        return productTable;
    }
}
