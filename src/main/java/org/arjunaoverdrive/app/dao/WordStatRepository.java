package org.arjunaoverdrive.app.dao;

import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.model.WordStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WordStatRepository extends JpaRepository<WordStat, Long> {
    Set<WordStat> findByWordSetStats(WordSetStats wordSetStats);
}
