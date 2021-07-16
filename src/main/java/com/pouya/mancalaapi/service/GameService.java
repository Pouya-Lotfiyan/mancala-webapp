package com.pouya.mancalaapi.service;

import com.pouya.mancalaapi.dto.response.GameResponseDto;
import com.pouya.mancalaapi.enums.GameStatus;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Pit;
import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

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


    public GameResponseDto startGame(Game game) {
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
        game.setBoard(boardService.createBoard(game));
        game.setStatus(GameStatus.INPROGRESS);
        game.setResponsiblePlayer(game.getFirstPlayer());
        return new GameResponseDto(this.gameRepository.save(game));

    }


    public Game getByIdOrThrowException(long id){
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if(optionalGame.isPresent()){
            return optionalGame.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "game with id:["+id+":] does not exists.");
    }



    public GameResponseDto handleMove(Pit pit, long gameId, long playerId) {
        Game game = this.getByIdOrThrowException(gameId);
        Player player = this.playerService.getByIdOrThrowException(playerId);
        if(game.getStatus().equals(GameStatus.DONE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "game is already done.");
        }
        if(!player.equals(game.getResponsiblePlayer())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "It's not the player's turn");
        }
        GameResponseDto gameResponse = this.boardService.handleMoves(game.getBoard(), player , pit);
        updateGameByGameResponseDto(game, gameResponse);
        return new GameResponseDto(game);
    }

    private void updateGameByGameResponseDto(Game game, GameResponseDto gameResponse) {
        if(gameResponse.getStatus().equals(GameStatus.DONE)){
             game.setStatus(GameStatus.DONE);
             game.setWinner(gameResponse.getWinner());
             updateWinnerAndStatus(game.getId(), game.getWinner(), game.getStatus() );
        }
        if(gameResponse.getNexTurnPlayer() == null ) {
            changeResponsiblePlayer(game);
            updateResponsiblePlayer(game.getId(), game.getResponsiblePlayer());
        }
    }

     public void changeResponsiblePlayer(Game game){
        if(game.getResponsiblePlayer().equals(game.getFirstPlayer())){
            game.setResponsiblePlayer(game.getSecondPlayer());
        }else {
            game.setResponsiblePlayer(game.getFirstPlayer());
        }
    }

     public void updateResponsiblePlayer(long id, Player player){
        this.gameRepository.updateResponsiblePlayer(id, player);
    }

     public void updateWinnerAndStatus(long id, Player winner, GameStatus status) {
        this.gameRepository.updateWinnerAndStatus(id, winner, status);
    }
}
