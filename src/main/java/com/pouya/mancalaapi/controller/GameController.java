package com.pouya.mancalaapi.controller;

import com.pouya.mancalaapi.dto.response.GameResponseDto;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Pit;
import com.pouya.mancalaapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameController {


    private GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @PostMapping
    public GameResponseDto postGame(@RequestBody Game game){
        return this.gameService.startGame(game);
    }

    @PostMapping
    @RequestMapping("{gameId}/player/{playerId}/move")
    public GameResponseDto getMove(@RequestBody Pit pit,
                                   @PathVariable("playerId") long playerId,
                                   @PathVariable("gameId") long gameId
                        ){
        return this.gameService.move(pit, gameId, playerId);
    }


}
