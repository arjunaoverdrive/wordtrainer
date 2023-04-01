package org.arjunaoverdrive.app.DAO;

import org.arjunaoverdrive.app.model.WordSetStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordSetStatsRepository extends JpaRepository<WordSetStats, Long> {
}
