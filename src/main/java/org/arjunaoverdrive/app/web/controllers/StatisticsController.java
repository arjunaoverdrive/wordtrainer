//package org.arjunaoverdrive.app.web.controllers;
//
//import org.arjunaoverdrive.app.model.User;
//import org.arjunaoverdrive.app.services.user.UserService;
//import org.arjunaoverdrive.app.web.DTO.OverallStatistics;
//import org.arjunaoverdrive.app.services.statistics.StatisticsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/api/v1/statistics")
//public class StatisticsController {
//
//    private final StatisticsService statisticsService;
//    private final UserService userService;
//
//    @Autowired
//    public StatisticsController(StatisticsService statisticsService, UserService userService) {
//        this.statisticsService = statisticsService;
//        this.userService = userService;
//    }
//
//    @ModelAttribute("user")
//    public User user(){
//        return userService.getUserFromSecurityContext();
//    }
//
//    @PreAuthorize("hasAuthority('set:read')")
//    @GetMapping
//    public String getOverallStatistics(Model model){
//        OverallStatistics stats = statisticsService.getOverallStatistics();
//        model.addAttribute("stats", stats);
//        return "/statistics";
//    }
//
//    @PreAuthorize("hasAuthority('set:read')")
//    @GetMapping("/{setId}")
//    public String getSetStatistics(@PathVariable("setId") Integer setId, Model model){
//        model.addAttribute("setStats", statisticsService.getSetStatistics(setId));
//        model.addAttribute("words", statisticsService.getWordsStatistics(setId));
//        return "/set_stats";
//    }
//}
