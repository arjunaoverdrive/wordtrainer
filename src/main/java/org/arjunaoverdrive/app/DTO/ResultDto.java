package org.arjunaoverdrive.app.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultDto {

    private final boolean lang;
    private final List<Map<String, List<String>>> result;

    public ResultDto(boolean lang, List<Map<String, List<String>>> result) {
        this.lang = lang;
        this.result = result;
    }

    public List<WordRes> getWordResults() {
        List<WordRes> wordResults = new ArrayList<>();
        createWordObjects(wordResults);
        return wordResults;
    }

    private void createWordObjects(List<WordRes> wordResults) {
        for (Map<String, List<String>> m : result) {
            int attempts = Integer.parseInt(m.keySet().stream().findFirst().get());
            m.values()
                    .forEach(v -> {
                        v.forEach(s ->
                                wordResults.add(new WordRes(attempts, s))
                        );
                    });
        }
    }

    public boolean isLang() {
        return lang;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "originalLanguage=" + lang +
                ", res=" + result.size() +
                '}';
    }
}
