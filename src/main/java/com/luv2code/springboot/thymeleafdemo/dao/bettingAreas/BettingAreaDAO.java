package com.luv2code.springboot.thymeleafdemo.dao.bettingAreas;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.BettingArea;

public interface BettingAreaDAO {
	
	public BettingArea getByName(String name);
	
	public List<BettingArea> getAll();
	
	public void save(BettingArea bettingArea);
	
	public void save(List<BettingArea> bettingAreas);
	
	public void delete(int theId);

}
