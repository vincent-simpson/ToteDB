package com.luv2code.springboot.thymeleafdemo.dao.bettingAreas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		
		Query setUpSequences = currentSession.createNativeQuery("SET search_path TO monmouth, public");
		setUpSequences.executeUpdate();
//		
		Query<BettingArea> theQuery = 
				currentSession.createQuery("from betting_areas", BettingArea.class);
		
		List<BettingArea> bettingAreas = theQuery.getResultList();
		
		
		
		return bettingAreas;
	}

	@Override
	public BettingArea getByName(String name) {
		
		Logger logger = LoggerFactory.getLogger(this.getClass());

		
		Session currentSession = entityManager.unwrap(Session.class);
		
		NativeQuery theQuery = currentSession.createSQLQuery("SELECT * FROM betting_areas where area_name = " + name);
		theQuery.addEntity(BettingArea.class);	
				
		BettingArea theBettingArea = (BettingArea) theQuery.uniqueResult();
		
		logger.warn(theBettingArea + " inside getByName()");
		
		return theBettingArea;
	}

	@Override
	public void save(BettingArea bettingArea) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<BettingArea> theQuery = currentSession.createQuery("from betting_areas where id=:bettingAreaId");
		theQuery.setParameter("bettingAreaId", bettingArea.getId());
		
		try {
			BettingArea bettingArea2 = theQuery.getSingleResult();
			
			currentSession.evict(bettingArea2);
			currentSession.update(bettingArea);
		} catch (NoResultException e) {
			currentSession.save(bettingArea);
		}
				
	}

	@Override
	public void save(List<BettingArea> bettingAreas) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		for(BettingArea bettingArea : bettingAreas) {
			currentSession.saveOrUpdate(bettingArea);
		}
		
	}

	@Override
	public int delete(int theId) {
		Logger logger = LoggerFactory.getLogger(this.getClass());

		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<BettingArea> theQuery = currentSession.createQuery("delete from betting_areas where id=:machineId");
		theQuery.setParameter("machineId", theId);
		try {
			theQuery.executeUpdate();
		} catch (Exception e) {
			return 0;
		}
		
		
		Query<BettingArea> emptyTableQuery = currentSession.createQuery("from betting_areas");
		List<BettingArea> temp = emptyTableQuery.getResultList();
		
		if (temp.isEmpty()) {
			logger.warn("in if statement");
			
			Query autoIncrementZero = currentSession.createSQLQuery("ALTER SEQUENCE betting_areas_id_seq RESTART WITH 1");
			autoIncrementZero.executeUpdate();
		} else {
			logger.warn(emptyTableQuery.getResultList().toString());
		}
		return 1;
	}

	@Override
	public BettingArea getById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		BettingArea bettingArea = currentSession.get(BettingArea.class, id);
		
		
		return bettingArea;
	}	

}
