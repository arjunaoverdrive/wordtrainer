package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;

import java.util.List;

public interface WordSetService {

    WordSet findById(Integer id);
    List<WordSet> findAll();
    void save(WordSet wordSet);
    void deleteSet(Integer id);

    List<WordSet> findAllRecent();

    List<Word> getWordList(Integer id);

    boolean update(WordSet wordSet);

    String getSetNameById(Integer setId);
}
