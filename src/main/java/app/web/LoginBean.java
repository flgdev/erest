package app.web;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import app.entity.Worker;
import app.service.WorkerService;

@Named
@Scope("session")
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private Worker.Role role;
	private String name;
	private String password;

	@Inject
	private transient WorkerService workerService;

	static private String[] pages = { "restadmin", "kitchen", "analytic",
			"superuser" };

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

	public void login() {
		role = workerService.login(name, password);
		for (int i = 0; i < Worker.Role.values().length; i++) {
			if (role == Worker.Role.values()[i]) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				try {
					context.redirect(pages[i]+".xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);     
	}
	public boolean right(String s) {
		for (int i = 0; i < Worker.Role.values().length; i++) {
			if ((s.equals(pages[i]))&&(role == Worker.Role.values()[i])) {
				return true;
			}
		}
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
		try {
			context.redirect("in.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String logout() {
		role = null;
		name = "";
		password = "";
		return "in.xhtml";
	}

	public Worker.Role getRole() {
		return role;
	}

	public void setRole(Worker.Role role) {
		this.role = role;
	}
}
