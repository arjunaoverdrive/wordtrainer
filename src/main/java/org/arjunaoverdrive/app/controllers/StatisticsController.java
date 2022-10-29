package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.OverallStatistics;
import org.arjunaoverdrive.app.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public String getOverallStatistics(Model model){
        OverallStatistics stats = statisticsService.getOverallStatistics();
        model.addAttribute("stats", stats);
        return "/statistics";
    }
}
