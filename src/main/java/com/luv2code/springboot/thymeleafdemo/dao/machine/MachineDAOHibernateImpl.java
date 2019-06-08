package com.luv2code.springboot.thymeleafdemo.dao.machine;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.Machine;

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
		theQuery.setParameter("machineId", machine.getId());
		
		theQuery.executeUpdate();		
		
	}

	@Override
	public void removeFromLSN(Machine machine, int LSN) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update Machines set lsn_number =:lsnNumber where id =:machineId");
		theQuery.setParameter("lsnNumber", null);
		theQuery.setParameter("machineId", machine.getId());
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void assignBettingArea(Machine machine, String bettingArea) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update Machines set betting_area =:bettingArea where id =:machineId");
		theQuery.setParameter("bettingArea", bettingArea);
		theQuery.setParameter("machineId", machine.getId());
		
		theQuery.executeUpdate();
		
	}

	@Override
	public void save(Machine machine) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from machines where id=:machineId");
		theQuery.setParameter("machineId", machine.getId());
		
		Machine machine2 = (Machine) theQuery.getSingleResult();
		
		if(machine2 == null) {
			currentSession.save(machine);
		} else {
			currentSession.evict(machine2);
			currentSession.update(machine);
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
