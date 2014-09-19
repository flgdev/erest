package app.dao;

import org.springframework.stereotype.Repository;

import app.entity.Order;
import java.util.List;

@Repository
public class OrderDao extends GenericDaoImp<Order>{
	public List<Order> findAll() {
		return em.createQuery("from Orders").getResultList();
	}
}
