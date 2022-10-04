package org.arjunaoverdrive.app.DAO;

import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordSetRepository extends JpaRepository<WordSet, Integer> {
}
