package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.ResultDto;
import org.arjunaoverdrive.app.DTO.WordRes;
import org.arjunaoverdrive.app.services.SetResultService;
import org.arjunaoverdrive.app.services.SetResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SetResultController {

    private final SetResultService service;

    @Autowired
    public SetResultController(SetResultServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/results/save/{id}")
    public void saveResults(@PathVariable("id") Integer id, @RequestBody ResultDto result) {
        service.save(id, result);
    }

    @PostMapping("/results/mistaken")
    public void updateWordsRates(@RequestBody List<WordRes> wordResultsList){
        service.updateWordsRates(wordResultsList);
    }
}
