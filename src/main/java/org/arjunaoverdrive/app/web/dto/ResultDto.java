package org.arjunaoverdrive.app.web.dto;

import java.util.List;
import java.util.Map;

public class ResultDto {
    private final int setId;
    private final String language;
    private final Map<String, List<String>> result;

    public ResultDto(int setId, String lang, Map<String, List<String>> result) {
        this.setId = setId;
        this.language = lang;
        this.result = result;
    }

    public int getSetId() {
        return setId;
    }

    public Map<String, List<String>> getResult() {
        return result;
    }

    public String getLanguage() {
        return language;
    }
}
