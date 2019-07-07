package com.vince.springboot.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vince.springboot.app.entity.BettingArea;
import com.vince.springboot.app.entity.Machine;
import com.vince.springboot.app.entity.Note;
import com.vince.springboot.app.service.BettingAreaService;
import com.vince.springboot.app.service.MachineService;
import com.vince.springboot.app.service.NotesService;

import javax.servlet.http.HttpServletRequest;

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
	public String saveMachine(@ModelAttribute("machine") Machine machine, HttpServletRequest request,
							  @RequestParam(value = "bettingAreaName", required = false) String bettingAreaName)
	{
		logger.warn("betting area name in /save mapping: " + bettingAreaName);
		machine.setBettingArea(bettingAreaService.getByName(bettingAreaName).getId());
		machineService.save(machine);

		String s = request.getHeader("Referer");
		if(s.toLowerCase().contains("master")) {
			logger.warn("machine betting area id: " + machine.getBettingArea());
			return "redirect:/machines/machineMasterList";
		} else {
			return "redirect:/machines/list";
		}

	}

	private Machine bindMachineToModel(@ModelAttribute("machine") Machine machine, @RequestParam("machineId2") int id, @RequestParam("bettingAreaButtonText") String bettingAreaButtonText) {
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
		return machine2;
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
	public String deleteMachine(@RequestParam("machineId") int id, HttpServletRequest httpServletRequest) {

		logger.warn("http servlet request referer: " + httpServletRequest.getHeader("Referer"));
		machineService.delete(id);

		return "redirect:" + httpServletRequest.getHeader("Referer");
	}

	@RequestMapping(value = "/unbind", method = {RequestMethod.GET, RequestMethod.POST})
	public String unbindMachineFromBettingArea(@RequestParam("machineId") int id, HttpServletRequest request) {
		logger.warn("UNBIND id param: " + id);
		machineService.unbindFromBettingArea(id);

		return "redirect:" + request.getHeader("Referer");
	}

	@PostMapping("/edit")
	public String editMachine(@RequestParam("machineId") int id, Model model, HttpServletRequest request) {

		logger.warn("/edit mapping id request param = " + id);
		List<BettingArea> bettingAreas = bettingAreaService.getAll();
		model.addAttribute("bettingAreas", bettingAreas);

		Machine machine;

		if(id == -1) {
			machine = new Machine();
		} else {
			machine = machineService.getByPrimaryId(id);
		}

		model.addAttribute("machine", machine);

		String s = request.getHeader("Referer").toLowerCase();
		if(s.contains("masterlist")) {
			return "machineMasterList :: modalAddMachine";
		}
		else if(s.contains("machinelist") || s.contains("bettingAreas".toLowerCase())) {
			return "machineList :: modalAddMachine";
		}
		else {
			logger.warn("Referer is: " + s);
			throw new RuntimeException("Referer not recognized");
		}
	}

	private String getReferer(HttpServletRequest request) {
		String s = request.getHeader("Referer").toLowerCase();
		if(s.contains("masterlist")) {
			return "machineMasterList";
		}
		else if(s.contains("machinelist") || s.contains("bettingareas")) {
			return "machineList";
		}
		else {
			logger.warn("Referer is: " + s);
			throw new RuntimeException("Referer not recognized");
		}
	}

	@PostMapping("/addNotes/bind")
	public String addNotes(@RequestParam("machineId") int id,
						   Model theModel,
						   HttpServletRequest request) {
		Machine machine;
		List<Note> notes;

		if (id == -1) {
			machine = new Machine();
		} else {
			machine = machineService.getByPrimaryId(id);
		}

		theModel.addAttribute("machine", machine);
		notes = notesService.getNotes(machine.getMachineId());
		theModel.addAttribute("notes", notes);

		return getReferer(request) + " :: modalAddNotes";

	}

	@PostMapping("/addNotes")
	public ResponseEntity addNotes(@RequestParam(value="date") String date,
								   @RequestParam("note") String note,
								   @RequestParam("machineId") int id) {

		if (note != null && date != null) {
			Note noteObj = new Note();
			noteObj.setDate(date);
			noteObj.setNote(note);
			noteObj.setMachineId(machineService.getByPrimaryId(id));

			int idSaved = notesService.save(noteObj);

			List<String> dateAndNote = new ArrayList<String>();
			dateAndNote.add(date);
			dateAndNote.add(note);
			dateAndNote.add(idSaved + "");

			return ResponseEntity.ok(dateAndNote) ;
		} else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/deleteNotes")
	public ResponseEntity deleteNotes(@RequestParam("noteId") int id) {

			notesService.deleteById(id);

			return new ResponseEntity(HttpStatus.OK);
	}



	@PostMapping("/bind")
	public String bindButtonText(@ModelAttribute("machine") Machine machine,
								  @RequestParam("bettingAreaButtonText") String buttonText,
								  @RequestParam("machineId") int id,
								  Model model) {

		Machine machine2 = bindMachineToModel(machine, id, buttonText);
		model.addAttribute("machine", machine2);

		machineService.save(machine2);

		return "redirect:/bettingAreas/list";
	}

	@GetMapping("/machineMasterList")
	public String getMachineMasterList(Model model) {

		List<BettingArea> bettingAreas = bettingAreaService.getAll();
		List<Machine> masterList = machineService.getAll();

		model.addAttribute("bettingAreas", bettingAreas);
		model.addAttribute("machines", masterList);
		model.addAttribute("machine", new Machine());

		return "machineMasterList";
	}
}
