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
	public String saveMachine(@ModelAttribute("machine") Machine machine, @RequestParam("chooseBettingArea") String bettingArea) {
		
		logger.info(bettingArea);
		
		BettingArea temp = bettingAreaService.getById(Integer.parseInt(bettingArea));
		
		machine.setBettingArea(
				temp.getId());
		
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

}
