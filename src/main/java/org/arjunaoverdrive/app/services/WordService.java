package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.Word;

public interface WordService {
    void saveWord(Integer setId);
    void editWord( Word word);
    void deleteWord(Long id);
}
