package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.web.DTO.ResultDto;
import org.arjunaoverdrive.app.web.DTO.WordRes;
import org.arjunaoverdrive.app.services.statistics.SetResultService;
import org.arjunaoverdrive.app.services.statistics.SetResultServiceImpl;
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
    @PostMapping("/results/save/")
    public void saveResults(@RequestBody ResultDto result) {
        service.save( result);
    }

    @PreAuthorize("hasAuthority('set:write')")
    @PostMapping("/results/mistaken")
    public void updateWordsRates(@RequestBody List<WordRes> wordResultsList){
        service.updateWordsRates(wordResultsList);
    }
}
