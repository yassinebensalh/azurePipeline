package com.example.stationski.services;

import com.example.stationski.entities.Moniteur;
import com.example.stationski.entities.MoniteurDTO;
import com.example.stationski.repositories.MoniteurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MoniteurServiceImpl implements IMoniteurService{

    @Autowired
    MoniteurRepository moniteurRepository;
    @Override
    public List<Moniteur> retrieveAllMoniteurs() {

        return moniteurRepository.findAll();
    }

    @Override
    public Moniteur addMoniteur(MoniteurDTO mDto) {
        return moniteurRepository.save(new Moniteur(1 ,mDto.getNumMoniteur(), mDto.getNomM(), mDto.getPrenomM(), mDto.getDateRecru(), mDto.getPrime()));
    }

    @Override
    public Moniteur updateMoniteur(Integer moniteurId, MoniteurDTO mDto) {
        Moniteur moniteurExistant = moniteurRepository.findMoniteurByIdMoniteur(moniteurId);
        moniteurExistant.setNumMoniteur(mDto.getNumMoniteur());
        moniteurExistant.setNomM(mDto.getNomM());
        moniteurExistant.setPrenomM( mDto.getPrenomM());
        moniteurExistant.setDateRecru(mDto.getDateRecru());
        moniteurExistant.setPrime(mDto.getPrime());
        return moniteurRepository.save(moniteurExistant);
    }

    @Override
    public Moniteur retrieveMoniteur(Integer idMoniteur) {
        Optional<Moniteur> value = moniteurRepository.findById(idMoniteur);
        return value.orElse(null);
    }

    @Override
    public void deleteMoniteur(Integer idMoniteur) {
  moniteurRepository.deleteById(idMoniteur);
    }



}