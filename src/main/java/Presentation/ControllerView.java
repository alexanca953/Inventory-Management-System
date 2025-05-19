package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Controls the main view of the application.
 * Handles navigation between Clients, Orders, and Products windows.
 */
public class ControllerView {
    View view;
    /**
     * Creates the main controller and sets up action listeners for the main menu buttons.
     * When a button is clicked, the corresponding controller is initialized.
     */
    public ControllerView() {
        view=new View();
        view.setVisible(true);
        view.getClientsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ControllerClient();
            }
        });
        view.getOrdersButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ControllerOrder();
            }
        });
        view.getProductsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ControllerProduct();
            }
        });
    }

}
