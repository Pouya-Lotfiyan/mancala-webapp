package com.pouya.mancalaapi.dto.response;

import com.pouya.mancalaapi.enums.GameStatus;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GameResponseDto {

    private long gameId;

    private LocalDateTime createdAt;

    private PlayerGameResponseDto firstPlayer;

    private PlayerGameResponseDto secondPlayer;

    private long bordId;

    private GameStatus status;

    private Player nexTurnPlayer;

    public GameResponseDto(Game game){
        this.createdAt = game.getCreatedAt();
        this.gameId = game.getId();
        this.status = game.getStatus();
        this.bordId = game.getBoard().getId();
        List<PitResponseDto> firstPlayerPits = game.getBoard().getPits()
                .stream()
                .filter(pit -> pit.getOwner() == game.getFirstPlayer())
                .map(PitResponseDto::new)
                .collect(Collectors.toList());
        this.firstPlayer = new PlayerGameResponseDto(game.getFirstPlayer(), firstPlayerPits);
        List<PitResponseDto> secondPlayerPits = game.getBoard().getPits()
                .stream()
                .filter(pit -> pit.getOwner() == game.getSecondPlayer())
                .map(PitResponseDto::new)
                .collect(Collectors.toList());
        this.secondPlayer = new PlayerGameResponseDto(game.getSecondPlayer(), secondPlayerPits);

        this.nexTurnPlayer = game.getResponsiblePlayer();
    }

    public GameResponseDto(){
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PlayerGameResponseDto getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(PlayerGameResponseDto firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public PlayerGameResponseDto getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(PlayerGameResponseDto secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public long getBordId() {
        return bordId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setBordId(long bordId) {
        this.bordId = bordId;
    }

    public Player getNexTurnPlayer() {
        return nexTurnPlayer;
    }

    public void setNexTurnPlayer(Player nexTurnPlayer) {
        this.nexTurnPlayer = nexTurnPlayer;
    }
}
