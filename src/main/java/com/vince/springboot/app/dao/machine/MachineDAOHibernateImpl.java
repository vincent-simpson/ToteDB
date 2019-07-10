package com.vince.springboot.app.dao.machine;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.vince.springboot.app.entity.Machine;

@Repository
public class MachineDAOHibernateImpl implements MachineDAO {
	
	private EntityManager entityManager;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public MachineDAOHibernateImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public Machine getByPrimaryId(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		return 	currentSession.get(Machine.class, id);
	}

	@Override
	public Machine getBySerialNumber(String serialNumber) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from machines where serial_number=:serialNumber");
		theQuery.setParameter("serialNumber", serialNumber);

		Machine machine;
		
		try {
			machine = (Machine) theQuery.uniqueResult();
		} catch (NonUniqueResultException exception) {
			machine = null;
		}
		return machine;
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
		
		Query theQuery = currentSession.createQuery("update machines set lsn_number =:lsnNumber where machine_id =:machineId");
		theQuery.setParameter("lsnNumber", LSN);
		theQuery.setParameter("machineId", machine.getMachineId());
		
		theQuery.executeUpdate();		
		
	}

	@Override
	public void removeFromLSN(Machine machine, int LSN) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update machines set lsn_number =:lsnNumber where machine_id =:machineId");
		theQuery.setParameter("lsnNumber", null);
		theQuery.setParameter("machineId", machine.getMachineId());
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void assignBettingArea(Machine machine, String bettingArea) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update machines set betting_area =:bettingArea where machine_id =:machineId");
		theQuery.setParameter("bettingArea", bettingArea);
		theQuery.setParameter("machineId", machine.getMachineId());
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void save(Machine machine) {
		Session currentSession = entityManager.unwrap(Session.class);
		logger.warn("machine id = " + machine.getMachineId());
		currentSession.saveOrUpdate(machine);
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
		
//		//comment these two lines below in/out if local or heroku
//		Query setUpSequences = currentSession.createNativeQuery("SET search_path TO monmouth, public");
//		setUpSequences.executeUpdate();
		
		Query<Machine> theQuery = 
				currentSession.createQuery("from machines", Machine.class);

		return theQuery.getResultList();
	}

	@Override
	public ResponseEntity delete(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query machineQuery = currentSession.createQuery("delete from machines where machine_id=:machineId");
		Query notesQuery = currentSession.createQuery("delete from notes where machine_id=:machineId");

		machineQuery.setParameter("machineId", id);
		notesQuery.setParameter("machineId", id);

//		try {
			notesQuery.executeUpdate();
			machineQuery.executeUpdate();

			Query emptyTableQuery = currentSession.createQuery("from machines");
			List temp = emptyTableQuery.getResultList();

			if(temp.isEmpty()) {
				//comment these two lines in/out if local or heroku
				Query autoIncrementZero = currentSession.createSQLQuery("ALTER SEQUENCE machines_machine_id_seq RESTART WITH 1");
				autoIncrementZero.executeUpdate();
			} else {
				Logger logger = LoggerFactory.getLogger(this.getClass());
				logger.warn(temp.toString());
			}
			return new ResponseEntity(HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		//}
	}

	@Override
	public String getNotesById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("SELECT notes FROM machines WHERE machine_id=:machineId");
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

	@Override
	public void unbindFromBettingArea(int machineId) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = currentSession.createQuery("UPDATE machines SET betting_area = 0 WHERE machine_id =:machineId");
		theQuery.setParameter("machineId", machineId);
		theQuery.executeUpdate();
	}

}
