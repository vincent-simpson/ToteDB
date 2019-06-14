package com.luv2code.springboot.thymeleafdemo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="machines")
@Table(name="machines")
public class Machine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="lsn_number")
	private int lsnNumber;
	
	@Column(name="betting_area")
	private int bettingArea;
	
	@Column(name="serial_number")
	private String serialNumber;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="mid")
	private List<Notes> notes;
	
	
	public Machine() {}
	
	public Machine(int id, int lsnNumber, int bettingArea, String serialNumber) {
		this.id = id;
		this.lsnNumber = lsnNumber;
		this.bettingArea = bettingArea;
		this.serialNumber = serialNumber;
	}
	
	public Machine(int lsnNumber, String serialNumber) {
		this.lsnNumber = lsnNumber;
		this.serialNumber = serialNumber;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
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

	@Override
	public String toString() {
		return "Machine [id=" + id + ", lsnNumber=" + lsnNumber + ", bettingArea=" + bettingArea + ", serialNumber="
				+ serialNumber + "]";
	}
	
	
	

}
