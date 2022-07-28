package cz.muni.fi.pv168.seminar3.team3;

import cz.muni.fi.pv168.seminar3.team3.data.ClientDao;
import cz.muni.fi.pv168.seminar3.team3.model.Client;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test class for ClientDao
 *
 * @author Tomas Kadavy
 * @since Milestone-3
 */
final class ClientDaoTest {

    private static EmbeddedDataSource dataSource;
    private ClientDao clientDao;

    @BeforeAll
    static void initTestDataSource() throws SQLException {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:client-evidence-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createClientDao() throws SQLException {
        clientDao = new ClientDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.CLIENT");
        }
    }

    @AfterEach
    void clientTableCleaning() {
        clientDao.dropTable();
    }

    @Test
    void createClient() {
        var client = new Client("tomas", "607562453","tomas@gmail.com", "464562");
        clientDao.create(client);

        assertThat(client.getId()).isNotNull();
        assertThat(clientDao.read()).usingFieldByFieldElementComparator().containsExactly(client);
    }

    @Test
    void findAllEmpty() {
        assertThat(clientDao.read()).isEmpty();
    }

    @Test
    void delete() {
        var client1 = new Client("tomas", "607562453","tomas@gmail.com", "464562");
        var client2 = new Client("pavel", "555555555","pavel@gmail.com", "46432176");
        clientDao.create(client1);
        clientDao.create(client2);
        clientDao.delete(client1);
        assertThat(clientDao.read()).usingFieldByFieldElementComparator().containsExactly(client2);
    }

    @Test
    void update() {
        var client1 = new Client("tomas", "607562453","tomas@gmail.com", "464562");
        clientDao.create(client1);
        client1.setName("newName");
        clientDao.update(client1);
        assertThat(clientDao.read()).usingFieldByFieldElementComparator().containsExactly(client1);
    }

}
