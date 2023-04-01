package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.web.DTO.OverallStatistics;
import org.arjunaoverdrive.app.web.DTO.SetStats;
import org.arjunaoverdrive.app.model.Word;

import java.util.List;

public interface StatisticsService {
    OverallStatistics getOverallStatistics();

    SetStats getSetStatistics(Integer setId);

    List<Word> getWordsStatistics(Integer setId);
}
