package org.example.test10.Web;


import jakarta.servlet.http.HttpSession;
import org.example.test10.DTO.signalementDTO;
import org.example.test10.Enum.Statue;
import org.example.test10.Repository.SignalementRepository;
import org.example.test10.Repository.UserRepository;
import org.example.test10.Services.signalementService;
import org.example.test10.entities.User;
import org.example.test10.entities.signalement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController

public class signalementController {
    @Autowired
    private signalementService signalementService;
    @Autowired
    private SignalementRepository signalementRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signalementsUser/{id}")
    public ResponseEntity<?> signalementsUser(@PathVariable Long id,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null){
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok().body(signalementService.listsignalementUser(id));
    }

    @GetMapping("/signalementsUserDetils/{id}")
    public ResponseEntity<?> signalementsUserDetils(@PathVariable Long id){
        return ResponseEntity.ok().body(signalementService.signalementUser(id));
    }
    @GetMapping("/signalements")
    public ResponseEntity<?> signalements(){
        return ResponseEntity.ok().body(signalementService.listsignalement());
    }

    @GetMapping("/ImageS/{fileName}")
    public ResponseEntity<?> ImageS(@PathVariable String fileName,HttpSession session) throws MalformedURLException {
        String role=(String) session.getAttribute("role");
        Path path= Paths.get("/UploadSImage/"+fileName);
        Resource resource=new UrlResource(path.toUri());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @PostMapping("/ajouterSignalement")
    public ResponseEntity<?> ajouterSignalement(@RequestParam("imageS")MultipartFile file,@RequestParam("signalementDTO")String signalementStringDTO,HttpSession session) throws IOException {
        String role=(String) session.getAttribute("role");
        if (role==null){
            return ResponseEntity.status(401).body(null);
        }
        Long id=(Long) session.getAttribute("id");
        User user=userRepository.findById(id).orElse(null);
        if(user.getEtatCompe().name().equals("Bloque")) return ResponseEntity.status(403).body(null);
        ObjectMapper mapper=new ObjectMapper();
        signalementDTO signalementDTO=mapper.readValue(signalementStringDTO,signalementDTO.class);
        signalementService.ajouterS(id,file,signalementDTO);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/modifierS/{id}")
    public ResponseEntity<?> ModifierS(@PathVariable Long id,@RequestParam(value = "imageS",required = false)MultipartFile file,@RequestParam("signalement")String signalementString,HttpSession session) throws IOException {
        String role=(String) session.getAttribute("role");
        if (role==null){
            return ResponseEntity.status(401).body(null);
        }
        Long idU=(Long) session.getAttribute("id");
        ObjectMapper mapper=new ObjectMapper();
        signalement signalement=signalementRepository.findById(id).orElse(null);
        signalementDTO signalementDTO=mapper.readValue(signalementString,signalementDTO.class);
        if (!idU.equals(signalement.getUser().getId())) return ResponseEntity.status(403).body(null);
        signalementDTO.setId(id);
        signalementDTO.setUserId(idU);
        signalementService.ajouterS(idU,file,signalementDTO);
        return ResponseEntity.ok(null);
    }
    @DeleteMapping("/deleteS/{id}")
    public ResponseEntity<?> deleteS(@PathVariable Long id,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long idU=(Long) session.getAttribute("id");
        signalement signalement=signalementRepository.findById(id).orElse(null);
        if(role.equals("ADMIN") || idU.equals(signalement.getUser().getId())){
            signalementService.deleteS(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(403).body(null);
    }
    @GetMapping("/signalementsUS/{statue}")
    public ResponseEntity<?> signalementsUS(@PathVariable Statue statue, HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        Long id=(Long) session.getAttribute("id");
        return ResponseEntity.ok(signalementService.listBystatueUser(statue,id));
    }
    @GetMapping("/signalementsS/{statue}")
    public ResponseEntity<?> signalementsS(@PathVariable Statue statue, HttpSession session){
        String role=(String) session.getAttribute("role");
        return ResponseEntity.ok(signalementService.listBystat(statue));
    }
    @PutMapping("/modifierStatue/{id}/{statue}")
    public ResponseEntity<?> modifierStatue(@PathVariable Long id,@PathVariable Statue statue,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        if(!role.equals("ADMIN")) return ResponseEntity.status(403).body(null);
        signalement signalement=signalementRepository.findById(id).orElse(null);
        signalement.setStatue(statue);
        signalementRepository.save(signalement);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/SignalementCommentaire/{idc}")
    public ResponseEntity<?> SignalementCommentaire(@PathVariable Long idc,HttpSession session){
        String role=(String) session.getAttribute("role");
        if(role==null) return ResponseEntity.status(401).body(null);
        signalementDTO signalementDTO=signalementService.signalementCommentaire(idc);
        return ResponseEntity.ok(signalementDTO);
    }
}
