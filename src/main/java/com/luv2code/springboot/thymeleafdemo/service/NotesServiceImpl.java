package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springboot.thymeleafdemo.dao.notes.NotesDAO;
import com.luv2code.springboot.thymeleafdemo.entity.Note;

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
	public void save(Note noteToAdd) {
		notesDAO.save(noteToAdd);
	}

	@Override
	@Transactional
	public List<Note> getNotes(int machineId) {
		return notesDAO.getNotes(machineId);
	}

}
