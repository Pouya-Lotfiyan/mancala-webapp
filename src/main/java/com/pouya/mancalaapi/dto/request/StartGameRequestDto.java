package com.pouya.mancalaapi.dto.request;

import com.pouya.mancalaapi.model.Player;

public class StartGameRequestDto {

    private Player firstPlayer;

    private Player secondPlayer;

    public StartGameRequestDto() {
    }

    public StartGameRequestDto(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
