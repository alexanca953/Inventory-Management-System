package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 * Generic Abstract Data Access Object (DAO) class for operations.
 * Provides basic methods to interact with database tables mapped to entity classes.
 */
 public class AbstractDAO<T>{

    protected static final Logger LOGGER= Logger.getLogger(AbstractDAO.class.getName());
private final Class<T> type;
@SuppressWarnings("unchecked")
public AbstractDAO(){
    this.type=(Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
}
    /**
     * <p>Builds a SELECT query to retrieve an entity by a specified field.</p>
     *
     * @param field the field name used in the WHERE clause
     * @return the SQL SELECT query string
     */
 private String createSelectQuery(String field)
{
    StringBuilder sb=new StringBuilder();
    sb.append("SELECT ");
    sb.append("*");
    sb.append(" FROM ");
    sb.append(type.getSimpleName());
    sb.append(" WHERE " + field + "=?");
    return sb.toString();
}
    /**
     * <p>Builds a SELECT query to retrieve all rows from the table.</p>
     *
     * @return the SQL SELECT query string
     */
 private String createSelectAllQuery()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * <p>Builds an INSERT query with placeholders for all entity fields.</p>
     *
     * @return the SQL INSERT query string
     */
 private String insertQuery()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" VALUES (");
        int i=0;
        Field[] fields=type.getDeclaredFields();
        for(Field f:fields){
            sb.append("?");
            if(i!=fields.length-1)
                sb.append(", ");
            i++;
        }
        sb.append(")");
        return sb.toString();
    }
    /**
     * <p>Builds a DELETE query to remove entities by a specified field.</p>
     *
     * @param field the field name used in the WHERE clause
     * @return the SQL DELETE query string
     */
 public String createDeleteQuery(String field)
    {
    StringBuilder sb=new StringBuilder();
    sb.append("DELETE FROM ");
    sb.append(type.getSimpleName());
    sb.append(" WHERE ");
    sb.append(field + "=?");
    return sb.toString();
    }
    /**
     * <p>Builds an UPDATE query for the entity, excluding the key field from updates.</p>
     *
     * @param field the key field used in the WHERE clause
     * @return the SQL UPDATE query string
     */
    public String createUpdateQuery(String field)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(type.getSimpleName()).append(" SET ");

        Field[] fields = type.getDeclaredFields();
        boolean first = true;
        for (Field f : fields) {
            if (!f.getName().equals(field)) {
                if (!first) sb.append(", ");
                sb.append(f.getName()).append(" = ?");
                first = false;
            }
        }
        sb.append(" WHERE ").append(field).append(" = ?");
        return sb.toString();
    }

    /**
     * <p>Finds an entity by its unique identifier.</p>
     *
     * @param id the ID of the entity to retrieve
     * @return the found entity, or null if not found
     */
 public T findById(int id)
{
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;
    String query=createSelectQuery("id");
    try {
        connection= ConnectionFactory.getConnection();
        preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        resultSet= preparedStatement.executeQuery();
        return createObjects(resultSet).get(0);
    }
    catch(SQLException e){
        LOGGER.log(Level.WARNING,type.getName()+"DAO:findById");
    }
    finally {
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(preparedStatement);
        ConnectionFactory.close(connection);
    }
    return null;
}
    /**
     * <p>Retrieves all entities of this type from the corresponding table.</p>
     *
     * @return a list of all entities
     */
 public List<T> findAll(){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String query=createSelectAllQuery() ;///createSelectQuery("id");
        try {
            connection= ConnectionFactory.getConnection();
            preparedStatement=connection.prepareStatement(query);
            resultSet= preparedStatement.executeQuery();
            return createObjects(resultSet);
        }
        catch(SQLException e){
            LOGGER.log(Level.WARNING,type.getName()+"DAO:findAll");
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * <p>Creates entity instances from the given ResultSet.</p>
     *
     * @param resultSet the result set containing database rows
     * @return a list of populated entities
     */
private List<T> createObjects(ResultSet resultSet){
List<T> list=new ArrayList<T>();
try {
    while (resultSet.next())
    {
        T instance=type.newInstance();
        for(Field field:type.getDeclaredFields()){
            Object value=resultSet.getObject(field.getName());
            PropertyDescriptor propertyDescriptor=new PropertyDescriptor(field.getName(),type);
            Method method=propertyDescriptor.getWriteMethod();
            method.invoke(instance,value);
        }
        list.add(instance);
    }
}
catch (InstantiationException | SQLException e)
{
    LOGGER.log(Level.SEVERE,type.getName()+"DAO:createObjects");
} catch (IntrospectionException e) {
    throw new RuntimeException(e);
} catch (InvocationTargetException e) {
    throw new RuntimeException(e);
} catch (IllegalAccessException e) {
    throw new RuntimeException(e);
}
    return list;
}
    /**
     * <p>Inserts a new entity into the database.</p>
     *
     * @param t the entity to insert
     * @return the inserted entity, or null if insertion fails
     */
 public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query=insertQuery();
        try {
            connection=ConnectionFactory.getConnection();
            statement=connection.prepareStatement(query);
            Field[] fields=type.getDeclaredFields();
            int i=0;
            for(Field f:fields){
                PropertyDescriptor propertyDescriptor=new PropertyDescriptor(f.getName(),type);
                Method method=propertyDescriptor.getReadMethod();
                Object value=method.invoke(t);
                statement.setObject(i+1,value);
                i++;
            }
            statement.executeUpdate();
            return t;
        } catch (SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING,type.getName()+"DAO:findAll"+ e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * <p>Updates an existing entity in the database by its ID.</p>
     *
     * @param t the entity with updated values
     * @return the updated entity, or null if update fails
     */
 public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field[] fields = type.getDeclaredFields();
            Object idValue = null;
            int paramIndex = 1;

            for (Field f : fields)
            {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(f.getName(), type);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(t);
                if (!f.getName().equals("id"))
                {
                    statement.setObject(paramIndex++, value);
                } else {
                    idValue = value;
                }
            }
            statement.setObject(paramIndex, idValue);
            statement.executeUpdate();
            return t;
        } catch (SQLException | IntrospectionException e)
        {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update" + e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        } finally
        {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * <p>Deletes an entity from the database by ID.</p>
     *
     * @param id the ID of the entity to delete
     * @return true if the deletion was successful, false otherwise
     */
 public boolean delete(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
             preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete", e);
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return false;
    }
    /**
     * <p>Populates a JTable with data from a list of entities.</p>
     *
     * @param table the JTable to populate
     * @param list the list of entities to display
     */
 public void populateTableFromList(JTable table, List<T> list)
    {
        if (list == null || list.isEmpty())
        {
            table.setModel(new DefaultTableModel());
            return;
        }
        Field[] fields = type.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++)
        {
            columnNames[i] = fields[i].getName();
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        try {
            for (T obj : list)
            {
                Object[] rowData = new Object[fields.length];
                for (int i = 0; i < fields.length; i++)
                {
                    fields[i].setAccessible(true);
                    rowData[i] = fields[i].get(obj);
                }
                model.addRow(rowData);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        table.setModel(model);
    }


}
