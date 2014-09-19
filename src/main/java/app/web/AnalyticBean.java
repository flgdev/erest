package app.web;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import app.entity.MyReport;
import app.service.OrderitemService;

@Named
@Scope("request")
public class AnalyticBean {
    private Date from;
    private Date to;
    private List<MyReport> dishReport;
    private List<MyReport> dayReport;
    private MyReport total;
    
    @Inject
    private OrderitemService orderitemService; 
	
	public List<MyReport> getByDay() {
		return dayReport;
	}
	
	public List<MyReport> getByDish() {	
		return dishReport;
	}
	
	public MyReport getTotal(){
		return total;
	}
	
    @PostConstruct
    private void init() {
    	if((from==null)||(to==null)){
    		from = new Date();
    		to = new Date();
    		to.setTime(to.getTime()+86400000);
    	}
        dishReport = orderitemService.getByDish(from,to);
        dayReport = orderitemService.getByDay(from,to);
        total = orderitemService.getTotal(from,to);
    }		
	
	public void refreshReport() {	
		init();
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}
