package com.luv2code.springboot.thymeleafdemo.dao.notes;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.Notes;

@Repository
public class NotesDAOHibernateImpl implements NotesDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public NotesDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void bindToMachine(int machineId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("update notes set machineId =:machineIdParam");
		theQuery.setParameter("machineIdParam", machineId);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public Notes getByPrimaryId(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from notes where id=:idParam");
		theQuery.setParameter("idParam", id);
		
		Notes note = (Notes) theQuery.uniqueResult();
		
		return note;
	}

	@Override
	public void save(String noteToAdd) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.save(noteToAdd);
		
	}

	@Override
	public List<Notes> getNotes(int machineId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Notes> theQuery = 
				currentSession.createQuery("from notes", Notes.class);
		
		List<Notes> notes = theQuery.getResultList();
		return notes;
	}

}
