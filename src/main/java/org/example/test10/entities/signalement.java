package org.example.test10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.test10.Enum.Statue;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class signalement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String image;
    private String Localisation;
    @Enumerated(EnumType.STRING)
    private Statue statue;
    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "signalement",fetch = FetchType.LAZY)
    private List<Commentaire> commentaires;
    private Date date;
}
