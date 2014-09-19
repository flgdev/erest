package app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Orderitem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="orderId")
	private Order order;
	@ManyToOne
	@JoinColumn(name="dishId")	
	private Dish dish;
	private boolean done;

	public Orderitem() {

	}
	
	public Orderitem(int dishId, Order o) {
		dish = new Dish();
		dish.setId(dishId);
		order = o;
	}

	public String toString() {
		return "Order ID: " + order.getId() + "; Dish ID: " + dish.getId() + "; Done: "
				+ done;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

}
