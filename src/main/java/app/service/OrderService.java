package app.service;

import app.dao.OrderDao;
import app.dao.OrderitemDao;
import app.entity.Dish;
import app.entity.Order;
import app.entity.Orderitem;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;


@Service
public class OrderService{
    @Inject
    private OrderDao orderDao;
    @Inject
    private OrderitemDao orderitemDao;

    @Transactional
    public void createOrder(int tablenum, List<Dish> items) {
    	Order order = new Order();
		order.setTablenum(tablenum);
		order.setDt(new Date());
        orderDao.create(order);
        for(Dish d:items){
        	Orderitem o = new Orderitem();
        	o.setOrder(order);
        	o.setDish(d);
        	o.setDone(false);
        	orderitemDao.create(o);
        }
    }
    
    @Transactional
    public void deleteOrder(int id) {
        orderDao.delete(id);
    }    

    public List<Order> findAll() {
        return orderDao.findAll();
    }
}