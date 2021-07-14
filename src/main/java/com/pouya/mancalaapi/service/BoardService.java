package com.pouya.mancalaapi.service;

import com.pouya.mancalaapi.model.Board;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Pit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    public Board getBoard(Game game){
        if(game == null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "board can not initial because game is null");
        }
        Board board = new Board();
        board.setGame(game);
        List<Pit> pits = new ArrayList<>();
        pits.add(new Pit(6, game.getFirstPlayer(),board, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board,false));
        pits.add(new Pit(0, game.getFirstPlayer(),board, true));
        pits.add(new Pit(6, game.getSecondPlayer(),board, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, false));
        pits.add(new Pit(0, game.getSecondPlayer(),board, true));
        board.setPits(pits);
        return board;
    }


}
