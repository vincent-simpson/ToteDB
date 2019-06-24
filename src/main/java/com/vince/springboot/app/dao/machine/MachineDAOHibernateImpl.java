package com.vince.springboot.app.dao.machine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vince.springboot.app.entity.Machine;

@Repository
public class MachineDAOHibernateImpl implements MachineDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public MachineDAOHibernateImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Machine getByPrimaryId(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Machine theMachine = 
				currentSession.get(Machine.class, id);
		
		return theMachine;
	}

	@Override
	public Machine getBySerialNumber(String serialNumber) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from machines where serial_number=:serialNumber");
		theQuery.setParameter("serialNumber", serialNumber);
		
		Machine theMachine = (Machine) theQuery.uniqueResult();		
		
		return theMachine;
	}

	@Override
	public List<Machine> getByBettingArea(String bettingAreaName) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Machine> theQuery = 
				currentSession.createQuery("from machines where betting_area=:bettingArea", Machine.class);
		
		theQuery.setParameter("bettingArea", bettingAreaName);
		
		List<Machine> theMachines = theQuery.getResultList();
		
		return theMachines;
	}

	@Override
	public Machine getByLSN(int LSN) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from machines where lsn_number=:lsnNumber");
		theQuery.setParameter("lsnNumber", LSN);
		
		Machine theMachine = (Machine) theQuery.uniqueResult();
		
		return theMachine;
	}

	@Override
	public void assignToLSN(Machine machine, int LSN) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update Machines set lsn_number =:lsnNumber where id =:machineId");
		theQuery.setParameter("lsnNumber", LSN);
		theQuery.setParameter("machineId", machine.getMachineId());
		
		theQuery.executeUpdate();		
		
	}

	@Override
	public void removeFromLSN(Machine machine, int LSN) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update Machines set lsn_number =:lsnNumber where id =:machineId");
		theQuery.setParameter("lsnNumber", null);
		theQuery.setParameter("machineId", machine.getMachineId());
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void assignBettingArea(Machine machine, String bettingArea) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update Machines set betting_area =:bettingArea where id =:machineId");
		theQuery.setParameter("bettingArea", bettingArea);
		theQuery.setParameter("machineId", machine.getMachineId());
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void save(Machine machine) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from machines where id=:machineId");
		theQuery.setParameter("machineId", machine.getMachineId());
		
		Machine machine2;
		
		try {
			
			Query updateQuery = currentSession.createNativeQuery("UPDATE machines SET lsn_number=:lsnParam, serial_number=:serialParam WHERE machineId=:machineIdParam");
			updateQuery.setParameter("lsnParam", machine.getLsnNumber());
			updateQuery.setParameter("serialParam", machine.getSerialNumber());
			updateQuery.setParameter("machineIdParam", machine.getMachineId());
			
			machine2 = (Machine) theQuery.getSingleResult(); //throws NoResultException
			currentSession.evict(machine2);
			updateQuery.executeUpdate();
		} catch (NoResultException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn("saving machine... : " + machine.toString());
			currentSession.save(machine);
		}		
		
	}

	@Override
	public void save(List<Machine> machines) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		for(Machine temp : machines) {
			currentSession.saveOrUpdate(temp);
		}
	}

	@Override
	public List<Machine> getAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
//		Query setUpSequences = currentSession.createNativeQuery("SET search_path TO monmouth, public");
//		setUpSequences.executeUpdate();
		
		Query<Machine> theQuery = 
				currentSession.createQuery("from machines", Machine.class);
		
		List<Machine> machines = theQuery.getResultList();
		
		
		
		return machines;
	}

	@Override
	public void delete(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("delete from machines where id=:machineId");
		theQuery.setParameter("machineId", id);
		
		theQuery.executeUpdate();
		
		Query emptyTableQuery = currentSession.createQuery("from machines");
		List<Machine> temp = emptyTableQuery.getResultList();
		
		if(temp.isEmpty()) {
//			Query autoIncrementZero = currentSession.createSQLQuery("ALTER SEQUENCE machines_id_seq RESTART WITH 1");
//			autoIncrementZero.executeUpdate();
		} else {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(temp.toString());
		}
		
	}

	@Override
	public String getNotesById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("SELECT notes FROM machines WHERE id=:machineId");
		theQuery.setParameter("machineId", id);
		
		
		return (String) theQuery.uniqueResult();
	}

	@Override
	public String getNotesByLSN(int LSN) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("SELECT notes FROM machines WHERE lsn_number=:lsnNumber");
		theQuery.setParameter("lsnNumber", LSN);
		
		
		return (String) theQuery.uniqueResult();
	}

}
