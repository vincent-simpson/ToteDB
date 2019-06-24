package com.vince.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vince.springboot.app.entity.BettingArea;
import com.vince.springboot.app.dao.bettingAreas.BettingAreaDAO;

@Service
public class BettingAreaServiceImpl implements BettingAreaService {
	
	private BettingAreaDAO bettingDAO;
	
	@Autowired
	public BettingAreaServiceImpl(BettingAreaDAO bettingDAO) {
		this.bettingDAO = bettingDAO;
	}

	@Override
	@Transactional
	public BettingArea getByName(String name) {
		return bettingDAO.getByName(name);
	}

	@Override
	@Transactional
	public List<BettingArea> getAll() {
		return bettingDAO.getAll();
	}

	@Override
	@Transactional
	public void save(BettingArea bettingArea) {
		bettingDAO.save(bettingArea);		
	}

	@Override
	@Transactional
	public void save(List<BettingArea> bettingAreas) {
		bettingDAO.save(bettingAreas);
	}

	@Override
	@Transactional
	public void delete(int theId) {
		bettingDAO.delete(theId);
	}

	@Override
	@Transactional
	public BettingArea getById(int id) {
		return bettingDAO.getById(id);
	}

}
