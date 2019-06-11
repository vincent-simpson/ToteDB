package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.BettingArea;
import com.luv2code.springboot.thymeleafdemo.entity.Machine;
import com.luv2code.springboot.thymeleafdemo.service.BettingAreaService;
import com.luv2code.springboot.thymeleafdemo.service.MachineService;

@Controller
@RequestMapping("/machines")
public class MachineController {
	
	private MachineService machineService;
	private BettingAreaService bettingAreaService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public MachineController(MachineService machineService, BettingAreaService bettingAreaService) {
		this.machineService = machineService;
		this.bettingAreaService = bettingAreaService;
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
		
		machine2.setId(id);
		
		model.addAttribute("machine", machine2);
		
		machineService.save(machine);
		
		return "redirect:/bettingAreas/list";
		
	}
	
	@GetMapping("/list")
	public String machineList(Model theModel) {
		List<Machine> machines = machineService.getAll();
		
		theModel.addAttribute("machines", machines);
		theModel.addAttribute("machine", new Machine());
		
		return "machineList";
	}
	
	@GetMapping("/delete")
	public String deleteMachine(@RequestParam("machineId") int id) {
		
		machineService.delete(id);
		
		return "redirect:/bettingAreas/list";
	}
	
	@PostMapping("/edit")
	public String editMachine(@RequestParam("machineId") int id, Model model) {
		
		if(id == -1) {
			model.addAttribute("machine", new Machine());
			
			return "machineList :: modalAddMachine";
		} else {
			Machine machine = machineService.getByPrimaryId(id);
			
			model.addAttribute("machine", machine);
			
			return "machineList :: modalAddMachine";
		}
		
		
	}
	
	@PostMapping("/bind") 
		public String bindButttonText(@ModelAttribute("machine") Machine machine,
				@RequestParam("bettingAreaButtonText") String buttonText,
				@RequestParam("machineId") int id) {
		
			logger.warn("inside bind function " + "::: " + buttonText + "::: " + id + "::: " + machine.toString());
			
//			if(id != -1) {
//				machine = machineService.getByPrimaryId(id);
//			} else {
//				machine = new Machine();
//			}
			
			BettingArea bettingArea = bettingAreaService.getByName(buttonText);
						
			machine.setBettingArea(bettingArea.getId());
			
			logger.warn("machine = " + machine.toString());
			logger.warn("betting area = " + bettingArea.toString());
			
			machineService.save(machine);
			
			return "redirect:/bettingAreas/list";

			
		}
	

}
