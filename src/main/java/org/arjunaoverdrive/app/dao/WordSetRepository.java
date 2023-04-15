package org.arjunaoverdrive.app.dao;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WordSetRepository extends JpaRepository<WordSet, Integer> {

    Set<WordSet> findAllByCreatedBy(User user);
}
