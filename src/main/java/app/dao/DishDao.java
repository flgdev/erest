package app.dao;

import org.springframework.stereotype.Repository;

import app.entity.Dish;

import java.util.List;

@Repository
public class DishDao extends GenericDaoImp<Dish>{
	public List<Dish> findAll() {
		return em.createQuery("SELECT d from Dish d WHERE d.deleted=false").getResultList();
	}
	
	@Override
	public void delete(final Object id) {
		Dish d = em.getReference(Dish.class, id);
		d.setDeleted(true);
		update(d);
	}
}
