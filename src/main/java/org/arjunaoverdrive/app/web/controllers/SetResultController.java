package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.services.statistics.SetResultService;
import org.arjunaoverdrive.app.services.statistics.SetResultServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SetResultController {

    private final SetResultService service;
    private final UserService userService;

    @Autowired
    public SetResultController(SetResultServiceImpl service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('set:write')")
    @PostMapping("/results/save/")
    public void saveResults(@RequestBody ResultDto result) {
        service.save(result, userService.getUserFromSecurityContext());
    }
}
