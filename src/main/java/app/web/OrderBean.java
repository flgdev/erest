package app.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import app.entity.Dish;
import app.service.OrderService;

@Named
@Scope("session")
public class OrderBean implements Serializable{
	private static final long serialVersionUID = 2L;	
	private int id;
	private int tablenum;
	private Date dt;
	private List<Dish> items;
	private double total;

    @Inject
    private OrderService orderService;	
    
    public String createOrder() {
        orderService.createOrder(tablenum, items);
        return "wait.xhtml";
    }
    
    public void addToOrder(Dish d) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, d.getName()+" added","");
        FacesContext.getCurrentInstance().addMessage(null, msg);     	
        items.add(d);
        total+=d.getPrice();
        total=Math.round(total*100)/100.0;        
    }
    
    public void removeFromOrder(Dish d) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, d.getName()+" removed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);     	
        items.remove(d);
        total-=d.getPrice();
        total=Math.round(total*100)/100.0;        
    }    
    
    @PostConstruct
    private void init(){
    	items = new ArrayList<Dish>();
    	total = 0;
    }
	
	public int itemsSize() {
		return items.size();
	}    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTablenum() {
		return tablenum;
	}

	public void setTablenum(int tablenum) {
		this.tablenum = tablenum;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public List<Dish> getItems() {
		return items;
	}

	public void setItems(List<Dish> items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
