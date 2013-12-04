package de.fh_dortmund.ticket_system.base;

import java.util.List;

public class BaseData<Entity, Dao extends BaseDao<Entity>>
{
	private Dao	dao;

	public Entity findByID(String konzernID)
	{
		Entity t = dao.findById(konzernID);
		return t;
	}

	public void update(Entity t)
	{
		dao.update(t);
	}

	public void delete(Entity t)
	{
		dao.delete(t);
	}

	public void add(Entity t)
	{
		dao.add(t);
	}

	public List<Entity> findAll()
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
