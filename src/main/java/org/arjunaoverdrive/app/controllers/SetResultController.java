package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.ResultDto;
import org.arjunaoverdrive.app.services.SetResultService;
import org.arjunaoverdrive.app.services.SetResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SetResultController {

    private final SetResultService service;

    @Autowired
    public SetResultController(SetResultServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/results/save/{id}")
    public void saveResults(@PathVariable("id")Integer id, @RequestBody List<Map<String,List<String>>> res){
        ResultDto result = new ResultDto(res);
        service.save(id, result);
    }
}
