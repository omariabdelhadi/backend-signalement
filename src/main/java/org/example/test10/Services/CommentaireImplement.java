package org.example.test10.Services;

import jakarta.transaction.Transactional;
import org.example.test10.DTO.CommentaireDTO;
import org.example.test10.Mappers.CommentaireMappers;
import org.example.test10.Repository.CommentaireRepository;
import org.example.test10.Repository.SignalementRepository;
import org.example.test10.Repository.UserRepository;
import org.example.test10.entities.Commentaire;
import org.example.test10.entities.User;
import org.example.test10.entities.signalement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentaireImplement implements CommentaireService{
    @Autowired
    CommentaireRepository commentaireRepository;
    @Autowired
    CommentaireMappers commentaireMappers;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SignalementRepository signalementRepository;

    @Override
    public List<CommentaireDTO> listCommentaireSignalement(Long id) {
        List<Commentaire> commentaires=commentaireRepository.findBySignalementId(id);
        List<CommentaireDTO> commentaireDTOS=new ArrayList<>();
        commentaires.forEach(c->commentaireDTOS.add(commentaireMappers.fromCommentaire(c)));
        return commentaireDTOS;
    }

    @Override
    public List<CommentaireDTO> listCommentaireUser(Long id) {
        List<Commentaire> commentaires=commentaireRepository.findByUserId(id);
        List<CommentaireDTO> commentaireDTOS=new ArrayList<>();
        commentaires.forEach(c->commentaireDTOS.add(commentaireMappers.fromCommentaire(c)));
        return commentaireDTOS;
    }

    @Override
    public void ajouterCommentaire(CommentaireDTO commentaireDTO, Long IdS, Long IdU) {
        Commentaire commentaire=commentaireMappers.fromCommentaireDTO(commentaireDTO);
        User user=userRepository.findById(IdU).orElse(null);
        signalement signalement=signalementRepository.findById(IdS).orElse(null);
        commentaire.setUser(user);
        commentaire.setSignalement(signalement);
        commentaireRepository.save(commentaire);
    }

    @Override
    public void deleteCommentaire(Long Id) {
        commentaireRepository.deleteAllByParentId(Id);
        commentaireRepository.deleteById(Id);
    }

    @Override
    public Commentaire getCommentaire(Long Idc) {
        Commentaire commentaire=commentaireRepository.findById(Idc).orElse(null);
        return commentaire;
    }

    @Override
    public List<CommentaireDTO> listCommentaire() {
        List<Commentaire> commentaires=commentaireRepository.findAll();
        List<CommentaireDTO> commentaireDTOS=new ArrayList<>();
        commentaires.forEach(c->commentaireDTOS.add(commentaireMappers.fromCommentaire(c)));
        return commentaireDTOS;
    }

    @Override
    public void ajouterReponse(CommentaireDTO commentaireDTO,Long Id) {
        Commentaire parent=commentaireRepository.findById(commentaireDTO.getParentId()).orElse(null);
        Commentaire reponse=commentaireMappers.fromCommentaireDTO(commentaireDTO);
        signalement signalement=signalementRepository.findById(parent.getSignalement().getId()).orElse(null);
        User user=userRepository.findById(Id).orElse(null);
        reponse.setUser(user);
        reponse.setParent(parent);
        reponse.setSignalement(signalement);
        parent.getReponses().add(reponse);
        commentaireRepository.save(reponse);
        commentaireRepository.save(parent);

    }

    @Override
    public boolean ajouerLike(Long id,Long Id) {

        Commentaire commentaire=commentaireRepository.findById(id).orElse(null);
        User user=userRepository.findById(Id).orElse(null);

        Integer likes=commentaire.getLikes();
        if (likes==null) likes=0;

        List<Long> UsertIdlist=commentaire.getUserIdLike();
        if (UsertIdlist==null) UsertIdlist=new ArrayList<>();

        List<Long> AdminIdlist=commentaire.getAdminIdLike();
        if (AdminIdlist==null) AdminIdlist=new ArrayList<>();

        if (user.getRole().name().equals("USER")){
            if (UsertIdlist.contains(Id)){
                likes=likes-1;
                UsertIdlist.remove(Id);
            } else if (!UsertIdlist.contains(Id)) {
                likes=likes+1;
                UsertIdlist.add(Id);
            }
            commentaire.setUserIdLike(UsertIdlist);

        } else if (user.getRole().name().equals("ADMIN")) {
            if (AdminIdlist.contains(Id)){
                likes=likes-1;
                AdminIdlist.remove(Id);
            } else if (!AdminIdlist.contains(Id)) {
                likes=likes+1;
                AdminIdlist.add(Id);
            }
            commentaire.setAdminIdLike(AdminIdlist);
        }

        commentaire.setLikes(likes);
        commentaireRepository.save(commentaire);
        return true;
    }
}
