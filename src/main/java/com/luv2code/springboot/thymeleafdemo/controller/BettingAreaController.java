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
@RequestMapping("/bettingAreas")
public class BettingAreaController {
	
	private BettingAreaService bettingAreaService;
	private MachineService machineService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public BettingAreaController(BettingAreaService bettingAreaService, MachineService machineService) {
		this.bettingAreaService = bettingAreaService;
		this.machineService = machineService;
	}
	
	@GetMapping("/list")
	public String bettingAreaList(Model theModel) {
		List<BettingArea> bettingAreas = bettingAreaService.getAll();
		BettingArea bettingArea = new BettingArea();
		
		List<Machine> machines = machineService.getAll();
		Machine machine = new Machine();
		
		theModel.addAttribute("bettingArea", bettingArea);
		theModel.addAttribute("bettingAreas", bettingAreas);
		theModel.addAttribute("machines", machines);
		theModel.addAttribute("machine", machine);
		
		return "machineList";
	}
	
	@PostMapping("/save")
	public String saveBettingArea(@ModelAttribute("bettingArea") BettingArea bettingArea) {
		bettingAreaService.save(bettingArea);
		
		return "redirect:/bettingAreas/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("bettingAreaId") int id) {
		
		bettingAreaService.delete(id);
		
		
		return "redirect:/bettingAreas/list";
	}

}
