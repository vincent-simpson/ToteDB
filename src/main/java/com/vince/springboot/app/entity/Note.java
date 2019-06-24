package com.vince.springboot.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="notes")
@Table(name="notes")
public class Note {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="machine_id")
	private Machine machineId;
	
	@Column(name="date")
	private String date;
	
	public Note() {}

	public Note(int id, String note, String date) {
		this.id = id;
		this.note = note;
		this.date = date;
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

	public Machine getMachineId() {
		return machineId;
	}

	public void setMachineId(Machine machineId) {
		this.machineId = machineId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Notes [id=" + id + ", note=" + note + ", machineId=" + machineId + ", date=" + date + "]";
	}
	
	
	
	
	
	
	
	

}
