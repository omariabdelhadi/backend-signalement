package org.example.test10.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.test10.Enum.EtatCompe;
import org.example.test10.Enum.Roles;

import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer age;
    private String jobe;
    @Enumerated(EnumType.STRING)
    private EtatCompe etatCompe;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private String imageProfile;
    @OneToMany(mappedBy = "user",fetch=FetchType.LAZY)
    private List<signalement> signalementList;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Commentaire> commentaireListU;

}
