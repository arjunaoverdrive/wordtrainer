package org.arjunaoverdrive.app.DAO;

import org.arjunaoverdrive.app.model.WordStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordStatRepository extends JpaRepository<WordStat, Long> {
}
