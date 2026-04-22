package org.example.test10.Services;

import jakarta.transaction.Transactional;
import org.example.test10.DTO.UserDTO;
import org.example.test10.Enum.EtatCompe;
import org.example.test10.Repository.SignalementRepository;
import org.example.test10.Repository.UserRepository;
import org.example.test10.Mappers.UserMappers;
import org.example.test10.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserImplement implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMappers userMappers;
    @Autowired
    SignalementRepository signalementRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public List<UserDTO> afficherUsers() {
        List<User> users=userRepository.findAll();
        List<UserDTO> userDTOS=new ArrayList<>();
        users.forEach(user -> userDTOS.add(userMappers.fromUser(user)));
        return userDTOS;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean ajouterUser(UserDTO userDTO) {
        User user=userRepository.findByUsername(userDTO.getUsername());
        if (user==null){
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(userMappers.fromUserDTO(userDTO));
            return true;
        }
        return false;
    }

    @Override
    public User chercherUser(UserDTO userDTO) {
        User user= userRepository.findByUsername(userDTO.getUsername());
        if (user==null) return null;
        if (!passwordEncoder.matches(userDTO.getPassword(),user.getPassword())) return null;
        return user;
    }

    @Override
    public UserDTO getUser(Long id) {
        User user=userRepository.findById(id).orElse(null);
        return userMappers.fromUser(user);
    }

    @Override
    public void deleteImage(Long id) {
        User user=userRepository.findById(id).orElse(null);
        user.setImageProfile(null);
        userRepository.save(user);
    }

    @Override
    public void modifierUser(UserDTO userDTO, MultipartFile file) throws IOException {
        String password=userRepository.findById(userDTO.getId()).orElse(null).getPassword();
        User user=userMappers.fromUserDTO(userDTO);
        if(userDTO.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else if (userDTO.getPassword()==null) {
            user.setPassword(password);
        }

        if(file!=null){
            String fileName="photo_U"+user.getId()+"_"+file.getOriginalFilename();
            Path path=Paths.get("Uploads/"+fileName);
            Files.createDirectories(path.getParent());
            Files.write(path,file.getBytes());
            user.setImageProfile(fileName);
        }
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> listUsersEtat(EtatCompe etatCompe) {
        List<User> userList=userRepository.findByEtatCompe(etatCompe);
        List<UserDTO> userDTOList=new ArrayList<>();
        userList.forEach(ul->userDTOList.add(userMappers.fromUser(ul)));
        return userDTOList;
    }

}
