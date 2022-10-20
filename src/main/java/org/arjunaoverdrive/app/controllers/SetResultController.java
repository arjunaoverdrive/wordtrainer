package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.ResultDto;
import org.arjunaoverdrive.app.services.SetResultService;
import org.arjunaoverdrive.app.services.SetResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SetResultController {

    private final SetResultService service;

    @Autowired
    public SetResultController(SetResultServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/results/save/{id}")
    public void saveResults(@PathVariable("id")Integer id, @RequestBody ResultDto result){
      log.info(result.toString());
        service.save(id, result);
    }
}
