package com.pouya.mancalaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pit")
public class Pit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "is_big_pick")
    private boolean isBigPick;

    @Column(name = "current_stones")
    private int currentStones;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Player owner;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "index")
    private int index;

    public Pit() {
    }

    public Pit(int currentStones, Player owner, Board board,int index , boolean isBigPick){
        this.currentStones = currentStones;
        this.owner = owner;
        this.isBigPick = isBigPick;
        this.board = board;
        this.index = index;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isBigPick() {
        return isBigPick;
    }

    public void setBigPick(boolean bigPick) {
        isBigPick = bigPick;
    }

    public int getCurrentStones() {
        return currentStones;
    }

    public void setCurrentStones(int currentStones) {
        this.currentStones = currentStones;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pit pit = (Pit) o;
        return id == pit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
