package org.arjunaoverdrive.app.DAO;

import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findAllByWordSet(WordSet set);

    List<Word> findAllByWordSetAndSrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(WordSet set, float i, int timesPracticed);

    List<Word> findAllByWordSetAndTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(WordSet set, float i, int timesPracticed);

    List<Word> findAllBySrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(float i, int timesPracticed);

    List<Word> findAllByTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(float i, int timesPracticed);
}
