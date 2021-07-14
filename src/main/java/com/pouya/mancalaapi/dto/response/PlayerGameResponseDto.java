package com.pouya.mancalaapi.dto.response;

import com.pouya.mancalaapi.model.Player;
import java.util.List;

public class PlayerGameResponseDto {


    private Player player;

    private List<PitResponseDto> pits;

    public PlayerGameResponseDto(Player player, List<PitResponseDto> pits) {
        this.player = player;
        this.pits = pits;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<PitResponseDto> getPits() {
        return pits;
    }

    public void setPits(List<PitResponseDto> pits) {
        this.pits = pits;
    }
}
