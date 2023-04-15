package org.arjunaoverdrive.app.web.dto.statistics;

import java.util.List;

public class WordSetDetailedStatsDto {
    private Integer setId;
    private String name;
    private List<WordStatsDto> wordStatsDtos;

    public WordSetDetailedStatsDto() {
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WordStatsDto> getWordStatsDtos() {
        return wordStatsDtos;
    }

    public void setWordStatsDtos(List<WordStatsDto> wordStatsDtos) {
        this.wordStatsDtos = wordStatsDtos;
    }
}
