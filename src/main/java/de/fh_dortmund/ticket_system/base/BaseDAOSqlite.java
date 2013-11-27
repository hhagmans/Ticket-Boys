package de.fh_dortmund.ticket_system.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class BaseDAOSqlite<T> implements BaseDao<T>
{
	protected static EntityManagerFactory	emf	= Persistence.createEntityManagerFactory("sqlite");
	private static EntityManager			em;

	protected BaseDAOSqlite()
	{

	}

	public static EntityManager getEm()
	{
		if (em == null)
			em = emf.createEntityManager();

		return em;
	}

	@Override
	public void add(T t)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().persist(t);
		tx.commit();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findById(String id)
	{
		Class<T> clazz = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

		T emp = getEm().find(clazz, id);
		return emp;
	}

	@Override
	public void update(T t)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().merge(t);
		tx.commit();
	}

	@Override
	public void delete(T t)
	{
		EntityTransaction tx = getEm().getTransaction();
		tx.begin();
		getEm().remove(t);
		tx.commit();
	}
}
