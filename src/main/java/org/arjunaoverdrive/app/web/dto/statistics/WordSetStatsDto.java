package org.arjunaoverdrive.app.web.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordSetStatsDto {
    private long id;
    private String setName;
    private String sourceLanguage;
    private String targetLanguage;

    private float progressSource;
    private float progressTarget;


}
