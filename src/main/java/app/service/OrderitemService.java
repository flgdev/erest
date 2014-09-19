package app.service;

import app.dao.OrderitemDao;
import app.entity.Dish;
import app.entity.MyReport;
import app.entity.Order;
import app.entity.Orderitem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;


@Service
public class OrderitemService {
    @Inject
    private OrderitemDao orderitemDao;

    @Transactional
    public void createOrderitem(Order order, Dish dish, boolean done) {
    	Orderitem orderitem = new Orderitem();
		orderitem.setOrder(order);
		orderitem.setDish(dish);
		orderitem.setDone(done);
        orderitemDao.create(orderitem);
    }
    
    @Transactional
    public void deleteOrderitem(int id) {
        orderitemDao.delete(id);
    }
    
    @Transactional
    public void checkDone(int id) {
    	orderitemDao.checkDone(id);
    }

    public List<Orderitem> findAll() {
        return orderitemDao.findAll();
    }
    
    public List<Orderitem> pendingList() {
        return orderitemDao.pendingList();
    }    
    
	public List<MyReport> getByDish(Date from, Date to) {
		return orderitemDao.getByDish(from,to);
	}	    
	
	public List<MyReport> getByDay(Date from, Date to) {
		return orderitemDao.getByDay(from,to);
	}	   
	
	public MyReport getTotal(Date from, Date to) {
		return orderitemDao.getTotal(from,to);
	}		
}
