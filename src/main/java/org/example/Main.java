package org.example;

import Connection.ConnectionFactory;
import DataAccess.ClientDao;
import Model.Client;
import Presentation.*;

import javax.naming.ldap.Control;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

       /// ConnectionFactory factory = new ConnectionFactory();
       //ConnectionFactory.getConnection();
new ControllerView();
        /// new ControllerClient();
       /// new ControllerProduct();
       /// new ControllerOrder();

    }
}