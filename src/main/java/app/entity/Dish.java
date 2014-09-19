package app.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	private boolean kitchen;
	private boolean deleted;
	@Enumerated
	private Category category;

	public enum Category {
		SOUPS, SALADS, MEATS, BEVERAGE, DESSERTS
	};

	public Dish() {

	}

	public Dish(String name, double price, Category category, boolean kitchen) {
		this.name = name;
		this.price = price;
		this.kitchen = kitchen;
		this.category = category;
	}

	public String toString() {
		return "Category: " + category + "; Name: " + name + "; Price: "
				+ price + "; Kitchen: " + kitchen;
	}
	
	public int getTmp() {
		return 0;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
