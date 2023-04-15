package org.arjunaoverdrive.app.dao;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WordSetStatsRepository extends JpaRepository<WordSetStats, Long> {
    Set<WordSetStats> findAllByPracticedBy(User user);

    List<WordSetStats> findByWordSet(WordSet ws);
}
