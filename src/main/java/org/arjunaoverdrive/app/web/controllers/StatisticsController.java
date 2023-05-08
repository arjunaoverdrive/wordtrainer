package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.services.statistics.UserStatisticsService;
import org.arjunaoverdrive.app.services.statistics.WordSetStatsService;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetDetailedStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final UserService userService;
    private final WordSetStatsService wordSetStatsService;
    private final UserStatisticsService userStatisticsService;

    @Autowired
    public StatisticsController(UserService userService, WordSetStatsService wordSetStatsService, UserStatisticsService userStatisticsService) {
        this.userService = userService;
        this.wordSetStatsService = wordSetStatsService;
        this.userStatisticsService = userStatisticsService;
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }


    @GetMapping("/{setid}")
    public String statistics(@PathVariable("setid") Integer id, Model model){
        WordSetDetailedStatsDto wordSetDetailedStats = wordSetStatsService.getWordSetDetailedStats(id, user());
        model.addAttribute("detailed", wordSetDetailedStats);
        return "statistics";
    }

    @GetMapping("/user/{userid}")
    public String userStatisticsPage(@PathVariable("userid") Long userId,
                              Model model){
        User user = userService.getUserFromSecurityContext();
        model.addAttribute("user", user);
        model.addAttribute("userStats", userStatisticsService.getStatistics(user));
        return "user_statistics_page";
    }
}
