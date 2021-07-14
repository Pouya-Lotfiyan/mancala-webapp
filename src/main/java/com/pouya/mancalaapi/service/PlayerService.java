package com.pouya.mancalaapi.service;

import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
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

    public Player getByIdOrThrowException(long id) {

        Optional<Player> optionalPlayer = this.playerRepository.findById(id);
        if(optionalPlayer.isPresent()){
            return optionalPlayer.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "player with id:["+id+"] dose not exists.");

    }



}
