package com.zenika.meeting_planner.Controllers;

import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("api/salles")
public class SalleController {
    private final SalleService salleService;

    @Autowired
    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping
    public List<Salle> getSalles(){
        return this.salleService.getSalles();
    }

    @GetMapping("/generated")
    public List<Salle> generateSalles(){
        return this.salleService.generateSalles();
    }
}
