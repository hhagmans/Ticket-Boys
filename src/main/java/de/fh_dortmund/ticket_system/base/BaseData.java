package de.fh_dortmund.ticket_system.base;

import java.util.List;

/**
 * Generic class for ManagedBeans with DAO abstraction.
 * 
 * @author Alex Hofmann
 *
 * @param <E> Class Type of the entity of the DAO
 * @param <Dao> The DAO implementation 
 */
public class BaseData<E, Dao extends BaseDao<E>>
{
	private Dao	dao;

	public E findByID(String konzernID)
	{
		E t = dao.findById(konzernID);
		return t;
	}

	public void update(E t)
	{
		dao.update(t);
	}

	public void update(List<E> l)
	{
		dao.update(l);
	}

	public void delete(E t)
	{
		dao.delete(t);
	}

	public void add(E t)
	{
		dao.add(t);
	}

	public void add(List<E> l)
	{
		dao.add(l);
	}

	public List<E> findAll()
	{
		return dao.findAll();
	}

	public Dao getDao()
	{
		return dao;
	}

	public void setDao(Dao dao)
	{
		this.dao = dao;
	}
}
