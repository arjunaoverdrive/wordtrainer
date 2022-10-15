package org.arjunaoverdrive.app.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultDto {

    private final List<Map<String, List<String>>> res;

    public ResultDto(List<Map<String, List<String>>> res) {
        this.res = res;
    }

    public List<WordRes> getWordResults() {
        List<WordRes> wordResults = new ArrayList<>();

        for (Map<String, List<String>> m : res) {
            int attempts = Integer.parseInt(m.keySet().stream().findFirst().get());
            m.values()
                    .forEach(v -> {
                        v.forEach(s ->
                                wordResults.add(new WordRes(attempts, s))
                        );
                    });
        }
        return wordResults;
    }
}
