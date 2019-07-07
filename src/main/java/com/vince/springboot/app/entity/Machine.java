package com.vince.springboot.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="machines")
@Table(name="machines")
public class Machine extends Throwable {
	
	@Id
	@Column(name="machine_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int machineId;
	
	@Column(name="lsn_number")
	private int lsnNumber;
	
	@Column(name="betting_area")
	private int bettingArea;
	
	@Column(name="serial_number")
	private String serialNumber;

	private String bettingAreaAsName;
	
	@OneToMany(targetEntity = Note.class, orphanRemoval = true, mappedBy = "machineId")
	private List<Note> notes;
	
	
	public Machine() {}
	
	public Machine(int id, int lsnNumber, int bettingArea, String serialNumber) {
		this.machineId = id;
		this.lsnNumber = lsnNumber;
		this.bettingArea = bettingArea;
		this.serialNumber = serialNumber;
	}
	
	public Machine(int id) {
		this.machineId = id;
	}
	
	public Machine(int lsnNumber, String serialNumber) {
		this.lsnNumber = lsnNumber;
		this.serialNumber = serialNumber;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
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

	public String getBettingAreaAsName() {
		return this.bettingAreaAsName;
	}

	public void setBettingAreaAsName(String s) {
		this.bettingAreaAsName = s;
	}

	@Override
	public String toString() {
		return "Machine [id=" + machineId + ", lsnNumber=" + lsnNumber + ", bettingArea=" + bettingArea + ", serialNumber="
				+ serialNumber + "]";
	}
	
	
	

}
