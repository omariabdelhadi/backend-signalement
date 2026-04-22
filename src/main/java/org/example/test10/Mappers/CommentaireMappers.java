package org.example.test10.Mappers;

import org.example.test10.DTO.CommentaireDTO;
import org.example.test10.entities.Commentaire;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CommentaireMappers {

    public CommentaireDTO fromCommentaire(Commentaire commentaire){
        CommentaireDTO commentaireDTO=new CommentaireDTO();
        BeanUtils.copyProperties(commentaire,commentaireDTO);
        commentaireDTO.setUserId(commentaire.getUser().getId());
        commentaireDTO.setSignalementId(commentaire.getSignalement().getId());
        commentaireDTO.setNomuser(commentaire.getUser().getUsername());
        commentaireDTO.setRole(commentaire.getUser().getRole().name());

        if (commentaire.getParent()!=null){
            commentaireDTO.setParentId(commentaire.getParent().getId());
            commentaireDTO.setUserNameParent(commentaire.getParent().getUser().getUsername());
            commentaireDTO.setContenueCommentaireParent(commentaire.getParent().getContenue());
        }
        if(commentaire.getReponses()!=null){
            List<CommentaireDTO> commentaireDTOList=new ArrayList<>();
            commentaire.getReponses().forEach(r->commentaireDTOList.add(fromCommentaire(r)));
            commentaireDTO.setReponses(commentaireDTOList);
        }
        return commentaireDTO;
    }
    public Commentaire fromCommentaireDTO(CommentaireDTO commentaireDTO){
        Commentaire commentaire=new Commentaire();
        BeanUtils.copyProperties(commentaireDTO,commentaire);
        return commentaire;
    }
}
