package com.vince.springboot.app.dao.bettingAreas;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vince.springboot.app.entity.BettingArea;


@Repository
public class BettingAreaHibernateImpl implements BettingAreaDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public BettingAreaHibernateImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}
	
	@Override
	public List getAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
//		//comment these two lines in/out depending on if local or heroku
//		Query setUpSequences = currentSession.createNativeQuery("SET search_path TO monmouth, public");
//		setUpSequences.executeUpdate();
//		
		NativeQuery theQuery =
				currentSession.createSQLQuery("select * from betting_areas");
		theQuery.addEntity(BettingArea.class);
		
		return theQuery.getResultList();
	}

	@Override
	public BettingArea getByName(String name) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		NativeQuery theQuery = currentSession.createSQLQuery("SELECT * FROM betting_areas " +
				"where area_name = " + "'" + name + "'");
		theQuery.addEntity(BettingArea.class);

		return (BettingArea) theQuery.uniqueResult();
	}

	@Override
	public void save(BettingArea bettingArea) {

		Session currentSession = entityManager.unwrap(Session.class);
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.warn(bettingArea.toString());
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
	public int delete(int theId) {
		Logger logger = LoggerFactory.getLogger(this.getClass());

		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from betting_areas where id=:machineId");
		theQuery.setParameter("machineId", theId);
		try {
			theQuery.executeUpdate();
		} catch (Exception e) {
			return 0;
		}
		
		
		Query<BettingArea> emptyTableQuery = currentSession.createSQLQuery("select * from betting_areas");
		List<BettingArea> temp = emptyTableQuery.getResultList();
		
		if (temp.isEmpty()) {
			//comment these two lines in/out depending on if local or heroku
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

		return currentSession.get(BettingArea.class, id);
	}	

}
