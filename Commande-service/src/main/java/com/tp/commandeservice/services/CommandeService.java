package com.tp.commandeservice.services;

import com.tp.commandeservice.entities.Commande;
import com.tp.commandeservice.entities.OrderItem;
import com.tp.commandeservice.repository.CommandeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommandeService {
    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public Commande createCommande(Commande commande) {
        if (commande.getOrderDate() == null) {
            commande.setOrderDate(LocalDateTime.now());
        }
        if (commande.getOrderItems() != null) {
            for (OrderItem item : commande.getOrderItems()) {
                item.setCommande(commande);
            }
        }
        return commandeRepository.save(commande);
    }

    public Commande updateCommande(Long id, Commande commandeDetails) {
        Commande commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.setStatus(commandeDetails.getStatus());
            commande.setTotalAmount(commandeDetails.getTotalAmount());
            // Updating items is complex, for simplicity we might just replace them or add
            // new ones
            // Here we just update basic fields. For a full update, we'd need more logic.
            return commandeRepository.save(commande);
        }
        return null;
    }

    public void deleteCommandeById(Long id) {
        commandeRepository.deleteById(id);
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
}
