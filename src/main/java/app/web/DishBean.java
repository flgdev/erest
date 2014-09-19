package app.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import app.entity.Dish;
import app.service.DishService;

@Named
@Scope("request")
public class DishBean {
	private int id;
	private String name;
	private double price;
	private boolean kitchen;
	private boolean deleted;
	private Dish.Category category;
	private List<Dish> dlist;

    @Inject
    private DishService dishService;

    public void createDish() throws IOException {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dish created","");
        FacesContext.getCurrentInstance().addMessage(null, msg);    	
        dishService.createDish(name,price,category,kitchen);
        init();
    }
    
    public void deleteDish(int id) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dish deleted","");
        FacesContext.getCurrentInstance().addMessage(null, msg);          	
        dishService.deleteDish(id);
        init();
    }    
    
	public List<Dish> getDishes() {
		return dlist;
	}    
	
    @PostConstruct
    private void init() {
    	dlist = dishService.findAll();
    }	
    
    public List<Dish> catDishes(int cat) {
    	List<Dish> res = new ArrayList<Dish>();
    	for(Dish d:dlist){
    		if(d.getCategory().ordinal()==cat){
    			res.add(d);
    		}
    	}
    	return res;
    }	    

	public List<Dish.Category> getCategories() {
		return Arrays.asList(Dish.Category.values());
	}	
	
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext context = FacesContext.getCurrentInstance();
            Dish d = context.getApplication().evaluateExpressionGet(context, "#{item}", Dish.class);
    		dishService.updateDish(d);
    		init();
        }
    }	
    
    public void onKBoxEdit(Dish d) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);    	
    	dishService.updateDish(d);
    	init();
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

	public Dish.Category getCategory() {
		return category;
	}

	public void setCategory(Dish.Category category) {
		this.category = category;
	}
}
