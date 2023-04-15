package org.arjunaoverdrive.app.dao;

import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findAllByWordSet(WordSet set);
}
