package com.pouya.mancalaapi.service;

import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;


    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }


    public Player insertOne(Player player) {
        Player foundPlayer = this.playerRepository.findByUsername(player.getUsername());
        if(foundPlayer != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "player with username["+player.getUsername()+"] already exists");
        }
        return playerRepository.save(player);
    }

    public List<Player> getAll() {
        return this.playerRepository.findAll();
    }
}
