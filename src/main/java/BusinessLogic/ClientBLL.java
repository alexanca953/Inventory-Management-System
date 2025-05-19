package BusinessLogic;


import BusinessLogic.validators.NotEmptyValidator;
import Model.Client;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import BusinessLogic.validators.EmailValidator;
import BusinessLogic.validators.Validator;
import DataAccess.ClientDao;

import javax.swing.*;
/**
 * <p>
 * Business Logic Layer class for managing Client entities.
 * Applies validation rules and delegates CRUD operations to ClientDao.
 * </p>
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    ClientDao clientDAO;
    /**
     * <p>
     * Constructor initializes the list of validators and the Client DAO.
     * </p>
     */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        validators.add(new NotEmptyValidator());
        clientDAO = new ClientDao();
    }
    /**
     * <p>
     * Deletes a client by id.
     * </p>
     *
     * @param id the ID of the client to be deleted
     */
 public void delete(int id) {
        clientDAO.delete(id);
    }
    /**
     * <p>
     * Retrieves all clients from the database.
     * </p>
     *
     * @return a list containing all clients
     */
 public ArrayList<Client> getAllClients() {
        return clientDAO.getAllClients();
    }
    /**
     * <p>
     * Finds a client by its id.
     * </p>
     *
     * @param id the ID of the client to be found
     * @return the client with the specified ID
     * @throws NoSuchElementException if no client with the given ID exists
     */
 public Client findById(int id) {
        Client client = clientDAO.findById(id);
        if(client == null)
            throw new NoSuchElementException("Client with id " + id + " not found");
        return client;
    }
    /**
     * <p>
     * Inserts a new client after validating it.
     * </p>
     *
     * @param client the client to be inserted
     * @return the inserted client
     */
 public Client insert(Client client) {
        validators.forEach(v -> v.validate(client));
        return clientDAO.insert(client);
    }
    /**
     * <p>
     * Updates an existing client after validating it.
     * </p>
     *
     * @param client the client to be updated
     * @return the updated client
     */
 public Client update(Client client) {
        validators.forEach(v -> v.validate(client));
        return clientDAO.update(client);
    }
    /**
     * <p>
     * Populates a JTable with client data from a list of clients.
     * </p>
     *
     * @param table the JTable to populate
     * @param clients the list of clients to populate the table with
     */
 public void populateTableFromList(JTable table, List<Client> clients)
    {
        clientDAO.populateTableFromList(table, clients);
    }

}