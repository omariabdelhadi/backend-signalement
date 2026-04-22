package org.example.test10.Services;

import org.example.test10.DTO.UserDTO;
import org.example.test10.Enum.EtatCompe;
import org.example.test10.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDTO> afficherUsers();
    void deleteUser(Long id);
    boolean ajouterUser(UserDTO userDTO);
    User chercherUser(UserDTO userDTO);
    UserDTO getUser(Long id);
    void deleteImage(Long id);
    void modifierUser(UserDTO userDTO, MultipartFile file) throws IOException;
    List<UserDTO> listUsersEtat(EtatCompe etatCompe);

}
