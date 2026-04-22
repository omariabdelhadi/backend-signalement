package org.example.test10.Mappers;

import org.example.test10.DTO.signalementDTO;
import org.example.test10.Repository.UserRepository;
import org.example.test10.entities.User;
import org.example.test10.entities.signalement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class signalementMappers {
    @Autowired
    private UserRepository userRepository;

    public signalementDTO fromSignalement(signalement signalement){
        signalementDTO signalementDTO=new signalementDTO();
        BeanUtils.copyProperties(signalement,signalementDTO);
        signalementDTO.setUserId(signalement.getUser().getId());
        return signalementDTO;
    }

    public signalement fromSignalementDTO(signalementDTO signalementDTO){
        signalement signalement=new signalement();
        BeanUtils.copyProperties(signalementDTO,signalement);
        User user=new User();
        user=userRepository.findById(signalementDTO.getUserId()).orElse(null);
        signalement.setUser(user);
        return signalement;
    }
}
