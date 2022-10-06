package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.WordSet;

import java.util.List;

public interface WordSetService {

    WordSet findById(Integer id);
    List<WordSet> findAll();
    void save(WordSet wordSet);
    void deleteSet(Integer id);
}
