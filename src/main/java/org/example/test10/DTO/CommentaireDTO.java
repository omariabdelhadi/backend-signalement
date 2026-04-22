package org.example.test10.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentaireDTO {
    private Long id;
    private String contenue;
    private String ContenueCommentaireParent;
    private Date date;
    private Long signalementId;
    private Long UserId;
    private String nomuser;
    private String role;
    private List<CommentaireDTO> reponses;
    private Long parentId;
    private String userNameParent;
    private Integer likes;
    private List<Long> UserIdLike;
    private List<Long> AdminIdLike;
}
