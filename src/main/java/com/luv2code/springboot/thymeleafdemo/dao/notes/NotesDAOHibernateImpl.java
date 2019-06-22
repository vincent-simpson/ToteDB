package com.luv2code.springboot.thymeleafdemo.dao.notes;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.thymeleafdemo.entity.Machine;
import com.luv2code.springboot.thymeleafdemo.entity.Note;

@Repository
public class NotesDAOHibernateImpl implements NotesDAO {
	
	private EntityManager entityManager;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	public Note getByPrimaryId(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = currentSession.createQuery("from notes where id=:idParam");
		theQuery.setParameter("idParam", id);
		
		Note note = (Note) theQuery.uniqueResult();
		
		return note;
	}

	@Override
	public void save(Note noteToAdd) {
		Session currentSession = entityManager.unwrap(Session.class);
		logger.warn("note to add = " + noteToAdd);
		currentSession.save(noteToAdd);
		
	}

	@Override
	public List<Note> getNotes(int machineId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Note> theQuery = 
				currentSession.createQuery("from notes where machineId =:machineIdParam", Note.class);
		
		theQuery.setParameter("machineIdParam", new Machine(machineId));
		
		List<Note> notes = theQuery.getResultList();
		return notes;
	}

	@Override
	public int deleteById(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Note> deleteQuery = currentSession.createQuery("delete from notes where id=:noteId");
		deleteQuery.setParameter("noteId", theId);
		
		int num = deleteQuery.executeUpdate();
		
		return num;
	}

}
