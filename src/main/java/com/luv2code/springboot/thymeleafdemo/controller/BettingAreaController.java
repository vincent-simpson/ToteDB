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

import com.luv2code.springboot.thymeleafdemo.entity.BettingArea;
import com.luv2code.springboot.thymeleafdemo.service.BettingAreaService;

@Controller
@RequestMapping("/bettingAreas")
public class BettingAreaController {
	
	private BettingAreaService bettingAreaService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public BettingAreaController(BettingAreaService bettingAreaService) {
		this.bettingAreaService = bettingAreaService;
	}
	
	@GetMapping("/list")
	public String bettingAreaList(Model theModel) {
		List<BettingArea> bettingAreas = bettingAreaService.getAll();
		BettingArea bettingArea = new BettingArea();
		
		theModel.addAttribute("bettingArea", bettingArea);
		theModel.addAttribute("bettingAreas", bettingAreas);
		
		return "machineList";
	}
	
	@PostMapping("/save")
	public String saveBettingArea(@ModelAttribute("bettingArea") BettingArea bettingArea) {
		bettingAreaService.save(bettingArea);
		
		return "redirect:/machineList";
	}

}
