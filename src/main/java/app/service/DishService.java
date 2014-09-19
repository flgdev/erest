package app.service;

import app.dao.DishDao;
import app.entity.Dish;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


@Service
public class DishService {
    @Inject
    private DishDao dishDao;

    @Transactional
    public void createDish(String name, double price, Dish.Category category, boolean kitchen) {
    	Dish dish = new Dish();
		dish.setName(name);
		dish.setPrice(price);
		dish.setCategory(category);
		dish.setKitchen(kitchen);
        dishDao.create(dish);
    }
    
    @Transactional
    public void updateDish(Dish t) {
        dishDao.update(t);
    }
    
    @Transactional
    public void deleteDish(int id) {
        dishDao.delete(id);
    }    

    public List<Dish> findAll() {
        return dishDao.findAll();
    }   
}
