package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import DataAccess.OrderDao;
import Model.Client;
import Model.Orders;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * Controller class responsible for managing client-related actions
 * in the user interface. It connects the ViewClient with the ClientBLL
 * business logic layer to handle client data operations.
 */
public class ControllerClient {
public ViewClient viewClient;
   public ClientBLL clientBLL;
    private ArrayList<Client> clients;
    /**
     * Constructs the ControllerClient, initializes the view and business logic,
     * loads all clients, and sets up event handlers for the Add, Delete, and Save buttons.
     */
    public ControllerClient() {
         viewClient = new ViewClient();
         clientBLL = new ClientBLL();
         clients= clientBLL.getAllClients();
        // Populate client table in the view with existing clients
         clientBLL.populateTableFromList(viewClient.getClientTable(),clients);
// Add client button handler: prompts for client details and inserts a new client
         viewClient.getAddButton().addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                     String firstName=  JOptionPane.showInputDialog(viewClient,"Enter first name");
                     String lastName=  JOptionPane.showInputDialog(viewClient,"Enter last name");
                     int ok=0;
                 String email = "";
                     while(ok==0){
                         email= JOptionPane.showInputDialog(viewClient,"Enter email"); 
                         ArrayList <Client> l2= clientBLL.getAllClients();
                         boolean exist=false;
                         for(Client c:l2){
                             if(c.getEmail().equals(email)){
                                 exist=true;
                                 break;
                             }
                         }
                         if(exist==false)
                             ok=1;
                     }
                     String phone=  JOptionPane.showInputDialog(viewClient,"Enter phone");
                     String address=  JOptionPane.showInputDialog(viewClient,"Enter address");
                     try
                     {
                         clientBLL.insert(new Client(0,firstName,lastName,email,phone,address));
                         clients= clientBLL.getAllClients();
                         clientBLL.populateTableFromList(viewClient.getClientTable(),clients);
                     }
                     catch(Exception ex)
                     {
                         JOptionPane.showMessageDialog(viewClient,ex);
                     }
             }
         });
        // Delete client button handler: deletes client and associated orders by id
        viewClient.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int id;
                try {
                    id = Integer.parseInt(JOptionPane.showInputDialog(viewClient, "Enter the id to delete:"));
                    Client c2= clientBLL.findById(id);
                    OrderBLL orderBLL = new OrderBLL();
                    ArrayList <Orders> oList= orderBLL.getAllOrders();
                    for(Orders o:oList){
                        if(o.getClientId()==c2.getId()){
                            orderBLL.deleteOrder(o.getId());
                        }
                    }
                    // Delete client and refresh list
                    clientBLL.delete(id);
                    clients= clientBLL.getAllClients();
                    clientBLL.populateTableFromList(viewClient.getClientTable(),clients);
                }
                   catch(Exception ex)
                   {
                       JOptionPane.showMessageDialog(viewClient,"Enter a valid id");
                   }


            }
        });
        // Save button handler: saves changes from the table back to the database
        viewClient.getSaveButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = viewClient.getClientTable();
                int rowCount = table.getRowCount();
                for (int i = 0; i < rowCount; i++)
                {
                    try {
                        int id = Integer.parseInt(table.getValueAt(i, 0).toString());
                        String firstName = table.getValueAt(i, 1).toString();
                        String lastName = table.getValueAt(i, 2).toString();
                        String email = table.getValueAt(i, 3).toString();
                        String phone = table.getValueAt(i, 4).toString();
                        String address = table.getValueAt(i, 5).toString();
                        Client client = new Client(id, firstName, lastName, email, phone, address);
                        clientBLL.update(client);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(viewClient, "Error while saving row " + (i + 1));
                    }
                }
                // Refresh the table with updated client data
                clients = clientBLL.getAllClients();
                clientBLL.populateTableFromList(table, clients);
                JOptionPane.showMessageDialog(viewClient, "Changes saved successfully!");
            }
        });
    }
}
