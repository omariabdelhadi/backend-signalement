package org.example.test10.Services;

import org.example.test10.DTO.signalementDTO;
import org.example.test10.Enum.Statue;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface signalementService {
    List<signalementDTO> listsignalementUser(Long id);
    List<signalementDTO> listsignalement();
    signalementDTO signalementUser(Long id);
    void ajouterS(Long id,MultipartFile file,signalementDTO signalementDTO) throws IOException;
    void deleteS(Long id);
    List<signalementDTO> listBystatueUser(Statue statue,Long id);
    List<signalementDTO> listBystat(Statue statue);
    signalementDTO signalementCommentaire(Long id);
}
