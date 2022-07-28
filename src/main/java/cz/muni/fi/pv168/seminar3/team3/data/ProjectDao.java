package cz.muni.fi.pv168.seminar3.team3.data;

import cz.muni.fi.pv168.seminar3.team3.model.Client;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Data access object class for Project
 *
 * @author Tomáš Kadavý
 * @since milestone-2
 */
public class ProjectDao implements DataAccessObject<Project>{

    private final DataSource dataSource;
    private final Function<Long, Client> clientResolver;
    private final Function<Long, WorkType> workTypeResolver;

    private static final I18N I18N = new I18N(ProjectDao.class);

    public ProjectDao(DataSource dataSource, Function<Long, Client> clientResolver, Function<Long, WorkType> workTypeResolver) {
        this.dataSource = dataSource;
        this.clientResolver = clientResolver;
        this.workTypeResolver = workTypeResolver;
        initTable();
    }

    private void initTable() {
        if(!tableExits("APP", "PROJECT")) {
            createTable();
        }
    }


    @Override
    public void create(Project project) throws DataAccessException {
        if (project.getId() != null) {
            throw new IllegalArgumentException(I18N.getString("projectHasIdError") + project);
        }

        project.validate();

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO PROJECT (NAME, CLIENT_ID, WORK_TYPE_ID, START_TIME, HOURS_WORKED, HOUR_RATE, DESCRIPTION)" +
                             " VALUES (?, ?, ?, ?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            setProjectSQLStatement(project, st);
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.getMetaData().getColumnCount() != 1) {
                    throw new DataAccessException(I18N.getString("compoundKeyError") + project);
                }
                if (rs.next()) {
                    project.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException(I18N.getString("noKeyReturnedError") + project);
                }
                if (rs.next()) {
                    throw new DataAccessException(I18N.getString("multipleKeysError") + project);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("createSQLError") + project, ex);
        }

    }

    private void setProjectSQLStatement(Project project, PreparedStatement st) throws SQLException {
        st.setString(1, project.getName());
        st.setLong(2, project.getClient().getId());
        st.setLong(3, project.getWorkType().getId());
        st.setDate(4, Date.valueOf(project.getStartTime()));
        st.setInt(5, project.getHoursWorked());
        st.setDouble(6, project.getHourRate());
        st.setString(7, project.getDescription());
    }

    @Override
    public List<Project> read() throws DataAccessException {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, NAME, CLIENT_ID, WORK_TYPE_ID," +
                     " START_TIME, HOURS_WORKED, HOUR_RATE, DESCRIPTION FROM PROJECT")) {
            List<Project> projects = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    long clientId = rs.getLong("CLIENT_ID");
                    Client client = rs.wasNull() ? null : clientResolver.apply(clientId);
                    long workTypeId = rs.getLong("WORK_TYPE_ID");
                    WorkType workType = rs.wasNull() ? null : workTypeResolver.apply(workTypeId);

                    var project = new Project(
                            rs.getString("NAME"),
                            client,
                            workType,
                            rs.getDate("START_TIME").toLocalDate(),
                            rs.getInt("HOURS_WORKED"),
                            rs.getDouble("HOUR_RATE"),
                            rs.getString("DESCRIPTION"));
                    project.setId(rs.getLong("ID"));
                    projects.add(project);
                }
            }
            return projects;
        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("readSQLError"), ex);
        }

    }

    @Override
    public void update(Project project) throws DataAccessException {
        if (project.getId() == null) {
            throw new IllegalArgumentException(I18N.getString("nullProjectId"));
        }
        project.validate();

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE PROJECT SET NAME = ?, CLIENT_ID = ?, WORK_TYPE_ID = ?," +
                             " START_TIME = ?, HOURS_WORKED = ?, HOUR_RATE = ?, DESCRIPTION = ? WHERE ID = ?")) {
            setProjectSQLStatement(project, st);
            st.setLong(8, project.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException(I18N.getString("updateNonExistingError") + project);

            }
        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("updateSQLError") + project, ex);
        }

    }

    @Override
    public void delete(Project project) throws DataAccessException {
        if (project.getId() == null) {
            throw new IllegalArgumentException(I18N.getString("nullProjectId"));
        }

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("DELETE FROM PROJECT WHERE ID = ?")) {
            st.setLong(1, project.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException(I18N.getString("deleteNonExistingError") + project);
            }
        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("deleteSQLError") + project, ex);
        }

    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE APP.PROJECT (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 0)," +
                    "NAME VARCHAR(100) NOT NULL," +
                    "CLIENT_ID BIGINT REFERENCES CLIENT(ID)," +
                    "WORK_TYPE_ID BIGINT REFERENCES WORK_TYPE(ID)," +
                    "START_TIME DATE NOT NULL," +
                    "HOURS_WORKED BIGINT," +
                    "HOUR_RATE DOUBLE," +
                    "DESCRIPTION VARCHAR(100) NOT NULL" +
                    ")");

        } catch (SQLException ex) {
            throw new DataAccessException(I18N.getString("createTableError"), ex);
        }
    }

    private boolean tableExits(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
             return rs.next();
        } catch (SQLException ex) {
            MessageFormat formatter = new MessageFormat(I18N.getString("tableExistsError"), Locale.getDefault());
            throw new DataAccessException(formatter.format(new Object[] {schemaName, tableName}), ex);
        }
    }

    /**
     * Drops the Project table
     */
    public void dropTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE APP.PROJECT");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to drop PROJECT table", ex);
        }
    }

}
