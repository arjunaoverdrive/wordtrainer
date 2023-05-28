package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.web.dto.WordSetDto;

import java.util.List;
import java.util.Set;

public interface WordSetService {

    WordSet findById(Integer id);

    List<WordSet> findAll();

    void save(WordSetDto wordSetDto, User user);

    boolean update(WordSet wordSet, User user);

    void deleteSet(Integer id, User user);

    List<WordSet> findAllRecent(User user);

    List<Word> getWordList(Integer id);

    Set<WordSet> findAllByCreatedBy(User user);
}
