package app.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import app.entity.Dish;
import app.entity.Order;
import app.entity.Orderitem;
import app.service.OrderitemService;

@Named
@Scope("request")
public class OrderitemBean {
	private int id;
	private Order order;
	private Dish dish;
	private boolean done;
	private List<Orderitem> orderitems;
	private List<Orderitem> pendingitems;
	
    @Inject
    private OrderitemService orderitemService;
    
    @PostConstruct
    private void init() {
    	orderitems = orderitemService.findAll();
    	pendingitems = orderitemService.pendingList();
    }

    public void createOrderitem() {
        orderitemService.createOrderitem(order,dish,done);
        init();
    }
    
    public void deleteOrderitem(int id) {
        orderitemService.deleteOrderitem(id);
        init();
    } 

    public List<Orderitem> getOrderitems() {
        return orderitems;
    }
    
    public List<Orderitem> getPending() {
        return pendingitems;
    }    
    
    public void checkDone(int id) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marked as done","");
        FacesContext.getCurrentInstance().addMessage(null, msg);          	
    	orderitemService.checkDone(id);
    	init();
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
