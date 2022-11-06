package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.ResultDto;
import org.arjunaoverdrive.app.DTO.WordRes;
import org.arjunaoverdrive.app.services.SetResultService;
import org.arjunaoverdrive.app.services.SetResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SetResultController {

    private final SetResultService service;

    @Autowired
    public SetResultController(SetResultServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/results/save/{id}")
    public void saveResults(@PathVariable("id") Integer id, @RequestBody ResultDto result) {
        log.info("saving results set " + id);
        service.save(id, result);
    }

    @PostMapping("/results/mistaken")
    public void updateWordsRates(@RequestBody List<WordRes> wordResultsList){
        log.info("updating words error rates");
        service.updateWordsRates(wordResultsList);
    }
}
