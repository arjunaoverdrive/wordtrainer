package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.web.DTO.ResultDto;
import org.arjunaoverdrive.app.web.DTO.WordRes;
import org.arjunaoverdrive.app.services.SetResultService;
import org.arjunaoverdrive.app.services.SetResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class SetResultController {

    private final SetResultService service;

    @Autowired
    public SetResultController(SetResultServiceImpl service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('set:write')")
    @PostMapping("/results/save/{id}")
    public void saveResults(@PathVariable("id") Integer id, @RequestBody ResultDto result) {
        service.save(id, result);
    }

    @PreAuthorize("hasAuthority('set:write')")
    @PostMapping("/results/mistaken")
    public void updateWordsRates(@RequestBody List<WordRes> wordResultsList){
        service.updateWordsRates(wordResultsList);
    }
}
