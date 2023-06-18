package org.arjunaoverdrive.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.app.model.Word;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeSetDto {
    private List<Word> wordList;
    private String sourceLanguageLocale;
    private String targetLanguageLocale;

}
