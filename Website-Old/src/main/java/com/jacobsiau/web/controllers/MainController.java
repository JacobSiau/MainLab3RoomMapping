package com.jacobsiau.web.controllers;

import java.lang.invoke.MethodHandles;
import java.util.List;
import com.jacobsiau.web.models.RobotData;
import com.jacobsiau.web.models.RobotDepthData;
import com.jacobsiau.web.models.RobotDepthDataWrapper;
import com.jacobsiau.web.models.RobotImageDataWrapper;
import com.jacobsiau.web.repositories.RobotDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * MainController is the main controller of this application. 
 */
@Controller
public class MainController {

    private Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private RobotDataRepository _robotDataRepository;

    @GetMapping("/home")
    public String getHomeView() {
        LOGGER.debug("/home requested.");
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/robotdata", method = RequestMethod.POST)
    public List<RobotData> getRobotData() {
        LOGGER.debug("/robotdata requested.");
        return _robotDataRepository.getAllRobotData();
    }

    @RequestMapping(value = "/updaterobotdepthdata", method = RequestMethod.GET)
    public String updateRobotDepthData(Model model) {
        LOGGER.debug("/updaterobotdepthdata requested.");
        model.addAttribute("data", _robotDataRepository.getAllRobotDepthData());
        LOGGER.debug("Added attribute *data* to model, returning fragment...");
        return "mapping_fragment :: robot_depth_data_table";
    }

    @ResponseBody
    @RequestMapping(value = "/robotdepthdata", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<RobotDepthData> getRobotDepthData() {
        LOGGER.debug("/robotdepthdata requested.");
        return _robotDataRepository.getAllRobotDepthData();
    }

    @ResponseBody
    @RequestMapping(value = "/insertdepthdata", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertDepthData(@RequestBody RobotDepthDataWrapper data) {
        LOGGER.debug("/insertdepthdata requested.");
        try {
            _robotDataRepository.deleteAllRobotDepthData();
            _robotDataRepository.insertRobotDepthData(data);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            LOGGER.error("[ERROR] Exception caught when /insertdepthdata was requested. See stack trace below.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/insertimagedata", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertImageData(@RequestBody RobotImageDataWrapper data) {
        LOGGER.debug("/insertimagedata requested.");
        try {
            _robotDataRepository.insertRobotImageData(data);
            return ResponseEntity.ok(data);
        }
        catch (Exception e) {
            LOGGER.error("[ERROR] Exception caught when /insertdepthdata was requested. See stack trace below.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/mapping")
    public String getMappingView(Model model) {
        LOGGER.debug("/mapping requested.");
        model.addAttribute("robot_depth_data", _robotDataRepository.getAllRobotDepthData());
        LOGGER.debug("Added attribute *robot_depth_data* to model, returning *mapping* view...");
        return "mapping";
    }

    @GetMapping("/reconstruction")
    public String getReconstructionView(Model model) {
        LOGGER.debug("/reconstruction requested.");
        model.addAttribute("robot_depth_data", _robotDataRepository.getAllRobotDepthData());
        model.addAttribute("robot_image_data", _robotDataRepository.getAllRobotImageData());
        return "reconstruction";
    }

    @GetMapping("/routing")
    public String getRoutingView() {
        LOGGER.debug("/routing requested.");
        return "routing";
    }

    @GetMapping("/about")
    public String getAboutView() {
        LOGGER.debug("/about requested.");
        return "about";
    }

    @GetMapping("/ourteam")
    public String getOurTeamView() {
        LOGGER.debug("/ourteam requested.");
        return "ourteam";
    }

    @GetMapping("/contact")
    public String getContactView() {
        LOGGER.debug("/contact requested.");
        return "contact";
    }

    @GetMapping("/faq")
    public String getFaqView() {
        LOGGER.debug("/faq requested.");
        return "faq";
    }
	
}
