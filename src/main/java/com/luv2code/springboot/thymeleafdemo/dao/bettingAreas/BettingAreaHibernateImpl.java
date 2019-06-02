package com.luv2code.springboot.thymeleafdemo.dao.bettingAreas;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.BettingArea;

@Repository
public class BettingAreaHibernateImpl implements BettingAreaDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public BettingAreaHibernateImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}
	
	@Override
	public List<BettingArea> getAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<BettingArea> theQuery = 
				currentSession.createQuery("from betting_areas", BettingArea.class);
		
		List<BettingArea> bettingAreas = theQuery.getResultList();
		
		return bettingAreas;
	}

	@Override
	public BettingArea getByName(String name) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from betting_areas where area_name=:areaName");
		theQuery.setParameter("areaName", name);
		
		BettingArea theBettingArea = (BettingArea) theQuery.uniqueResult();
		
		return theBettingArea;
	}

	@Override
	public void save(BettingArea bettingArea) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(bettingArea);
		
	}

	@Override
	public void save(List<BettingArea> bettingAreas) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		for(BettingArea bettingArea : bettingAreas) {
			currentSession.saveOrUpdate(bettingArea);
		}
		
	}

	@Override
	public void delete(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from betting_areas where id=:machineId");
		theQuery.setParameter("machineId", theId);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public BettingArea getById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		BettingArea bettingArea = currentSession.get(BettingArea.class, id);
		
		
		return bettingArea;
	}	

}
