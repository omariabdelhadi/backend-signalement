package org.example.test10.Services;

import org.example.test10.DTO.CommentaireDTO;
import org.example.test10.entities.Commentaire;

import java.util.List;

public interface CommentaireService {
    List<CommentaireDTO> listCommentaireSignalement(Long id);
    List<CommentaireDTO> listCommentaireUser(Long id);
    void ajouterCommentaire(CommentaireDTO commentaireDTO,Long IdS,Long IdU);
    void deleteCommentaire(Long Id);
    Commentaire getCommentaire(Long Idc);
    List<CommentaireDTO> listCommentaire();
    void ajouterReponse(CommentaireDTO commentaireDTO,Long Id);
    boolean ajouerLike(Long id,Long Id);
}
