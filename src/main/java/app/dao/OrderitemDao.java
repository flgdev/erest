package app.dao;

import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;

import app.entity.MyReport;
import app.entity.Orderitem;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

@Repository
public class OrderitemDao extends GenericDaoImp<Orderitem> {
	public List<Orderitem> findAll() {
		return em.createQuery("from Orderitem").getResultList();
	}

	public List<MyReport> getByDay(Date from, Date to) {
		Query q = em
				.createQuery("SELECT new app.entity.MyReport(year( o.order.dt ),month( o.order.dt ),day( o.order.dt ) , COUNT( * ) , SUM( o.dish.price )) "
						+ "FROM Orderitem o WHERE o.order.dt>:fr AND o.order.dt<:to "
						+ "GROUP BY year( o.order.dt ) , month( o.order.dt ) , day( o.order.dt )");
		q.setParameter("fr", from);
		q.setParameter("to", to);
		return q.getResultList();
	}

	public List<MyReport> getByDish(Date from, Date to) {
		Query q = em
				.createQuery("SELECT new app.entity.MyReport(o.dish.name,COUNT( * ),SUM( o.dish.price )) "
						+ "FROM  Orderitem o WHERE o.order.dt>:fr AND o.order.dt<:to "
						+ "GROUP BY o.dish.name ORDER BY COUNT( * ) DESC");
		q.setParameter("fr", from);
		q.setParameter("to", to);		
		return q.getResultList();
	}

	public MyReport getTotal(Date from, Date to) {
		Query q = em
				.createQuery("SELECT new app.entity.MyReport(1,COUNT( * ),SUM( o.dish.price )) "
						+ "FROM  Orderitem o WHERE o.order.dt>:fr AND o.order.dt<:to");
		q.setParameter("fr", from);
		q.setParameter("to", to);
		return (MyReport) q.getSingleResult();
	}

	public List<Orderitem> pendingList() {
		return em
				.createQuery(
						"SELECT o from Orderitem o WHERE o.done=false AND o.dish.kitchen=true ORDER BY o.order.dt")
				.getResultList();
	}

	public void checkDone(final Object id) {
		Orderitem o = em.getReference(Orderitem.class, id);
		o.setDone(true);
		update(o);
	}
}