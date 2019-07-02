package com.vince.springboot.app.rest;

import com.vince.springboot.app.entity.Machine;
import com.vince.springboot.app.service.MachineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineRestController {

    private MachineService machineService;

    @Autowired
    public MachineRestController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/{machineId}")
    public Machine getMachine(@PathVariable int machineId) {
        return machineService.getByPrimaryId(machineId);
    }

    @GetMapping("/getAll")
    public List<Machine> getMachines() {
        return machineService.getAll();
    }
}
