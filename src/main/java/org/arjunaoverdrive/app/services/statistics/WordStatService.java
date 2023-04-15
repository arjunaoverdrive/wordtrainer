package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.model.WordStat;
import org.arjunaoverdrive.app.web.dto.ResultDto;

import java.util.Set;

public interface WordStatService {
    void save(ResultDto resultDto, WordSetStats wordSetStats);

    Set<WordStat> findByWordSetStats(WordSetStats wss);
}
