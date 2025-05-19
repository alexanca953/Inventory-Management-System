package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import DataAccess.BillDao;
import DataAccess.OrderDao;
import Model.Bill;
import Model.Client;
import Model.Orders;
import Model.Product;
import com.sun.jdi.InvalidLineNumberException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Controller class for managing order operations.
 * Handles creating, deleting, and updating orders through the GUI.
 * Supports selecting clients, choosing products with quantities, and generating bills.
 */
public class ControllerOrder {
   public ViewOrder view;
    public OrderBLL orderBLL;
    private ArrayList<Orders> orders;
    /**
     * Constructs the controller and sets up all GUI event listeners.
     * Loads existing orders into the table and handles order creation, deletion, and saving.
     */
    public ControllerOrder() {
        view=new ViewOrder();
        orderBLL =new OrderBLL();
        orderBLL.populateTableFromList(view.getOrderTable(), orderBLL.getAllOrders());
// Handle adding a new order
        view.getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Select Order Details");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());
// Build client selection panel
                JPanel clientPanel = new JPanel();
                clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
                clientPanel.setBorder(BorderFactory.createTitledBorder("Select a Client"));

                ButtonGroup clientGroup = new ButtonGroup();
                ArrayList<Client> clients = (ArrayList<Client>) new ClientBLL().getAllClients();
                ArrayList<JRadioButton> clientButtons = new ArrayList<>();

                clients.forEach(c -> {
                    JRadioButton clientRadio = new JRadioButton(c.getFirstName() + " " + c.getLastName());
                    clientGroup.add(clientRadio);
                    clientPanel.add(clientRadio);
                    clientButtons.add(clientRadio);
                });
                JScrollPane clientScroll = new JScrollPane(clientPanel);
                clientScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                // Build product selection panel
                JPanel productPanel = new JPanel();
                productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
                productPanel.setBorder(BorderFactory.createTitledBorder("Select Products"));
                ArrayList<Product> products = new ProductBLL().getAllProducts();
                ArrayList<JCheckBox> productCheckboxes = new ArrayList<>();
                ArrayList<Integer> selectedQuantities = new ArrayList<>();
                for (Product p : products) {
                    JCheckBox productCheck = new JCheckBox(p.getName() + " - " + p.getDescription() + " - $" + p.getPrice() + " - Stock: " + p.getStockQuantity());
                    productCheckboxes.add(productCheck);
                    selectedQuantities.add(0);
                    productPanel.add(productCheck);
                    productCheck.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (productCheck.isSelected()) {
                                String input = JOptionPane.showInputDialog(frame, "Enter quantity for " + p.getName() + " (max " + p.getStockQuantity() + "):");
                                try {
                                    int qty = Integer.parseInt(input);
                                    if (qty > p.getStockQuantity() || qty <= 0) {
                                        JOptionPane.showMessageDialog(frame, "Invalid quantity! Max: " + p.getStockQuantity());
                                        productCheck.setSelected(false);
                                    } else {
                                        selectedQuantities.set(productCheckboxes.indexOf(productCheck), qty);
                                    }
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
                                    productCheck.setSelected(false);
                                }
                            } else {
                                selectedQuantities.set(productCheckboxes.indexOf(productCheck), 0);
                            }
                        }
                    });
                }
                JScrollPane productScroll = new JScrollPane(productPanel);
                productScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, clientScroll, productScroll);
                splitPane.setDividerLocation(300);
                frame.add(splitPane, BorderLayout.CENTER);

                JButton confirmBtn = new JButton("Confirm");
                confirmBtn.setPreferredSize(new java.awt.Dimension(120, 40));

                confirmBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Client selectedClient = null;
                        for (int i = 0; i < clientButtons.size(); i++) {
                            if (clientButtons.get(i).isSelected()) {
                                selectedClient = clients.get(i);
                                break;
                            }
                        }

//                        if (selectedClient == null) {
//                            JOptionPane.showMessageDialog(frame, "Please select a client.");
//                            return;
//                        }

                        ArrayList<Product> selectedProducts = new ArrayList<>();
                        ArrayList<Integer> quantities = new ArrayList<>();

                        for (int i = 0; i < productCheckboxes.size(); i++) {
                            if (productCheckboxes.get(i).isSelected()) {
                                selectedProducts.add(products.get(i));
                                quantities.add(selectedQuantities.get(i));
                            }
                        }
                        int priceCal=0;
                        int i=0;

                        String notes="";
                        for(Product p : selectedProducts) {
                            priceCal+=p.getPrice()*quantities.get(i);
                            notes+=p.getName()+"*"+quantities.get(i)+", ";
                            i++;
                        }
                        String shippingAddress=JOptionPane.showInputDialog(frame,"Enter Shipping Address");
                        frame.dispose();
                     try
                     {
                         orderBLL.insertOrder(new Orders(0,selectedClient.getId(), Date.valueOf(LocalDate.now()).toString(),priceCal,shippingAddress,notes),selectedProducts,selectedClient);
                         orderBLL.populateTableFromList(view.getOrderTable(), orderBLL.getAllOrders());
                         ProductBLL productBLL=new ProductBLL();
                         i=0;
                         for(Product p : selectedProducts) {
                             System.out.println(p.toString()+ quantities.get(i));
                             p.setStockQuantity(p.getStockQuantity()-quantities.get(i));
                             i++;
                             productBLL.update(p);
                         }

                         // Insert bill
                         new BillDao().insertBill(new Bill(0,selectedClient.getId(),selectedClient.getFirstName()+" "+selectedClient.getLastName(),notes,priceCal, Date.valueOf(LocalDate.now()).toString()));
                     }
                     catch (NullPointerException ex)
                     {
                         JOptionPane.showMessageDialog(frame, "Please enter a valid number.error:"+ex.getMessage());
                     }

                    }
                });
                JPanel bottomPanel = new JPanel();
                bottomPanel.add(confirmBtn);
                frame.add(bottomPanel, BorderLayout.SOUTH);
                frame.setVisible(true);
            }
        });
// Delete order by ID
        view.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    orderBLL.deleteOrder(Integer.parseInt(JOptionPane.showInputDialog(null, "Enter id of the order to delete")));
                    orderBLL.populateTableFromList(view.getOrderTable(), orderBLL.getAllOrders());
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Enter a valid id");
                }
            }
        });
        // Save changes to order table
        view.getSaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTable table = view.getOrderTable();
                int rowCount = table.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    try {
                        int id = Integer.parseInt(table.getValueAt(i, 0).toString());
                        int clientId =Integer.parseInt( table.getValueAt(i, 1).toString());
                        String orderDate = table.getValueAt(i, 2).toString();
                        int price = Integer.parseInt(table.getValueAt(i, 3).toString());
                        String shippingAddress = table.getValueAt(i, 4).toString();
                        String notes = table.getValueAt(i, 5).toString();
                        Orders order= new Orders(id,clientId,orderDate,price,shippingAddress,notes);
                        orderBLL.updateOrder(order);
                    } catch (Exception ex) {
                        ///ex.printStackTrace();
                        JOptionPane.showMessageDialog(view, "Error while saving row " + (i + 1));
                    }
                }
               orders = orderBLL.getAllOrders();
                orderBLL.populateTableFromList(table, orders);
                JOptionPane.showMessageDialog(view, "Changes saved successfully!");
            }

        });
    }
}
