package com.pouya.mancalaapi.dto.response;

import com.pouya.mancalaapi.model.Pit;

public class PitResponseDto {

    private long playerId;

    private int currentStones;

    private boolean isBigPit;

    public PitResponseDto(Pit pit){
        this.currentStones = pit.getCurrentStones();
        this.isBigPit = pit.isBigPick();
        this.playerId = pit.getOwner().getId();
    }



    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getCurrentStones() {
        return currentStones;
    }

    public void setCurrentStones(int currentStones) {
        this.currentStones = currentStones;
    }

    public boolean isBigPit() {
        return isBigPit;
    }

    public void setBigPit(boolean bigPit) {
        isBigPit = bigPit;
    }
}
