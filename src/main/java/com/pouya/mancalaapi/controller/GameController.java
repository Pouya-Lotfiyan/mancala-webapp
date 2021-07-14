package com.pouya.mancalaapi.controller;

import com.pouya.mancalaapi.dto.response.GameCreationResponseDto;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
public class GameController {


    private GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping
    public GameCreationResponseDto postGame(@RequestBody Game game){
        return this.gameService.startGame(game);
    }



}
