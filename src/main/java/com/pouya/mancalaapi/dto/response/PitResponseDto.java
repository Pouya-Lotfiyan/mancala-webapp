package com.pouya.mancalaapi.dto.response;

import com.pouya.mancalaapi.model.Pit;

public class PitResponseDto {

    private long playerId;

    private long id;

    private int currentStones;

    private boolean isBigPit;

    private int index;

    public PitResponseDto(Pit pit){
        this.currentStones = pit.getCurrentStones();
        this.isBigPit = pit.isMancalaPit();
        this.id = pit.getId();
        this.index = pit.getIndex();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
