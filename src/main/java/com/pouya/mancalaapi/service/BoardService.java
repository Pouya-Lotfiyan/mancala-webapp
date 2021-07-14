package com.pouya.mancalaapi.service;

import com.pouya.mancalaapi.dto.response.GameResponseDto;
import com.pouya.mancalaapi.enums.GameStatus;
import com.pouya.mancalaapi.model.Board;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Pit;
import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.repository.PitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BoardService {


    private PitRepository pitRepository;

    @Autowired
    public BoardService(PitRepository pitRepository){
        this.pitRepository = pitRepository;
    }



    public Board getBoard(Game game){
        if(game == null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "board can not initial because game is null");
        }
        Board board = new Board();
        board.setGame(game);
        List<Pit> pits = new ArrayList<>();
        pits.add(new Pit(6, game.getFirstPlayer(),board, 1, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, 2, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, 3, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, 4, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, 5, false));
        pits.add(new Pit(6, game.getFirstPlayer(),board, 6, false));
        pits.add(new Pit(0, game.getFirstPlayer(),board, 7, true));
        pits.add(new Pit(6, game.getSecondPlayer(),board, 8, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, 9, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, 10,false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, 11, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, 12, false));
        pits.add(new Pit(6, game.getSecondPlayer(),board, 13, false));
        pits.add(new Pit(0, game.getSecondPlayer(),board, 14, true));
        board.setPits(pits);
        return board;
    }

    public List<Pit> getPits(Board board){
        return this.pitRepository.findByBoardOrderByIndex(board);
    }

    public Pit getPitByBoardAndId(Board board, long id ){
        Optional<Pit> optionalPit = this.pitRepository.findById(id);
        if(!optionalPit.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pit with id:["+id+"] does not exists.");
        }
        Pit pit = optionalPit.get();
        if(!pit.getBoard().equals(board)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pit doesn't belong to this board");
        }
        return pit;
    }


    public GameResponseDto move(Board board, Player player, Pit pit) {
        pit = getPitByBoardAndId(board, pit.getId());
        validateMove(pit);
        GameResponseDto gameResponseDto = new GameResponseDto();
        List<Pit> pits = getPits(board);
        AtomicInteger pickedStones = new AtomicInteger(pit.getCurrentStones());
        long skipIndex = pit.getIndex();
        boolean BREAK = true;
        boolean  CONTINUE = false;

        while (pickedStones.get() > 0){
            pits.stream()
                    .skip(skipIndex)
                    .filter( each -> {
                            if(!each.getOwner().equals(player) && each.isBigPick()) return CONTINUE;
                            if(pickedStones.get() == 1){
                                handleLastOneMove(each, pits, gameResponseDto, player);
                                pickedStones.getAndDecrement();
                                return BREAK;
                            }
                            each.setCurrentStones(each.getCurrentStones() + 1);
                            pickedStones.getAndDecrement();
                            return CONTINUE;
                    })
                    .findFirst();

            skipIndex = 0;
        }

        updatePits(pits);
        gameResponseDto.setStatus(checkGameStatus(pits));
        gameResponseDto.setBordId(board.getId());
        return gameResponseDto;
    }

    private GameStatus checkGameStatus(List<Pit> pits) {
        Optional<Pit> pit = pits.stream().filter(each -> each.getCurrentStones() != 0 && !each.isBigPick()).findFirst();
        if(pit.isPresent()){
            return GameStatus.INPROGRESS;
        }else {
            return GameStatus.DONE;
        }
    }

    private void updatePits(List<Pit> pits){
        pits.stream().forEach(pit -> {
            this.pitRepository.update(pit.getId(), pit.getCurrentStones());
        });
    }

    private void handleLastOneMove(Pit pit, List<Pit> pits, GameResponseDto gameResponseDto, Player player) {
        if(pit.isBigPick() && pit.getOwner().equals(player)){
            gameResponseDto.setNexTurnPlayer(player);
            pit.setCurrentStones(pit.getCurrentStones() + 1);
        }
        if(pit.getCurrentStones() == 0){
            Pit oppositePit = pits.get(pits.size() - pit.getIndex());
            Pit playerBigPit = getBigPitByPlayer(pits, player);
            playerBigPit.setCurrentStones( playerBigPit.getCurrentStones() + oppositePit.getCurrentStones() + 1);
            oppositePit.setCurrentStones(0);
        }
        if(pit.getCurrentStones() != 0 ){
            pit.setCurrentStones(pit.getCurrentStones() + 1);
        }
    }

    private void validateMove(Pit pit) {
        if(pit.getCurrentStones() == 0 ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the Pit with id:["+pit.getId()+"] has no stones.");
        }
        if(pit.isBigPick()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "can not start with a big pit.");
        }
    }

    private Pit getBigPitByPlayer(List<Pit> pits, Player player){
       return pits.stream().filter(pit -> pit.isBigPick() && pit.getOwner().equals(player)).findFirst().get();
    }


}
