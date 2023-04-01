package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.web.DTO.ResultDto;

public interface WordStatService {
    void save(ResultDto resultDto, WordSetStats wordSetStats);
}
