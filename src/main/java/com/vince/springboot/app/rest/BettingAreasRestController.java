package com.vince.springboot.app.rest;

import com.vince.springboot.app.entity.BettingArea;
import com.vince.springboot.app.service.BettingAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bettingAreas")
public class BettingAreasRestController {

    private BettingAreaService bettingAreaService;

    @Autowired
    public BettingAreasRestController(BettingAreaService bettingAreaService) {
        this.bettingAreaService = bettingAreaService;
    }

    @GetMapping("/{id}")
    public BettingArea getByPrimaryId(@PathVariable int id) {
        return this.bettingAreaService.getById(id);
    }

}
