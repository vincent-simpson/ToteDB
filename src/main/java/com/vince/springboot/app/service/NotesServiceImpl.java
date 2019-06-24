package com.vince.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vince.springboot.app.dao.notes.NotesDAO;
import com.vince.springboot.app.entity.Note;

@Service
public class NotesServiceImpl implements NotesService {
	
	private NotesDAO notesDAO;
	
	@Autowired
	public NotesServiceImpl(NotesDAO notesDAO) {
		this.notesDAO = notesDAO;
	}

	@Override
	@Transactional
	public void bindToMachine(int machineId) {
		notesDAO.bindToMachine(machineId);
		
	}

	@Override
	@Transactional
	public Note getByPrimaryId(int id) {
		return notesDAO.getByPrimaryId(id);
	}

	@Override
	@Transactional
	public int save(Note noteToAdd) {
		return notesDAO.save(noteToAdd);
	}

	@Override
	@Transactional
	public List<Note> getNotes(int machineId) {
		return notesDAO.getNotes(machineId);
	}

	@Override
	@Transactional
	public int deleteById(int theId) {
		return notesDAO.deleteById(theId);
	}

}
