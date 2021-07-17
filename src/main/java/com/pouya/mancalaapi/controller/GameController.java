package com.pouya.mancalaapi.controller;

import com.pouya.mancalaapi.dto.request.StartGameRequestDto;
import com.pouya.mancalaapi.dto.response.GameResponseDto;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Pit;
import com.pouya.mancalaapi.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "Provides operations for gaming (start and move pits)")
@RestController
@RequestMapping("api/game")
public class GameController {


    private GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @ApiOperation(value = "start a game with two players",
            consumes = "application/json" ,
            produces = "application/json"
    )
    @PostMapping
    public GameResponseDto postGame(@RequestBody StartGameRequestDto gameRequestDto){
        return this.gameService.startGame(gameRequestDto);
    }

    @ApiOperation(value = "start a move by particular pit on board",
            consumes = "application/json" ,
            produces = "application/json"
    )
    @PostMapping(path = "{gameId}/player/{playerId}/move")
    public GameResponseDto getMove(@RequestBody Pit pit,
                                   @PathVariable("playerId") long playerId,
                                   @PathVariable("gameId") long gameId
                        ){
        return this.gameService.handleMove(pit, gameId, playerId);
    }


}
