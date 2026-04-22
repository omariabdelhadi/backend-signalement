package org.example.test10.Web;


import jakarta.servlet.http.HttpSession;
import org.example.test10.DTO.CommentaireDTO;
import org.example.test10.DTO.signalementDTO;
import org.example.test10.Repository.CommentaireRepository;
import org.example.test10.Repository.UserRepository;
import org.example.test10.Services.CommentaireService;
import org.example.test10.Services.signalementService;
import org.example.test10.entities.Commentaire;
import org.example.test10.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentaireRepository commentaireRepository;

    @GetMapping("/listCommentaireSignalement/{id}")
    public List<CommentaireDTO> listCommentaireSignalement(@PathVariable Long id){
        return commentaireService.listCommentaireSignalement(id);
    }

    @GetMapping("/listCommentaireUser/{id}")
    public ResponseEntity<?> listCommentaireUser(@PathVariable Long id, HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        if(!id.equals(Id) && !role.equals("ADMIN")) return ResponseEntity.status(403).body(null);
        return ResponseEntity.ok(commentaireService.listCommentaireUser(id));
    }

    @PostMapping("/ajouterCommentaire/{idS}")
    public ResponseEntity<?> ajouterCommentaire(@PathVariable Long idS, @RequestBody CommentaireDTO commentaireDTO,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        User user=userRepository.findById(Id).orElse(null);
        if(user.getEtatCompe().name().equals("Bloque")) return ResponseEntity.status(403).body(null);
        commentaireService.ajouterCommentaire(commentaireDTO,idS,Id);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/repondreCommentaire")
    public ResponseEntity<?> repondreCommentaire(@RequestBody CommentaireDTO commentaireDTO,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        User user=userRepository.findById(Id).orElse(null);
        if(user.getEtatCompe().name().equals("Bloque")) return ResponseEntity.status(403).body(null);
        commentaireService.ajouterReponse(commentaireDTO,Id);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/DeleteCommentaire/{idC}")
    public ResponseEntity<?> DeleteCommentaire(@PathVariable Long idC,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        Commentaire commentaire=commentaireService.getCommentaire(idC);
        if(!role.equals("ADMIN") && !Id.equals(commentaire.getUser().getId())) return ResponseEntity.status(403).body(null);
        commentaireService.deleteCommentaire(idC);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/Commentaires")
    public ResponseEntity<?> Commentaires(HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        if(!role.equals("ADMIN")) return ResponseEntity.status(403).body(null);
        return ResponseEntity.ok(commentaireService.listCommentaire());
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?> like(@PathVariable Long id,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        commentaireService.ajouerLike(id,Id);
        return ResponseEntity.ok(null);
    }
}
