package app.service;

import app.dao.WorkerDao;
import app.entity.Worker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;


@Service
public class WorkerService {
    @Inject
    private WorkerDao workerDao;

    @Transactional
    public void createWorker(String name, String password, Worker.Role role) {
    	Worker worker = new Worker();
    	worker.setName(name);
    	worker.setPassword(password);
    	worker.setRole(role);
        workerDao.create(worker);
    }
    
    @Transactional
    public void deleteWorker(int id) {
        workerDao.delete(id);
    }
    
	public Worker.Role login(String name, String pass) {
		Worker w = workerDao.getByName(name);
		if(w==null||(!pass.equals(w.getPassword())))return null;
		return w.getRole();
	}	    

    public List<Worker> findAll() {
        return workerDao.findAll();
    }
}
