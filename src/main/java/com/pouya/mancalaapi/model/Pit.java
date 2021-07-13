package com.pouya.mancalaapi.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Player owner;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatdAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

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

    public LocalDateTime getUpdatdAt() {
        return updatdAt;
    }

    public void setUpdatdAt(LocalDateTime updatdAt) {
        this.updatdAt = updatdAt;
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


}
