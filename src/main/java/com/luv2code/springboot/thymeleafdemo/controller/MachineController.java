package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.BettingArea;
import com.luv2code.springboot.thymeleafdemo.entity.Machine;
import com.luv2code.springboot.thymeleafdemo.entity.Note;
import com.luv2code.springboot.thymeleafdemo.service.BettingAreaService;
import com.luv2code.springboot.thymeleafdemo.service.MachineService;
import com.luv2code.springboot.thymeleafdemo.service.NotesService;

@Controller
@RequestMapping("/machines")
public class MachineController {
	
	private MachineService machineService;
	private NotesService notesService;
	private BettingAreaService bettingAreaService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public MachineController(MachineService machineService, BettingAreaService bettingAreaService,
			NotesService notesService) {
		this.machineService = machineService;
		this.bettingAreaService = bettingAreaService;
		this.notesService = notesService;
	}
	
	@PostMapping("/save")
	public String saveMachine(@ModelAttribute("machine") Machine machine, 
			@RequestParam("machineId2") int id,
			@RequestParam("bettingAreaButtonText") String bettingAreaButtonText,
			Model model) {
		
		Machine machine2;
		
		if(id == -1) {
			machine2 = new Machine(machine.getLsnNumber(), machine.getSerialNumber());
		} else {
			machine2 = machine;
		}
		
		logger.warn(bettingAreaButtonText + "  betting area button text");
						
		BettingArea temp = bettingAreaService.getByName(bettingAreaButtonText);
		
		logger.warn("temp to string: " + temp.toString());
		
		machine2.setBettingArea(
				temp.getId());
		
		machine2.setMachineId(id);
		
		model.addAttribute("machine", machine2);
		
		machineService.save(machine);
		
		return "redirect:/bettingAreas/list";
		
	}
	
	@GetMapping("/list")
	public String machineList(Model theModel) {
		List<Machine> machines = machineService.getAll();
		Machine machine = new Machine();
		
		theModel.addAttribute("machines", machines);
		theModel.addAttribute("machine", machine);
		
		List<Note> notes = new ArrayList<Note>();
		
		theModel.addAttribute("notes", notes);

		
		return "machineList";
	}
	
	@GetMapping("/delete")
	public String deleteMachine(@RequestParam("machineId") int id) {
		
		machineService.delete(id);
		
		return "redirect:/bettingAreas/list";
	}
	
	@PostMapping("/edit")
	public String editMachine(@RequestParam("machineId") int id, Model model) {
		
		logger.warn("/edit mapping id request param = " + id);
		
		if(id == -1) {
			model.addAttribute("machine", new Machine());
			
			return "machineList :: modalAddMachine";
		} else {
			Machine machine = machineService.getByPrimaryId(id);
			
			model.addAttribute("machine", machine);
			
			return "machineList :: modalAddMachine";
		}		
	}
	
	@PostMapping("/addNotes/bind")
	public String addNotes(@RequestParam("machineId") int id,
			 Model theModel) {
		
		if (id == -1) {
			Machine machine = new Machine();
			theModel.addAttribute("machine", machine);
			
			logger.warn("MACHINEID IS -1");
			
			List<Note> notes = notesService.getNotes(machine.getMachineId());
			
			theModel.addAttribute("notes", notes);
			
			return "machineList :: modalAddNotes";
		} else {
			Machine machine = machineService.getByPrimaryId(id);
			theModel.addAttribute("machine", machine);	
			
			logger.warn("MACHINE ID IS NOT -1: " + id);
			logger.warn("MACHINE ID : " + machine.getMachineId());
			
			List<Note> notes = notesService.getNotes(machine.getMachineId());
			
			theModel.addAttribute("notes", notes);
			
			return "machineList :: modalAddNotes";
		}
		
	}
	
	@PostMapping("/addNotes")
	public ResponseEntity addNotes(@RequestParam(value="date") String date,
							@RequestParam("note") String note,
							@RequestParam("machineId") int id) {
		
		if (note != null && date != null) {
			Note noteObj = new Note();
			noteObj.setDate(date);
			noteObj.setNote(note);
			noteObj.setMachineId(new Machine(id));
			
			notesService.save(noteObj);
			
			List<String> dateAndNote = new ArrayList<String>();
			dateAndNote.add(date);
			dateAndNote.add(note);
			
			return ResponseEntity.ok(dateAndNote) ;
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/deleteNotes")
	public ResponseEntity deleteNotes(@RequestParam("noteId") int id) {
		
		if(id != 0) {
			notesService.deleteById(id);
			
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	  
	
	@PostMapping("/bind") 
		public String bindButttonText(@ModelAttribute("machine") Machine machine,
				@RequestParam("bettingAreaButtonText") String buttonText,
				@RequestParam("machineId") int id,
				Model model) {
		
			logger.warn("inside bind function " + "::: " + buttonText + "::: " + id + "::: " + machine.toString());
			
			
			Machine machine2;
			
			if(id == -1) {
				machine2 = new Machine(machine.getLsnNumber(), machine.getSerialNumber());
			} else {
				machine2 = machine;
			}
			
			logger.warn(buttonText + "  betting area button text");
							
			BettingArea temp = bettingAreaService.getByName(buttonText);
			
			logger.warn("temp to string: " + temp.toString());
			
			machine2.setBettingArea(
					temp.getId());
						
			model.addAttribute("machine", machine2);
			
			logger.warn("machine = " + machine2.toString());
			
			machineService.save(machine2);
			
			return "redirect:/bettingAreas/list";

			
		}
	
	
	

}
