package com.luv2code.springboot.thymeleafdemo.dao.notes;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Notes;

public interface NotesDAO {
	
	public void bindToMachine(int machineId);
	
	public Notes getByPrimaryId(int id);
	
	public void save(String noteToAdd);
	
	public List<Notes> getNotes(int machineId);
	

}
