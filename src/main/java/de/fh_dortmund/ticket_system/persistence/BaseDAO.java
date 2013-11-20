package de.fh_dortmund.ticket_system.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDAO
{
	protected static EntityManagerFactory	emf	= Persistence.createEntityManagerFactory("sqlite");
	private static EntityManager			em;

	protected BaseDAO()
	{

	}

	public static EntityManager getEm()
	{
		if (em == null)
			em = emf.createEntityManager();

		return em;
	}
}
