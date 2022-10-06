package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void deleteWord(Long id) {
        Word word = this.wordRepository.findById(id).get();
        this.wordRepository.delete(word);
        log.info("delete word " + id);
    }
}
