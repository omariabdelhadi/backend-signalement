package org.example.test10.Repository;

import org.example.test10.entities.Commentaire;
import org.example.test10.entities.signalement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {
    List<Commentaire> findBySignalementId(Long id);
    List<Commentaire> findByUserId(Long id);
    void deleteBySignalementId(Long id);
    void deleteAllByParentId(Long Id);
}
