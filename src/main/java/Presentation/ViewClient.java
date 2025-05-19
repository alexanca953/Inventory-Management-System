package Presentation;

import javax.swing.*;
import java.awt.*;
/**
 * Represents the GUI window for managing clients.
 * It displays a table of clients and includes buttons to add, delete, and save client data.
 */
public class ViewClient extends JFrame {
    private JTable clientTable = new JTable();
    private JButton addButton = new JButton("Add");
    private JButton deleteButton = new JButton("Delete");
    private JButton saveButton = new JButton("Save");

    public ViewClient() {
        setTitle("Client Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panou vertical pentru butoane
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

        // Scroll pane pentru tabel
        JScrollPane tableScrollPane = new JScrollPane(clientTable);

        // Layout principal
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

    public JTable getClientTable() {
        return clientTable;
    }
}
