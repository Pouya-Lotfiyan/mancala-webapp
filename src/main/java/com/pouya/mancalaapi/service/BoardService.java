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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BoardService {


    private final PitRepository pitRepository;

    @Autowired
    public BoardService(PitRepository pitRepository){
        this.pitRepository = pitRepository;
    }

    public Board createBoard(Game game){
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

    public Pit getPitByIdOrThrowException(long id ){
        Optional<Pit> optionalPit = this.pitRepository.findById(id);
        if(!optionalPit.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pit with id:["+id+"] does not exists.");
        }
        return optionalPit.get();
    }


    public GameResponseDto handleMoves(Board board, Player player, Pit pit) {
        pit = getPitByIdOrThrowException(pit.getId());
        validateMove(board, pit, player);
        GameResponseDto gameResponseDto = new GameResponseDto();
        List<Pit> pits = getPits(board);
        AtomicInteger pickedStones = new AtomicInteger(pit.getCurrentStones());
        pit.setCurrentStones(0);
        long skipIndex = pit.getIndex();

        while (pickedStones.get() > 0){
            boolean BREAK = true, CONTINUE = false;
            pits.stream().skip(skipIndex).filter( each -> {
                            if(!each.getOwner().equals(player) && each.isMancalaPit()) return CONTINUE;
                            if(pickedStones.get() == 1){
                                handleLastOneMove(each, pits, gameResponseDto, player);
                                pickedStones.getAndDecrement();
                                return BREAK;
                            }
                            each.setCurrentStones(each.getCurrentStones() + 1);
                            pickedStones.getAndDecrement();
                            return CONTINUE;
                    }).findFirst();

            skipIndex = 0;
        }
        updatePitsCurrentStones(pits);
        board.setPits(pits);
        GameStatus status = getGameStatus(pits);
        gameResponseDto.setStatus(status);
        if(status.equals(GameStatus.DONE)){
            gameResponseDto.setWinner(determineWinner(pits));
        }
        return gameResponseDto;
    }

    public Player determineWinner(List<Pit> pits) {
        Optional<Pit> pit = pits.stream().filter(Pit::isMancalaPit).max(Pit::compareTo);
        return pit.map(Pit::getOwner).orElse(null);
    }

    public GameStatus getGameStatus(List<Pit> pits) {
        Map<Long, Integer> remainingStones = new HashMap<>();
        pits.stream()
                .filter(pit -> !pit.isMancalaPit())
                .forEach(pit -> {
                    long playerId = pit.getOwner().getId();
                    int lastCount = Optional.ofNullable(remainingStones.get(playerId)).orElse(0);
                    remainingStones.put(pit.getOwner().getId(), lastCount + pit.getCurrentStones());
                });
        Optional<Integer> emptyStones = remainingStones.values().stream().filter(stones -> stones == 0).findFirst();
        if(emptyStones.isPresent()){
            return GameStatus.DONE;
        }
        return GameStatus.INPROGRESS;
    }

    public void updatePitsCurrentStones(List<Pit> pits){
        pits.stream().forEach(pit -> {
            this.pitRepository.updatePitsCurrentStones(pit.getId(), pit.getCurrentStones());
        });
    }

    public void handleLastOneMove(Pit targetPit, List<Pit> pits, GameResponseDto gameResponseDto, Player player) {
        if(targetPit.isMancalaPit() && targetPit.getOwner().equals(player)){
            gameResponseDto.setNexTurnPlayer(player);
            targetPit.setCurrentStones(targetPit.getCurrentStones() + 1);
            return;
        }
        if(targetPit.getCurrentStones() == 0){
            Pit oppositePit = pits.get(pits.size() - 1 - targetPit.getIndex() );
            Pit playerBigPit = getMancalPitPlayer(pits, player);
            playerBigPit.setCurrentStones( playerBigPit.getCurrentStones() + oppositePit.getCurrentStones() + 1);
            oppositePit.setCurrentStones(0);
            return;
        }
        if(targetPit.getCurrentStones() != 0 ){
            targetPit.setCurrentStones(targetPit.getCurrentStones() + 1);
        }
    }

    public void validateMove(Board board, Pit pit, Player player) {
        if(!pit.getBoard().equals(board)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "pit doesn't belong to this board");
        }
        if (!pit.getOwner().equals(player)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "requested pit does not belong to player");
        }
        if(pit.isMancalaPit()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "can not start with a big pit.");
        }
        if(pit.getCurrentStones() == 0 ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the Pit with id:["+pit.getId()+"] has no stones.");
        }
    }

    public Pit getMancalPitPlayer(List<Pit> pits, Player player){
       return pits.stream().filter(pit -> pit.isMancalaPit() && pit.getOwner().equals(player)).findFirst().get();
    }


}
