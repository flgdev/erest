package app.web;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import app.entity.Worker;
import app.service.WorkerService;

@Named
@Scope("request")
public class WorkerBean {
	private int id;
	private String name;
	private String password;
	private Worker.Role role;
	private List<Worker> workers;
	
    @Inject
    private WorkerService workerService;
	
    public void createWorker() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "User created","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        workerService.createWorker(name,password,role);
        init();
    }
    
    public void deleteWorker(int id) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "User deleted","");
        FacesContext.getCurrentInstance().addMessage(null, msg);        	
        workerService.deleteWorker(id);
        init();
    }    
    
    @PostConstruct
    private void init() {
    	workers = workerService.findAll();
    }    

    public List<Worker> getWorkers() {
        return workers;
    }	

	public List<Worker.Role> getRoles() {
		return Arrays.asList(Worker.Role.values());
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Worker.Role getRole() {
		return role;
	}

	public void setRole(Worker.Role role) {
		this.role = role;
	}
}
