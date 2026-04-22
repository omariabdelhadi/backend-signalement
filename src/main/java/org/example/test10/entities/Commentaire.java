package org.example.test10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenue;
    private Date date;

    @ManyToOne
    @JsonIgnore
    private signalement signalement;

    @ManyToOne
    private User user;

    @ManyToOne
    private Commentaire parent;

    @OneToMany(mappedBy = "parent")
    private List<Commentaire> reponses;

    private Integer likes;
    private List<Long> UserIdLike;
    private List<Long> AdminIdLike;

}
