package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	private int test;
	
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
	public String saveBettingArea(@ModelAttribute("bettingArea") BettingArea bettingArea,
			@RequestParam("bettingAreaId2") int id) {
		bettingArea.setId(id);
		
		bettingAreaService.save(bettingArea);
		
		return "redirect:/bettingAreas/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("bettingAreaId") int id) {
		
		bettingAreaService.delete(id);
		
		
		return "redirect:/bettingAreas/list";
	}
	
	
	@PostMapping("/edit")
	public String editBettingArea(@RequestParam("bettingAreaId") int id, Model model) {
		
		if(id == -1) {
			model.addAttribute("bettingArea", new BettingArea());
			
			return "machineList :: modalAddBettingArea";
		} else {
			BettingArea bettingArea = bettingAreaService.getById(id);
			
			model.addAttribute("bettingArea", bettingArea);
			
			return "machineList :: modalAddBettingArea";
		}
		
	}
	
	@PostMapping(value="/bindToModel")
	public @ResponseBody void bindToModel(@RequestParam("bettingAreaId")int bettingAreaId, @ModelAttribute("machine") Machine machine, Model theModel) {
		
		logger.warn("bindToModel bettingAreaId = " + bettingAreaId);
		
		machine.setBettingArea(bettingAreaId);
		test = bettingAreaId;
		
		theModel.addAttribute("machine", machine);
		
	}
	
	@GetMapping("/bind")
	public String finalSaveMachine(@RequestParam("lsnNumber") int lsnNumber,
									@RequestParam("serialNumber") String serialNumber,
									@ModelAttribute("machine") Machine theMachine) {
		
		logger.warn("/bind   lsnNumber = " + lsnNumber + " :: serialNumber = " + serialNumber);
		
		theMachine.setLsnNumber(lsnNumber);
		theMachine.setSerialNumber(serialNumber);
		theMachine.setBettingArea(test);
		logger.warn("bind machine to save: " + theMachine.toString());
		machineService.save(theMachine);
		
		return "redirect:/bettingAreas/list";
		
	}

}
