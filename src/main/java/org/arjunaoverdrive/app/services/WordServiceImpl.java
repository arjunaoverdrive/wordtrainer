package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.dao.WordRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void saveAll(Iterable<Word> words) {
        this.wordRepository.saveAll(words);
        log.info("saving all words");
    }

    @Override
    public List<Word> findAllBySet(WordSet set) {
        return this.wordRepository.findAllByWordSet(set);
    }

}
