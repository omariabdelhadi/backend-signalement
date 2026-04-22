package org.example.test10.Repository;

import org.example.test10.Enum.Statue;
import org.example.test10.entities.signalement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignalementRepository extends JpaRepository<signalement,Long> {
    List<signalement> findAllByUserId(Long id);
    List<signalement> findByStatueAndUserId(Statue statue,Long id);
    List<signalement> findByStatue(Statue statue);
    void deleteByUserId(Long id);
    signalement findByCommentairesId(Long id);
}
