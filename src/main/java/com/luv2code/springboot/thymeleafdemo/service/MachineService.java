package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Machine;

public interface MachineService {
	
	public Machine getByPrimaryId(int id);
	
	public Machine getBySerialNumber(String serialNumber);
	
	public List<Machine> getByBettingArea(String bettingAreaName);
	
	public Machine getByLSN(int LSN);
	
	public void assignToLSN(Machine machine, int LSN);
	
	public void removeFromLSN(Machine machine, int LSN);
	
	public void assignBettingArea(Machine machine, String bettingArea);
	
	public void save(Machine machine);
	
	public void save(List<Machine> machines);
	
	public List<Machine> getAll();
	
	public void delete(int id);

}
