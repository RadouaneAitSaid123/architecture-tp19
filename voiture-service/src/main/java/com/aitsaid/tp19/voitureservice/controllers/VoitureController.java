package com.aitsaid.tp19.voitureservice.controllers;

import com.aitsaid.tp19.voitureservice.entities.Client;
import com.aitsaid.tp19.voitureservice.entities.Voiture;
import com.aitsaid.tp19.voitureservice.repositories.VoitureRepository;
import com.aitsaid.tp19.voitureservice.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.List;

/**
 * @author radouane
 **/
@RestController
public class VoitureController {

    private final VoitureRepository voitureRepository;
    private final ClientService clientService;

    public VoitureController(VoitureRepository voitureRepository, ClientService clientService) {
        this.voitureRepository = voitureRepository;
        this.clientService = clientService;
    }

    @GetMapping("/voitures")
    public List<Voiture> findAll() {
        List<Voiture> voitures = voitureRepository.findAll();

        for (Voiture v : voitures) {
            v.setClient(clientService.getClientById(v.getId_client()));
        }

        return voitures;
    }

    @GetMapping("/voiture/{id}")
    public Voiture findById(@PathVariable Long id) throws Exception {
        Voiture v = voitureRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture non trouvée"));

        v.setClient(clientService.getClientById(v.getId_client()));

        return v;
    }

    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.getClientById(id);
            if (client != null) {
                List<Voiture> voitures = voitureRepository.findByClientId(id);
                for (Voiture v : voitures) {
                    v.setClient(client);
                }
                return ResponseEntity.ok(voitures);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/voitures/{clientId}")
    public ResponseEntity<Object> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
        try {
            Client client = clientService.getClientById(clientId);

            if (client != null) {
                voiture.setClient(client);
                voiture.setId_client(clientId);
                Voiture savedVoiture = voitureRepository.save(voiture);
                return ResponseEntity.ok(savedVoiture);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Client not found with ID: " + clientId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving voiture: " + e.getMessage());
        }
    }

    @PutMapping("/voitures/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Voiture updatedVoiture) {
        try {
            Voiture existingVoiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture not found with ID: " + id));

            if (updatedVoiture.getMatricule() != null && !updatedVoiture.getMatricule().isEmpty()) {
                existingVoiture.setMatricule(updatedVoiture.getMatricule());
            }
            if (updatedVoiture.getMarque() != null && !updatedVoiture.getMarque().isEmpty()) {
                existingVoiture.setMarque(updatedVoiture.getMarque());
            }
            if (updatedVoiture.getModel() != null && !updatedVoiture.getModel().isEmpty()) {
                existingVoiture.setModel(updatedVoiture.getModel());
            }

            Voiture savedVoiture = voitureRepository.save(existingVoiture);
            return ResponseEntity.ok(savedVoiture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating voiture: " + e.getMessage());
        }
    }
}
