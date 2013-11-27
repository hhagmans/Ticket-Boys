package de.fh_dortmund.ticket_system.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class BaseDaoSqlite<T> implements BaseDao<T>
{
	protected static EntityManagerFactory	emf	= Persistence.createEntityManagerFactory("sqlite");
	private static EntityManager			em;

	protected BaseDaoSqlite()
	{

	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityBeanTyp()
	{
		return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
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
	public T findById(String id)
	{
		T emp = getEm().find(getEntityBeanTyp(), id);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll()
	{
		return getEm().createQuery("from " + getEntityBeanTyp().getName()).getResultList();
	}
}
