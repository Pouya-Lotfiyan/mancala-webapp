package com.pouya.mancalaapi.controller;

import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("player")
public class PlayerController {


    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> get(){
        return this.playerService.getAll();
    }


    @GetMapping("/{id}")
    public Player get(@PathVariable("id") long id){
        return null;
    }

    @PostMapping()
    public Player postPlayer(@RequestBody Player player){
        return this.playerService.insertOne(player);
    }


}
