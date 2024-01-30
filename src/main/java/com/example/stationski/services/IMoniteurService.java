package com.example.stationski.services;

import com.example.stationski.entities.Moniteur;
import com.example.stationski.entities.MoniteurDTO;

import java.util.List;

public interface IMoniteurService {

    List<Moniteur> retrieveAllMoniteurs();
    Moniteur addMoniteur(MoniteurDTO mDto);
    Moniteur updateMoniteur (Integer moniteurId,MoniteurDTO mDto);
    Moniteur retrieveMoniteur (Integer idMoniteur);
    void deleteMoniteur( Integer idMoniteur);
}