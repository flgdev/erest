package app.entity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Main {
	private static final String UNIT_NAME = "Restaurant";
	private static EntityManagerFactory factory;
	private static EntityManager em;

	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(UNIT_NAME);
		em = factory.createEntityManager();

		Dish n = new Dish("TEST: NEW DISH", 6.78, Dish.Category.DESSERTS, true);
		Dish d = new Dish("TEST: EDITED DISH", 4.56, Dish.Category.DESSERTS,
				true);
		n.setKitchen(true);
		d.setId(25);

		Worker w = new Worker();
		w.setName("TEST: NEW WORKER");
		w.setPassword("1");
		w.setRole(Worker.Role.ANALYTIC);

		Order o = new Order();
		o.setDt(new Timestamp(new Date().getTime()));
		o.setTablenum(1);
		ArrayList<Orderitem> a = new ArrayList<Orderitem>();
		a.add(new Orderitem(1, o));
		a.add(new Orderitem(6, o));
		a.add(new Orderitem(11, o));

		// reset();
		// createDish(n);
		// editDish(d);
		// removeDish(1);
		// createWorker(w);
		// removeWorker(1);
		// markAsDone(35);
		// makeOrder(o,a);
		// getReport(Timestamp.valueOf("2014-05-12 00:00:00"),
		// Timestamp.valueOf("2014-05-15 00:00:00"));
		// getDishList();
		// getPendingList();
		// getWorkerList();
	}

	public static void reset() {
		String query = "";
		try {
			Query q;
			List<String> l = Files.readAllLines(Paths.get("db.sql"),
					Charset.forName("UTF-8"));
			for (String s : l) {
				query += s + System.getProperty("line.separator");
			}
			String[] qq = query.split(";");
			em.getTransaction().begin();
			for (String s : qq) {
				if (s.length() < 3) {
					continue;
				}
				q = em.createNativeQuery(s);
				q.executeUpdate();
			}
			em.getTransaction().commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Dish> getDishList() {
		TypedQuery<Dish> query = em
				.createQuery(
						"SELECT d FROM Dish d WHERE d.deleted=false ORDER BY d.category",
						Dish.class);
		List<Dish> listM = null;
		try {
			listM = query.getResultList();
		} finally {
		}
		if (listM != null) {
			for (Object p : listM) {
				System.out.println(p.toString());
			}
		}
		return listM;
	}

	public static List<Worker> getWorkerList() {
		TypedQuery<Worker> query = em.createQuery("SELECT w FROM Worker w",
				Worker.class);
		List<Worker> listM = null;
		try {
			listM = query.getResultList();
		} finally {
		}
		if (listM != null) {
			for (Object p : listM) {
				System.out.println(p.toString());
			}
		}
		return listM;
	}

	public static List<Orderitem> getPendingList() {
		TypedQuery<Orderitem> query = em.createQuery(
				"SELECT o FROM Orderitem o WHERE o.done=false AND"
						+ " o.dish.kitchen=true ORDER BY o.order.dt",
				Orderitem.class);
		List<Orderitem> listM = null;
		try {
			listM = query.getResultList();
		} finally {
		}
		if (listM != null) {
			for (Object p : listM) {
				System.out.println(p.toString());
			}
		}
		return listM;
	}

	public static boolean removeDish(int id) {
		Dish d = null;
		try {
			d = em.find(Dish.class, id);
		} finally {
		}
		if (d != null) {
			em.getTransaction().begin();
			d.setDeleted(true);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}

	public static boolean removeWorker(int id) {
		Worker w = null;
		try {
			w = em.find(Worker.class, id);
		} finally {
		}
		if (w != null) {
			em.getTransaction().begin();
			em.remove(w);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}

	public static void createDish(Dish d) {
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}

	public static void editDish(Dish d) {
		em.getTransaction().begin();
		em.merge(d);
		em.getTransaction().commit();
	}

	public static void createWorker(Worker w) {
		em.getTransaction().begin();
		em.persist(w);
		em.getTransaction().commit();
	}

	public static boolean markAsDone(int id) {
		Orderitem o = null;
		try {
			o = em.find(Orderitem.class, id);
		} finally {
		}
		if (o != null) {
			em.getTransaction().begin();
			o.setDone(true);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}

	public static List<Orderitem> getReport(Timestamp from, Timestamp to) {
		int k = ((int) (to.getTime() - from.getTime()) / 86400000) + 1;
		double[][][] res = new double[k][6][2];
		TypedQuery<Orderitem> query = em.createQuery(
				"SELECT o FROM Orderitem o WHERE o.order.dt>=:from "
						+ "and o.order.dt<:to ORDER BY o.order.dt",
				Orderitem.class);
		List<Orderitem> listM = null;
		try {
			query.setParameter("from", from);
			query.setParameter("to", to);
			listM = query.getResultList();
		} finally {
		}
		if (listM != null) {
			for (Orderitem p : listM) {
				int day = (int) Math.floor((double) (p.getOrder().getDt()
						.getTime() - from.getTime()) / 86400000);
				int cat = p.getDish().getCategory().ordinal();
				res[day][cat][0] += p.getDish().getPrice();
				res[day][cat][1] += 1;
			}
		}

		for (int i = 0; i < k - 1; i++) {
			for (int j = 0; j < 5; j++) {
				res[k - 1][j][0] += res[i][j][0];
				res[i][5][0] += res[i][j][0];
				res[k - 1][j][1] += res[i][j][1];
				res[i][5][1] += res[i][j][1];
			}
		}

		for (int j = 0; j < 5; j++) {
			res[k - 1][5][0] += res[k - 1][j][0];
			res[k - 1][5][1] += res[k - 1][j][1];
		}

		for (double[][] a : res) {
			for (double[] b : a) {
				System.out.print("   " + ((double) Math.round(b[0] * 100))
						/ 100 + "[" + (int) b[1] + "]");
			}
			System.out.println();
		}

		return listM;
	}

	public static void makeOrder(Order o, ArrayList<Orderitem> a) {
		em.getTransaction().begin();
		em.persist(o);
		for (Orderitem e : a) {
			em.persist(e);
		}
		em.getTransaction().commit();
	}

}