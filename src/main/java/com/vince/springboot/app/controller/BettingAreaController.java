package com.vince.springboot.app.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.vince.springboot.app.entity.BettingArea;
import com.vince.springboot.app.entity.Machine;
import com.vince.springboot.app.service.BettingAreaService;
import com.vince.springboot.app.service.MachineService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/bettingAreas")
public class BettingAreaController {

    private BettingAreaService bettingAreaService;
    private MachineService machineService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int bettingAreaGlobal;

    @Autowired
    public BettingAreaController(BettingAreaService bettingAreaService, MachineService machineService) {
        this.bettingAreaService = bettingAreaService;
        this.machineService = machineService;
    }

    /**
     * Mapping that retrieves all of the machines and betting areas that exist in the database and binds them
     * to the {@param theModel}
     *
     * @param theModel the model to bind the machines and betting areas to.
     * @return a String that represents the view to resolve. In this case, machineList.html
     */
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

    /**
     * A mapping that is responsible for saving a betting area to the database.
     *
     * @param bettingArea the model attribute representing the betting area to be saved.
     * @return a String representing a mapping to bettingAreas/list to refresh the page.
     */
    @PostMapping("/save")
    public String saveBettingArea(@ModelAttribute("bettingArea") BettingArea bettingArea)
    {
        bettingAreaService.save(bettingArea);

        return "redirect:/bettingAreas/list";
    }

    /**
     * The mapping responsible for handling delete requests.
     *
     * @param id the id of the bettingArea to delete.
     * @return a String representing a mapping to bettingAreas/list to refresh the page
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("bettingAreaId") int id) {

        bettingAreaService.delete(id);


        return "redirect:/bettingAreas/list";
    }

    /**
     * The mapping responsible for handling edit requests. This request is made when clicking the edit button.
     * The main purpose of this mapping is to pre-fill the input boxes with the already existing data.
     *
     * @param id the id of the bettingArea that is being edited.
     * @param model the model to bind the betting area data retrieved to.
     * @return a thymeleaf fragment modal.
     */
    @PostMapping("/edit")
    public String editBettingArea(@RequestParam("bettingAreaId") int id, Model model) {

        if (id == -1) {
            model.addAttribute("bettingArea", new BettingArea());

            return "machineList :: modalAddBettingArea";
        } else {
            BettingArea bettingArea = bettingAreaService.getById(id);

            model.addAttribute("bettingArea", bettingArea);

            return "machineList :: modalAddBettingArea";
        }

    }

    /**
     * This mapping is called via an Ajax request from the function "bindParentButtonText" in extFunctions.js.
     * When the "Add Machine" button is clicked, the betting area id that we're binding the machine to is set in this
     * function below. The machine with the new betting area is then bound back to the model.
     *
     * @param bettingAreaId the id of the betting area that the machine is being added to.
     * @param machine the machine to bind to the betting area.
     * @param theModel the model that the machine is then added to.
     */
    @PostMapping("/bindToModel")
    public @ResponseBody void bindToModel(@RequestParam("bettingAreaId") int bettingAreaId,
                                          @ModelAttribute("machine") Machine machine,
                                          Model theModel)
    {

        logger.warn("bindToModel bettingAreaId = " + bettingAreaId);

        machine.setBettingArea(bettingAreaId);
        bettingAreaGlobal = bettingAreaId;

        theModel.addAttribute("machine", machine);
    }

    /**
     * This mapping is called from the modal that is responsible for handling the addition/editing of machines.
     * Its purpose is to take the id of the betting area that was added as a global variable in
     * {@link BettingAreaController#bindToModel(int, Machine, Model)} and bind it to the machine that we're saving.
     *
     * @param theMachine the model attribute representing the machine to be saved after addition/editing
     * @param request the {@link HttpServletRequest} object that is used to get the header representing the referring page
     * @return a redirect to the page that initially requested this mapping.
     */
    @GetMapping("/bind")
    public String finalSaveMachine(@ModelAttribute("machine") Machine theMachine,
                                    @RequestParam("serialNumber") int serialNumber,
                                    HttpServletRequest request)
    {
    	Machine temp = machineService.getBySerialNumber(serialNumber);
    	if (temp != null) {
        	theMachine.setMachineId(temp.getMachineId());
    	}
    	
        theMachine.setBettingArea(bettingAreaGlobal);
        theMachine.setSerialNumber(serialNumber);
        logger.warn("bind machine to save: " + theMachine.toString());
        machineService.save(theMachine);

        return "redirect:" + request.getHeader("Referer");

    }

}
