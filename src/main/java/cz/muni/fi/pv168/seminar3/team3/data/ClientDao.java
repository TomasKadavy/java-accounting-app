package cz.muni.fi.pv168.seminar3.team3.data;

import cz.muni.fi.pv168.seminar3.team3.model.Client;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Data access object class for Client
 *
 * @author Tomáš Kadavý
 * @since milestone-2
 */
public class ClientDao implements DataAccessObject<Client>{

    private final DataSource dataSource;
    private static final I18N I18N = new I18N(ClientDao.class);

    public ClientDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    private void initTable() {
        if(!tableExits("APP", "CLIENT")) {
            createTable();
        }
    }

    private boolean tableExits(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            MessageFormat formatter = new MessageFormat(I18N.getString("tableExistsError"), Locale.getDefault());
            String message = formatter.format(new Object[] {schemaName, tableName});
            throw new DataAccessException(message, ex);
        }
    }

    public Client findById(Long id) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, NAME, PHONE_NUMBER, EMAIL, CRN FROM CLIENT WHERE ID = ?")) {
            st.setLong(1, id);
            try (var rs = st.executeQuery()) {
                if (rs.next()) {
                    var client = new Client(
                            rs.getString("NAME"),
                            rs.getString("PHONE_NUMBER"),
                            rs.getString("EMAIL"),
                            rs.getString("CRN"));
                    client.setId(rs.getLong("ID"));
                    if (rs.next()) {
                        MessageFormat formatter = new MessageFormat(I18N.getString("multipleIdsError"), Locale.getDefault());
                        throw new DataAccessException(formatter.format(id));
                    }
                    return client;
                }
                return null;
            }
        } catch (SQLException ex) {
            MessageFormat formatter = new MessageFormat(I18N.getString("notFoundError"), Locale.getDefault());
            throw new DataAccessException(formatter.format(id));
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE APP.CLIENT (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0)," +
                    "NAME VARCHAR(100) NOT NULL," +
                    "PHONE_NUMBER VARCHAR(100) NOT NULL UNIQUE," +
                    "EMAIL VARCHAR(100) NOT NULL UNIQUE," +
                    "CRN VARCHAR(100) NOT NULL UNIQUE" +
                    ")");

        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("createTableError"), ex);
        }
    }

    @Override
    public void create(Client client) throws DataAccessException {
        if (client.getId() != null) {
            MessageFormat formatter = new MessageFormat(I18N.getString("notFoundError"), Locale.getDefault());
            throw new IllegalArgumentException(formatter.format(client.getId()));
        }

        client.validate();

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO CLIENT (NAME, PHONE_NUMBER, EMAIL, CRN)" +
                             " VALUES (?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setString(1, client.getName());
            st.setString(2, client.getPhoneNumber());
            st.setString(3, client.getEmail());
            st.setString(4, client.getCrn());
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.getMetaData().getColumnCount() != 1) {
                    MessageFormat formatter = new MessageFormat(I18N.getString("compoundKeyError"), Locale.getDefault());
                    throw new DataAccessException(formatter.format(client));
                }
                if (rs.next()) {
                    client.setId(rs.getLong(1));
                } else {
                    MessageFormat formatter = new MessageFormat(I18N.getString("compoundKeyError"), Locale.getDefault());
                    throw new DataAccessException(formatter.format("noKeyReturnedError"));
                }
                if (rs.next()) {
                    MessageFormat formatter = new MessageFormat(I18N.getString("multipleKeysError"), Locale.getDefault());
                    throw new DataAccessException(formatter.format(client));
                }
            }
        } catch (SQLException ex) {
            MessageFormat formatter = new MessageFormat(I18N.getString("createSQLError"), Locale.getDefault());
            throw new DataAccessException(formatter.format(client), ex);
        }

    }

    @Override
    public Collection<Client> read() throws DataAccessException {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, NAME, PHONE_NUMBER, EMAIL, CRN FROM CLIENT")) {
            List<Client> clients = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    var client = new Client(
                            rs.getString("NAME"),
                            rs.getString("PHONE_NUMBER"),
                            rs.getString("EMAIL"),
                            rs.getString("CRN"));
                    client.setId(rs.getLong("ID"));
                    clients.add(client);
                }
            }
            return clients;
        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("readAllSQLError"), ex);
        }
    }

    @Override
    public void update(Client client) throws DataAccessException {
        if (client.getId() == null) {
            throw new IllegalArgumentException(I18N.getString("nullClientId"));
        }

        client.validate();

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE CLIENT SET NAME = ?, PHONE_NUMBER = ?, EMAIL = ?, CRN = ? WHERE ID = ?")) {
            st.setString(1, client.getName());
            st.setString(2, client.getPhoneNumber());
            st.setString(3, client.getEmail());
            st.setString(4, client.getCrn());
            st.setLong(5, client.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException("" + client);
            }
        } catch (SQLException ex) {
            MessageFormat formatter = new MessageFormat(I18N.getString("updateNonExistingError"), Locale.getDefault());
            throw new DataAccessException(formatter.format(client), ex);
        }

    }

    @Override
    public void delete(Client client) throws DataAccessException {
        if (client.getId() == null) {
            throw new IllegalArgumentException(I18N.getString("nullClientId"));
        }

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("DELETE FROM CLIENT WHERE ID = ?")) {
            st.setLong(1, client.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted == 0) {
                MessageFormat formatter = new MessageFormat(I18N.getString("deleteNonExistingError"), Locale.getDefault());
                throw new DataAccessException(formatter.format(client));
            }
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new DataAccessException(I18N.getString("foreignKeyViolation"), ex);
        }

        catch (SQLException ex) {

            MessageFormat formatter = new MessageFormat(I18N.getString("deleteSQLError"), Locale.getDefault());
            throw new DataAccessException(formatter.format(client), ex);
        }
    }

    /**
     * Drops the client table
     */
    public void dropTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE APP.CLIENT");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to drop CLIENT table", ex);
        }
    }
}
