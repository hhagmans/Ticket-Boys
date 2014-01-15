package de.fh_dortmund.ticket_system.base;

import java.util.List;

/**
 * Interface of a BaseDao for all entities
 * 
 */
public interface BaseDao<T> {

	/**
	 * Add a single entity
	 * 
	 * @param t
	 *            entity
	 */
	public void add(T t);

	/**
	 * Adds a list of entities
	 * 
	 * @param List
	 *            <T> l list of entities
	 */
	public void add(List<T> l);

	/**
	 * Find Entity by ID
	 * 
	 * @param id
	 *            id of entity
	 */
	public T findById(String id);

	/**
	 * Find all entities
	 * 
	 * @return List<T< list of all entities
	 */
	public List<T> findAll();

	/**
	 * Update entity
	 * 
	 * @param t
	 *            entity
	 */
	public void update(T t);

	/**
	 * Update a list of entities
	 * 
	 * @param List
	 *            <T> l list of entities
	 */
	public void update(List<T> l);

	/**
	 * Delete a single entity
	 * 
	 * @param t
	 *            entity
	 */
	public void delete(T t);
}
