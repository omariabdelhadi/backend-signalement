package org.example.test10.Mappers;

import org.example.test10.DTO.UserDTO;
import org.example.test10.Repository.UserRepository;
import org.example.test10.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class UserMappers {

    @Autowired
    UserRepository userRepository;
    public UserDTO fromUser(User user){
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }
    public User fromUserDTO(UserDTO userDTO){
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }

}
