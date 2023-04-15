package org.arjunaoverdrive.app.web.controllers.user;

import org.arjunaoverdrive.app.services.statistics.UserStatisticsService;
import org.arjunaoverdrive.app.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserAccountController {

    private final UserService userService;
    private final UserStatisticsService userStatisticsService;

    @Autowired
    public UserAccountController(UserService userService, UserStatisticsService userStatisticsService) {
        this.userService = userService;
        this.userStatisticsService = userStatisticsService;
    }



}
