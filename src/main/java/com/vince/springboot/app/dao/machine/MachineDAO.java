package com.vince.springboot.app.dao.machine;

import java.util.List;

import com.vince.springboot.app.entity.Machine;
import org.springframework.http.ResponseEntity;

public interface MachineDAO {

	Machine getByPrimaryId(int id);

	Machine getBySerialNumber(String serialNumber);

	List<Machine> getByBettingArea(String bettingAreaName);

	Machine getByLSN(int LSN);

	void assignToLSN(Machine machine, int LSN);

	void removeFromLSN(Machine machine, int LSN);

	void assignBettingArea(Machine machine, String bettingArea);

	void save(Machine machine);

	void save(List<Machine> machines);

	List<Machine> getAll();

	ResponseEntity delete(int id);
	
	String getNotesById(int id);
	
	String getNotesByLSN(int LSN);

	void unbindFromBettingArea(int machineId);

}
