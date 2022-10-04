package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void saveWord(Integer setId) {

    }

    @Override
    public void editWord(Word word) {
        this.wordRepository.save(word);
    }

    @Override
    public void deleteWord(Long id) {
        this.wordRepository.deleteById(id);
    }
}
