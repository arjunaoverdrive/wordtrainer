package org.arjunaoverdrive.app.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetStatsDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsDto {
    private Set<WordSet> createdSets;
    private Set<WordSetStatsDto> practicedStats;
}
