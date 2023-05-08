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

    private Timestamp lastPracticedSourceLang;
    private Timestamp lastPracticedTargetLang;

    private float bestResultSourceLang;
    private float bestResultTargetLang;

    private float averageResultSourceLang;
    private float averageResultTargetLang;

    private int timesPracticedSource;
    private int timesPracticedTarget;
}
