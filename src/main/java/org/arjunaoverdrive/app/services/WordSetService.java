package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;

import java.util.List;
import java.util.Set;

public interface WordSetService {

    WordSet findById(Integer id);

    List<WordSet> findAll();

    void save(WordSet wordSet, User user);

    boolean update(WordSet wordSet, User user);

    void deleteSet(Integer id);

    List<WordSet> findAllRecent(User user);

    List<Word> getWordList(Integer id);

    Set<WordSet> findAllByCreatedBy(User user);
}
