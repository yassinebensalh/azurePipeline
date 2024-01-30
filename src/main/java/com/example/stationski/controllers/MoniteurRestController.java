package com.example.stationski.controllers;

import com.example.stationski.entities.Moniteur;
import com.example.stationski.entities.MoniteurDTO;
import com.example.stationski.services.IMoniteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/moniteur")
@Tag(name = "Moniteur Management")
public class MoniteurRestController {

    @Autowired
    private IMoniteurService moniteurService;


    // http://localhost:8089/stationSki/moniteur/retrieve-all-moniteurs
    @Operation(description = "liste des moniteurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moniteur.class)) }),
            @ApiResponse(responseCode = "400",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    content = @Content) })
    @GetMapping("/retrieve-all-moniteurs")
    public List<Moniteur> getMoniteurs() {
        return moniteurService.retrieveAllMoniteurs();
    }

    // http://localhost:8089/stationSki/moniteur/retrieve-moniteur/8
    @Operation(description = "récupérer un moniteur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the monitor",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Moniteur.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Moniteur not found",
                    content = @Content) })
    @GetMapping("/retrieve-moniteur/{moniteur-id}")
    public Moniteur retrieveMoniteur(@Parameter(description = "id of monitor to be searched")
                                         @PathVariable("moniteur-id") Integer moniteurId) {
        return moniteurService.retrieveMoniteur(moniteurId);
}



    // http://localhost:8089/stationSki/moniteur/add-moniteur  ResponseEntity<Moniteur>
    @Operation(description = "ajouter un moniteur")
    @PostMapping("/add-moniteur")
    public ResponseEntity<Moniteur> addMoniteur(@RequestBody MoniteurDTO m) {
        return new ResponseEntity<>(moniteurService.addMoniteur(m), HttpStatus.CREATED);
    }

    @Operation(description = "supprimer un moniteur")
    // http://localhost:8089/stationSki/moniteur/remove-moniteur/1
    @DeleteMapping("/remove-moniteur/{moniteur-id}")
    public void removeMoniteur(@PathVariable("moniteur-id") Integer moniteurId) {
        moniteurService.deleteMoniteur(moniteurId);
    }

    @Operation(description = "modifier un moniteur")
    // http://localhost:8089/stationSki/moniteur/update-moniteur
    @PutMapping("/update-moniteur/{moniteur-id}")
    public ResponseEntity<Moniteur> updateMoniteur(@PathVariable("moniteur-id") Integer moniteurId, @NotNull @RequestBody MoniteurDTO m) {
        return new ResponseEntity<>(moniteurService.updateMoniteur(moniteurId,m), HttpStatus.ACCEPTED);
    }

}