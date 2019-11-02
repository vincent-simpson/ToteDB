package com.vince.springboot.app.service;

import java.util.List;

import com.vince.springboot.app.entity.Machine;

public interface MachineService {
	
	Machine getByPrimaryId(int id);
	
	Machine getBySerialNumber(int serialNumber);
	
	List<Machine> getByBettingArea(String bettingAreaName);
	
	Machine getByLSN(int LSN);
	
	void assignToLSN(Machine machine, int LSN);
	
	void removeFromLSN(Machine machine, int LSN);
	
	void assignBettingArea(Machine machine, String bettingArea);
	
	void save(Machine machine);
	
	void save(List<Machine> machines);
	
	List<Machine> getAll();
	
	void delete(int id);
	
	String getNotesById(int id);
	
	String getNotesByLSN(int LSN);

	void unbindFromBettingArea(int machineId);

}
