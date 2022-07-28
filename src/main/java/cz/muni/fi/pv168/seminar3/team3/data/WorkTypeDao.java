package cz.muni.fi.pv168.seminar3.team3.data;

import cz.muni.fi.pv168.seminar3.team3.model.InvalidFormatException;
import cz.muni.fi.pv168.seminar3.team3.model.WorkType;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Data access object class for WorkType
 *
 * @author Tomáš Kadavý
 * @since milestone-2
 */
public class WorkTypeDao implements DataAccessObject<WorkType>{

    private final DataSource dataSource;

    private static final I18N I18N = new I18N(WorkTypeDao.class);

    public WorkTypeDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    private void initTable() {
        if(!tableExits("APP", "WORK_TYPE")) {
            createTable();
        }
    }

    private boolean tableExits(String schemaName, String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, schemaName, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to detect if the table " + schemaName + "." + tableName + " exist", ex);
        }
    }

    public WorkType findById(Long id) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, TYPE, DEFAULT_RATE FROM WORK_TYPE WHERE ID = ?")) {
            st.setLong(1, id);
            try (var rs = st.executeQuery()) {
                if (rs.next()) {
                    var workType = new WorkType(
                            rs.getString("TYPE"),
                            rs.getDouble("DEFAULT_RATE"));
                    workType.setId(rs.getLong("ID"));
                    if (rs.next()) {
                        throw new DataAccessException("Multiple WORKTYPES with id " + id + " found");
                    }
                    return workType;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load WORKTYPE with id " + id, ex);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE APP.WORK_TYPE (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "TYPE VARCHAR(100) NOT NULL UNIQUE," +
                    "DEFAULT_RATE DOUBLE NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create WORK_TYPE table", ex);
        }
    }

    @Override
    public void create(WorkType workType) throws DataAccessException {
        if (workType.getId() != null) {
            throw new IllegalArgumentException("Worktype already has ID: " + workType);
        }

        if (workType.getType().equals("")) {
            throw new InvalidFormatException(I18N.getString("emptyName"));
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO WORK_TYPE (TYPE, DEFAULT_RATE)" +
                             " VALUES (?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setString(1, workType.getType());
            st.setDouble(2, workType.getDefaultRate());
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.getMetaData().getColumnCount() != 1) {
                    throw new DataAccessException("Failed to fetch generated key: compound key returned for workType: " + workType);
                }
                if (rs.next()) {
                    workType.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Failed to fetch generated key: no key returned for workType: " + workType);
                }
                if (rs.next()) {
                    throw new DataAccessException("Failed to fetch generated key: multiple keys returned for workType: " + workType);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store workType " + workType, ex);
        }
    }

    @Override
    public Collection<WorkType> read() throws DataAccessException {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, TYPE, DEFAULT_RATE FROM WORK_TYPE")) {
            List<WorkType> workTypes = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    var workType = new WorkType(rs.getString("TYPE"), rs.getDouble("DEFAULT_RATE"));
                    workType.setId(rs.getLong("ID"));
                    workTypes.add(workType);
                }
            }
            return workTypes;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all worktypes", ex);
        }
    }

    @Override
    public void update(WorkType workType) throws DataAccessException {
        if (workType.getId() == null) {
            throw new IllegalArgumentException("Worktype has null ID");
        }

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE WORK_TYPE SET TYPE = ?, DEFAULT_RATE = ? WHERE ID = ?")) {
            st.setString(1, workType.getType());
            st.setDouble(2, workType.getDefaultRate());
            st.setLong(3, workType.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException("Failed to update non-existing workType: " + workType);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to update workType " + workType, ex);
        }
    }

    @Override
    public void delete(WorkType workType) throws DataAccessException {
        if (workType.getId() == null) {
            throw new IllegalArgumentException("Worktype has null ID: " + workType);
        }

        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("DELETE FROM WORK_TYPE WHERE ID = ?")) {
            st.setLong(1, workType.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException("Failed to delete non-existing workType: " + workType);
            }
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new DataAccessException(I18N.getString("foreignKeyViolation"), ex);
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to delete workType " + workType, ex);
        }
    }

    /**
     * Drops the workType table
     */
    public void dropTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE APP.WORK_TYPE");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to drop WORK_TYPE table", ex);
        }
    }
}
