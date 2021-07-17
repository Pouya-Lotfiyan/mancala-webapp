package com.pouya.mancalaapi.controller;

import com.pouya.mancalaapi.dto.request.CreationPlayerResponseDto;
import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/player")
@Api(description = "Provides some operation for players in game")
public class PlayerController {


    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @ApiOperation(value = "Return all players",
            consumes = "application/json" ,
            produces = "application/json"
    )
    @GetMapping
    public List<Player> get(){
        return this.playerService.getAll();
    }

    @ApiOperation(value = "Return a player with particular Id",
            consumes = "application/json" ,
            produces = "application/json"
    )
    @GetMapping("/{id}")
    public Player get(@PathVariable("id") long id){
        return this.playerService.getByIdOrThrowException(id);
    }

    @ApiOperation(value = "create a player",
            consumes = "application/json" ,
            produces = "application/json"
    )
    @PostMapping
    public Player postPlayer(@RequestBody CreationPlayerResponseDto player){
        return this.playerService.insertOne(player);
    }


}
