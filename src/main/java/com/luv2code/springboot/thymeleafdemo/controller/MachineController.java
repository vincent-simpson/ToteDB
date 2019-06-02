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

import com.luv2code.springboot.thymeleafdemo.entity.Machine;
import com.luv2code.springboot.thymeleafdemo.service.MachineService;

@Controller
@RequestMapping("/machines")
public class MachineController {
	
	private MachineService machineService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public MachineController(MachineService machineService) {
		this.machineService = machineService;
	}
	
	@PostMapping("/save")
	public String saveMachine(@ModelAttribute("machine") Machine machine) {
		
		machineService.save(machine);
		
		return "redirect:/machineList";
		
	}
	
	@GetMapping("/list")
	public String machineList(Model theModel) {
		List<Machine> machines = machineService.getAll();
		Machine machine = new Machine();
		
		theModel.addAttribute("machines", machines);
		theModel.addAttribute("machine", machine);
		
		return "machineList";
	}

}
