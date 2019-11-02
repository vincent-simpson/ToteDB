package com.vince.springboot.app.service;

import java.util.List;

import com.vince.springboot.app.dao.machine.MachineDAOHibernateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vince.springboot.app.dao.machine.MachineDAO;
import com.vince.springboot.app.entity.Machine;

@Service
public class MachineServiceImpl implements MachineService {
	
	private MachineDAO machineDAO;
	
	@Autowired
	public  MachineServiceImpl(MachineDAO machineDAO) {
		this.machineDAO = machineDAO;
	}

	@Override
	@Transactional
	public Machine getByPrimaryId(int id) {
		return machineDAO.getByPrimaryId(id);
	}

	@Override
	@Transactional
	public Machine getBySerialNumber(int serialNumber) {
		return machineDAO.getBySerialNumber(serialNumber);
	}

	@Override
	@Transactional
	public List<Machine> getByBettingArea(String bettingAreaName) {
		return machineDAO.getByBettingArea(bettingAreaName);
	}

	@Override
	@Transactional
	public Machine getByLSN(int LSN) {
		return machineDAO.getByLSN(LSN);
	}

	@Override
	@Transactional
	public void assignToLSN(Machine machine, int LSN) {
		machineDAO.assignToLSN(machine, LSN);
	}

	@Override
	@Transactional
	public void removeFromLSN(Machine machine, int LSN) {
		machineDAO.removeFromLSN(machine, LSN);
	}

	@Override
	@Transactional
	public void assignBettingArea(Machine machine, String bettingArea) {
		machineDAO.assignBettingArea(machine, bettingArea);
	}

	@Override
	@Transactional
	public void save(Machine machine) {
		machineDAO.save(machine);
	}

	@Override
	@Transactional
	public void save(List<Machine> machines) {
		machineDAO.save(machines);		
	}

	@Override
	@Transactional
	public List<Machine> getAll() {
		return machineDAO.getAll();
	}

	@Override
	@Transactional
	public void delete(int id) {
		machineDAO.delete(id);
	}

	@Override
	@Transactional
	public String getNotesById(int id) {
		return machineDAO.getNotesById(id);
	}

	@Override
	@Transactional
	public String getNotesByLSN(int LSN) {
		return machineDAO.getNotesByLSN(LSN);
	}

	@Override
	@Transactional
	public void unbindFromBettingArea(int machineId) {
		machineDAO.unbindFromBettingArea(machineId);
	}

}
