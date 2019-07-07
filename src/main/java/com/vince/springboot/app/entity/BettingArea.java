package com.vince.springboot.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="betting_areas")
@Table(name="betting_areas")
public class BettingArea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name="area_name")
	private String areaName;
	
	public BettingArea() {}

	public BettingArea(int id, String areaName) {
		this.id = id;
		this.areaName = areaName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "BettingArea [id=" + id + ", areaName=" + areaName + "]";
	}
		
	

}
