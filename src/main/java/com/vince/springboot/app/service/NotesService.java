package com.vince.springboot.app.service;

import java.util.List;

import com.vince.springboot.app.entity.Note;

public interface NotesService  {
	
	public void bindToMachine(int machineId);
	
	public Note getByPrimaryId(int id);
	
	public int save(Note noteToAdd);
	
	public List<Note> getNotes(int machineId);
	
	public int deleteById(int theId);


}
