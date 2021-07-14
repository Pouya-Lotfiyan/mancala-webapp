package com.pouya.mancalaapi.dto.request;

import com.pouya.mancalaapi.enums.PlayerType;

public class MoveRequestDto {


    private long startPitId;

    private PlayerType playerType;


    public MoveRequestDto() {
    }

    public MoveRequestDto(long startPitId, PlayerType playerType) {
        this.startPitId = startPitId;
        this.playerType = playerType;
    }

    public long getStartPit() {
        return startPitId;
    }

    public void setStartPit(long startPitId) {
        this.startPitId = startPitId;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
