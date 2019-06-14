package com.luv2code.springboot.thymeleafdemo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="notes")
@Table(name="notes")
public class Notes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="note")
	private String note;
	
	@ManyToOne(targetEntity = Machine.class)
	@JoinColumn(name="mid", nullable = false)
	private int machineId;
	
	public Notes() {}

	public Notes(int id, String note) {
		this.id = id;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	@Override
	public String toString() {
		return "Notes [id=" + id + ", note=" + note + "]";
	}
	
	
	
	

}
