package org.example.test10.Web;

import org.example.test10.Enum.EtatCompe;
import org.example.test10.Enum.Statue;
import org.example.test10.Repository.UserRepository;
import org.example.test10.entities.signalement;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import jakarta.servlet.http.HttpSession;
import org.example.test10.DTO.UserDTO;
import org.example.test10.Services.UserService;
import org.example.test10.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/users")
    public ResponseEntity<?> ListUsers(HttpSession session){
        String role=(String) session.getAttribute("role");
        if (role==null) return ResponseEntity.status(401).body(null);
        if(!role.equals("ADMIN")) return ResponseEntity.status(403).body(null);
        return ResponseEntity.ok(userService.afficherUsers());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> user(@PathVariable Long id,HttpSession session){
        String role=(String) session.getAttribute("role");
        if (role==null) return ResponseEntity.status(401).body(null);
        UserDTO userDTO=userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        if (!role.equals("ADMIN") && !id.equals(Id)) return ResponseEntity.status(403).body(null);
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/inscription")
    public ResponseEntity<?> ajouterUser(@RequestBody UserDTO userDTO){
        boolean b=userService.ajouterUser(userDTO);
        if(b){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(409).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> chercherUser(@RequestBody UserDTO userDTO, HttpSession session){
        User user=userService.chercherUser(userDTO);
        if(user==null){
            return ResponseEntity.status(404).body(null);
        }
        session.setAttribute("role",user.getRole().name());
        session.setAttribute("id",user.getId());
        return ResponseEntity.ok(Map.of("role",user.getRole().name(),"id",user.getId(),"etatCompte",user.getEtatCompe().name(),"user",user.getUsername()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        session.invalidate();
        return ResponseEntity.ok(null);
    }
    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName,HttpSession session) throws MalformedURLException {
        String role =(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Path path=Paths.get("Uploads/"+fileName);
        Resource resource=new UrlResource(path.toUri());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }
    @PostMapping("/modifierUser")
    public ResponseEntity<?> modifierUser(@RequestParam("user")String user,@RequestParam(value = "image",required = false)MultipartFile file,HttpSession session) throws IOException {
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        ObjectMapper mapper=new ObjectMapper();
        UserDTO userDTO=mapper.readValue(user,UserDTO.class);
        Long id=(Long) session.getAttribute("id");
        if(!id.equals(userDTO.getId())) return ResponseEntity.status(403).body(null);
        userService.modifierUser(userDTO,file);
        return ResponseEntity.ok(null);
    }
    @PutMapping("/modifierEtatCompte/{id}/{etatCompte}")
    public ResponseEntity<?> modifierEtatCompte(@PathVariable Long id, @PathVariable EtatCompe etatCompte, HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        if(!role.equals("ADMIN")) return ResponseEntity.status(403).body(null);
        User user=userRepository.findById(id).orElse(null);
        user.setEtatCompe(etatCompte);
        userRepository.save(user);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/filterEtat/{Etat}")
    public ResponseEntity<?> signalementsS(@PathVariable EtatCompe Etat, HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        if(!role.equals("ADMIN")) return ResponseEntity.status(401).body(null);
        return ResponseEntity.ok(userService.listUsersEtat(Etat));
    }

    @DeleteMapping("/DeleteImageProf")
    public ResponseEntity<?> DeleteImage(HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long Id=(Long) session.getAttribute("id");
        userService.deleteImage(Id);
        return ResponseEntity.ok(null);
    }
}
