package cz.muni.fi.pv168.seminar3.team3;

import cz.muni.fi.pv168.seminar3.team3.data.WorkTypeDao;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for WorkType
 *
 * @author Tomas Kadavy
 * @since Milestone-3
 */
public class WorkTypeDaoTest {

    private static EmbeddedDataSource dataSource;
    private WorkTypeDao workTypeDao;

    @BeforeAll
    static void initTestDataSource() throws SQLException {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:workType-evidence-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createWorkTypeDao() throws SQLException {
        workTypeDao = new WorkTypeDao(dataSource);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.WORK_TYPE");
        }
    }

    @AfterEach
    void workTypeTableCleaning() {
        workTypeDao.dropTable();
    }

    @Test
    void createWorkType() {
        var workType = new WorkType("programming", 15);
        workTypeDao.create(workType);

        assertThat(workType.getId()).isNotNull();
        assertThat(workTypeDao.read()).usingFieldByFieldElementComparator().containsExactly(workType);
    }

    @Test
    void findAllEmpty() {
        assertThat(workTypeDao.read()).isEmpty();
    }

    @Test
    void delete() {
        var workType1 = new WorkType("programming", 15);
        var workType2 = new WorkType("debugging", 10);

        workTypeDao.create(workType1);
        workTypeDao.create(workType2);
        workTypeDao.delete(workType1);
        assertThat(workTypeDao.read()).usingFieldByFieldElementComparator().containsExactly(workType2);
    }

    @Test
    void update() {
        var workType1 = new WorkType("programming", 15);
        workTypeDao.create(workType1);
        workType1.setType("newType");
        workTypeDao.update(workType1);
        assertThat(workTypeDao.read()).usingFieldByFieldElementComparator().containsExactly(workType1);
    }

}
