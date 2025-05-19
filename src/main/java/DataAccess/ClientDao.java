package DataAccess;

import Connection.ConnectionFactory;
import Model.Client;

import java.util.ArrayList;
/**
 * <p>Data Access Object (DAO) for Client entities.</p>
 * <p>Provides access to client-related database operations,
 * extending generic CRUD functionality from AbstractDAO.</p>
 */
public class ClientDao extends AbstractDAO<Client> {
    /**
     * <p>Retrieves all clients from the database.</p>
     *
     * @return a list of all clients stored in the database
     */
    public ArrayList<Client> getAllClients() {
        return (ArrayList<Client>) super.findAll();
    }
}
