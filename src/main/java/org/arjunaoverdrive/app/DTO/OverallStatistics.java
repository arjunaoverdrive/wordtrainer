package org.arjunaoverdrive.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallStatistics {
    private int setsCount;
    private SetStats avgResults;

    private List<SetStats> setsStatsList;
}
