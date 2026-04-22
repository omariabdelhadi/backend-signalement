package org.example.test10.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.test10.Enum.EtatCompe;
import org.example.test10.Enum.Roles;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Roles role;
    private String imageProfile;
    private Integer age;
    private EtatCompe etatCompe;
    private String jobe;
}
