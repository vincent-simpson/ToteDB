package com.luv2code.springboot.thymeleafdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.service.BettingAreaService;

@Entity(name="machines")
@Table(name="machines")
public class Machine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="lsn_number")
	private int lsnNumber;
	
	@Column(name="betting_area")
	private int bettingArea;
	
	@Column(name="serial_number")
	private String serialNumber;
	
	@Column(name="notes")
	private String notes;
		
	public Machine() {}
	
	public Machine(int id, int lsnNumber, int bettingArea, String serialNumber, String notes) {
		this.id = id;
		this.lsnNumber = lsnNumber;
		this.bettingArea = bettingArea;
		this.serialNumber = serialNumber;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLsnNumber() {
		return lsnNumber;
	}

	public void setLsnNumber(int lsnNumber) {
		this.lsnNumber = lsnNumber;
	}

	public int getBettingArea() {
		return bettingArea;
	}

	public void setBettingArea(int bettingArea) {
		
		
		this.bettingArea = bettingArea;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", lsnNumber=" + lsnNumber + ", bettingArea=" + bettingArea + ", serialNumber="
				+ serialNumber + ", notes=" + notes + "]";
	}
	
	
	

}
