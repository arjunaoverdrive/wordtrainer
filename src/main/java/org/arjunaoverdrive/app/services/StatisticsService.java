package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DTO.OverallStatistics;
import org.arjunaoverdrive.app.DTO.SetStats;
import org.arjunaoverdrive.app.model.Word;

import java.util.List;

public interface StatisticsService {
    OverallStatistics getOverallStatistics();

    SetStats getSetStatistics(Integer setId);

    List<Word> getWordsStatistics(Integer setId);
}
