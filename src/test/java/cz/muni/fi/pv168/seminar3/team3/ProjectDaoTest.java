package cz.muni.fi.pv168.seminar3.team3;

import cz.muni.fi.pv168.seminar3.team3.data.ClientDao;
import cz.muni.fi.pv168.seminar3.team3.data.ProjectDao;
import cz.muni.fi.pv168.seminar3.team3.data.WorkTypeDao;
import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test class for ProjectDao
 *
 * @author Tomas Kadavy
 * @since Milestone-3
 */
public class ProjectDaoTest {
    private static EmbeddedDataSource dataSource;
    private static WorkTypeDao workTypeDao;
    private static ClientDao clientDao;
    private ProjectDao projectDao;

    private static final WorkType PROGRAMMING = new WorkType("programming", 300);
    private static final Client PAVEL = new Client("pavel", "555555555", "pavel@gmail.com", "4613");

    @BeforeAll
    static void initTestDataSource() throws SQLException {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:project-evidence-test");
        dataSource.setCreateDatabase("create");
        workTypeDao = new WorkTypeDao(dataSource);
        clientDao = new ClientDao(dataSource);
        clientDao.create(PAVEL);
        workTypeDao.create(PROGRAMMING);
    }

    @BeforeEach
    void createProjectDao() throws SQLException {
        projectDao = new ProjectDao(dataSource, clientDao::findById, workTypeDao::findById);
        try (var connection = dataSource.getConnection(); var st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM APP.PROJECT");
        }
    }

    @AfterEach
    void projectTableCleaning() {
        projectDao.dropTable();
    }

    @Test
    void createProject() {
        var project = new Project("Project A", PAVEL, PROGRAMMING, LocalDate.of(2000,2,16), 50, 16, "desc");
        projectDao.create(project);

        assertThat(project.getId()).isNotNull();
        assertThat(projectDao.read()).usingFieldByFieldElementComparator().containsExactly(project);
    }

    @Test
    void findAllEmpty() {
        assertThat(projectDao.read()).isEmpty();
    }

    @Test
    void delete() {
        var project1 = new Project("Project A", PAVEL, PROGRAMMING, LocalDate.of(2000,2,16), 50, 16, "desc");
        var project2 = new Project("Project B", PAVEL, PROGRAMMING, LocalDate.of(2000,2,16), 60, 16, "descsd");
        projectDao.create(project1);
        projectDao.create(project2);
        projectDao.delete(project1);
        assertThat(projectDao.read()).usingFieldByFieldElementComparator().containsExactly(project2);
    }

    @Test
    void update() {
        var project1 = new Project("Project A", PAVEL, PROGRAMMING, LocalDate.of(2000,2,16), 50, 16, "desc");
        projectDao.create(project1);
        project1.setName("newName");
        projectDao.update(project1);
        assertThat(projectDao.read()).usingFieldByFieldElementComparator().containsExactly(project1);
    }
}
