package org.example.test10.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.test10.Enum.Statue;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class signalementDTO {

    private Long id;
    private String titre;
    private String description;
    private String image;
    private String Localisation;
    private Statue statue;
    private Long userId;
    private Date date;
}
