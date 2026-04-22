package org.example.test10.Services;

import jakarta.transaction.Transactional;
import org.example.test10.DTO.signalementDTO;
import org.example.test10.Enum.Statue;
import org.example.test10.Repository.CommentaireRepository;
import org.example.test10.Repository.SignalementRepository;
import org.example.test10.Repository.UserRepository;
import org.example.test10.Mappers.signalementMappers;
import org.example.test10.entities.User;
import org.example.test10.entities.signalement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class signalementImplement implements signalementService{

    @Autowired
    private SignalementRepository signalementRepository;
    @Autowired
    private signalementMappers signalementMappers;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentaireRepository commentaireRepository;

    @Override
    public List<signalementDTO> listsignalementUser(Long id) {
        List<signalement> signalementList=signalementRepository.findAllByUserId(id);
        List<signalementDTO> signalementDTOList=new ArrayList<>();
        signalementList.forEach(s->signalementDTOList.add(signalementMappers.fromSignalement(s)));
        return signalementDTOList;
    }

    @Override
    public List<signalementDTO> listsignalement() {
        List<signalement> signalementList=signalementRepository.findAll();
        List<signalementDTO> signalementDTOList=new ArrayList<>();
        signalementList.forEach(s->signalementDTOList.add(signalementMappers.fromSignalement(s)));
        return signalementDTOList;
    }

    @Override
    public signalementDTO signalementUser(Long id) {
        signalement signalement=signalementRepository.findById(id).orElse(null);
        signalementDTO signalementDTO=new signalementDTO();
        signalementDTO=signalementMappers.fromSignalement(signalement);
        return signalementDTO;
    }

    @Override
    public void ajouterS(Long id,MultipartFile file, signalementDTO signalementDTO) throws IOException {
        User user=userRepository.findById(id).orElse(null);
        signalement signalement=signalementMappers.fromSignalementDTO(signalementDTO);

        if(file!=null){
            String fileName="photo_"+id+"_"+file.getOriginalFilename();
            Path path= Paths.get("/UploadSImage/"+fileName);
            Files.createDirectories(path.getParent());
            Files.write(path,file.getBytes());
            signalement.setImage(fileName);
        }
        
        signalement.setUser(user);
        signalementRepository.save(signalement);
    }

    @Override
    public void deleteS(Long id) {
        commentaireRepository.deleteBySignalementId(id);
        signalementRepository.deleteById(id);
    }

    @Override
    public List<signalementDTO> listBystatueUser(Statue statue,Long id) {
        List<signalement> signalementList=new ArrayList<>();
        signalementList=signalementRepository.findByStatueAndUserId(statue,id);
        List<signalementDTO> signalementDTOList=new ArrayList<>();
        signalementList.forEach(s->signalementDTOList.add(signalementMappers.fromSignalement(s)));
        return signalementDTOList;
    }

    @Override
    public List<signalementDTO> listBystat(Statue statue) {
        List<signalement> signalementList=new ArrayList<>();
        signalementList=signalementRepository.findByStatue(statue);
        List<signalementDTO> signalementDTOList=new ArrayList<>();
        signalementList.forEach(s->signalementDTOList.add(signalementMappers.fromSignalement(s)));
        return signalementDTOList;
    }

    @Override
    public signalementDTO signalementCommentaire(Long id) {
        signalement signalement=signalementRepository.findByCommentairesId(id);
        signalementDTO signalementDTO=signalementMappers.fromSignalement(signalement);
        return signalementDTO;
    }
}
