package de.fh_dortmund.ticket_system.base;

public interface BaseDao<T> 
{
	public void update(T t);

	public void delete(T t);

	public void add(T t);

	public T findById(String id);
}
