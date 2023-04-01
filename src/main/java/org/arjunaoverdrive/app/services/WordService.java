package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;

import java.util.List;

public interface WordService {
    void deleteWord(Long id);
    void saveAll(Iterable<Word>words);
    List<Word> findAllBySet(WordSet set);

}
