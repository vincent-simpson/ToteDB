package com.luv2code.springboot.thymeleafdemo.dao.notes;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Note;

public interface NotesDAO {
	
	public void bindToMachine(int machineId);
	
	public Note getByPrimaryId(int id);
	
	public int save(Note noteToAdd);
	
	public List<Note> getNotes(int machineId);
	
	public int deleteById(int theId);
	

}
