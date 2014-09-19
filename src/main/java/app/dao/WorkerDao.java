package app.dao;

import org.springframework.stereotype.Repository;

import app.entity.Worker;

import java.util.List;

@Repository
public class WorkerDao extends GenericDaoImp<Worker>{
	public List<Worker> findAll() {
		return em.createQuery("from Worker").getResultList();
	}
	
	public Worker getByName(String name){
		List<Worker> w = em.createQuery("SELECT w from Worker w where w.name = :name").setParameter("name", name).getResultList();
		if(w.size()>0){
			return w.get(0);
		}
		return null;
	}
}
