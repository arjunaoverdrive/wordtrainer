package org.arjunaoverdrive.app.DAO;

import org.arjunaoverdrive.app.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
