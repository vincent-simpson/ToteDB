package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.BettingArea;

public interface BettingAreaService {
	
	public BettingArea getByName(String name);
	
	public BettingArea getById(int id);
	
	public List<BettingArea> getAll();
	
	public void save(BettingArea bettingArea);
	
	public void save(List<BettingArea> bettingAreas);
	
	public void delete(int theId);

}
