package cz.muni.fi.pv168.seminar3.team3.data;

import java.util.Collection;

/**
 * Interface for CRUD operations
 *
 * @param <E> type of entity
 * @author Tomas Kadavy
 * @since Milestone-2
 */
public interface DataAccessObject<E> {
    /**
     * Creates a new entity
     * @param entity to be created
     * @throws DataAccessException when anything goes wrong with the underlying data source
     */
    void create(E entity) throws DataAccessException;

    /**
     * Read entities
     * @return collection of entities
     * @throws DataAccessException when anything goes wrong with the underlying data source
     */
    Collection<E> read() throws DataAccessException;

    /**
     * Updates an entity
     * @param entity entity to be deleted
     * @throws IllegalArgumentException when the entity has not been persisted yet
     * @throws DataAccessException when anything goes wrong with the underlying data source
     */
    void update(E entity) throws DataAccessException;

    /**
     * Deletes an entity
     * @param entity entity to be deleted
     * @throws IllegalArgumentException when the entity has not been persisted yet
     * @throws DataAccessException when anything goes wrong with the underlying data source
     */
    void delete(E entity) throws DataAccessException;
}
