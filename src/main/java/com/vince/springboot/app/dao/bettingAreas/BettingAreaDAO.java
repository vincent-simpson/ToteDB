package com.vince.springboot.app.dao.bettingAreas;

import java.util.List;

import com.vince.springboot.app.entity.BettingArea;

public interface BettingAreaDAO {
	
	public BettingArea getByName(String name);
	
	public BettingArea getById(int id);
	
	public List getAll();
	
	public void save(BettingArea bettingArea);
	
	public void save(List<BettingArea> bettingAreas);
	
	public int delete(int theId);
	

}
