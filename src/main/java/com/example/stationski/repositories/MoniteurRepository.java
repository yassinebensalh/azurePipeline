package com.example.stationski.repositories;

import com.example.stationski.entities.Moniteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoniteurRepository extends JpaRepository<Moniteur,Integer> {
    Moniteur findMoniteurByIdMoniteur(Integer id);

}