package com.pouya.mancalaapi.service;

import com.pouya.mancalaapi.dto.response.GameCreationResponseDto;
import com.pouya.mancalaapi.enums.GameStatus;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GameService {


    private PlayerService playerService;

    private BoardService boardService;

    private GameRepository gameRepository;

    @Autowired
    public GameService(PlayerService playerService, GameRepository gameRepository, BoardService boardService){
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }


    public GameCreationResponseDto startGame(Game game) {
        Player firstPlayer = game.getFirstPlayer();
        Player secondPlayer = game.getSecondPlayer();
        if(firstPlayer == null || secondPlayer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game must have to player");
        }
        if(firstPlayer.equals(secondPlayer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game should have two player at least.");
        }
        game.setFirstPlayer(this.playerService.getByIdOrThrowException(firstPlayer.getId()));
        game.setSecondPlayer(this.playerService.getByIdOrThrowException(secondPlayer.getId()));
        game.setBoard(boardService.getBoard(game));
        game.setStatus(GameStatus.INPROGRESS);
        return  new GameCreationResponseDto(this.gameRepository.save(game));
    }





}
