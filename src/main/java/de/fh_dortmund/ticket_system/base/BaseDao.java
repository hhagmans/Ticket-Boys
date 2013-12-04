package de.fh_dortmund.ticket_system.base;

import java.util.List;

public interface BaseDao<T>
{
	public void add(T t);

	public T findById(String id);

	public List<T> findAll();

	public void update(T t);

	public void delete(T t);
}
