package com.example.stationski.entities;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Data
@ToString
public class MoniteurDTO implements Serializable {
    private Long numMoniteur;
    private String nomM;
    private String prenomM;
    private LocalDate dateRecru;
    private float prime;

    public MoniteurDTO(Long numMoniteur,
                       String nomM,
                       String prenomM,
                       LocalDate dateRecru,
                       float prime){
        this.numMoniteur = numMoniteur;
        this.nomM = nomM;
        this.prenomM = prenomM;
        this.dateRecru = dateRecru;
        this.prime = prime;
    }
}