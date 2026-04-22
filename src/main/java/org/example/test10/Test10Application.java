package org.example.test10;

import org.example.test10.DTO.UserDTO;
import org.example.test10.Enum.EtatCompe;
import org.example.test10.Enum.Roles;
import org.example.test10.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class Test10Application {

    public static void main(String[] args) {
        SpringApplication.run(Test10Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService){
        return args -> {
            UserDTO userDTO=new UserDTO();
            userDTO.setUsername("ADMIN");
            userDTO.setPassword("123123");
            userDTO.setAge(35);
            userDTO.setJobe("ADMINISTRATEUR DE SET WEB");
            userDTO.setRole(Roles.ADMIN);
            userDTO.setEtatCompe(EtatCompe.MARCHE);
            userService.ajouterUser(userDTO);


            userDTO.setUsername("ADMIN2");
            userDTO.setPassword("123123");
            userDTO.setAge(40);
            userDTO.setJobe("ADMINISTRATEUR2 DE SET WEB");
            userDTO.setRole(Roles.ADMIN);
            userDTO.setEtatCompe(EtatCompe.MARCHE);
            userService.ajouterUser(userDTO);
        };
    }

}
