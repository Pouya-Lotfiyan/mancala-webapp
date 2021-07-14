package com.pouya.mancalaapi.model;

import com.pouya.mancalaapi.enums.GameStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "frist_player_id")
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id")
    private Player secondPlayer;


    @ManyToOne
    @JoinColumn(name = "responsible_player")
    private Player responsiblePlayer;


    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    private Board board;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatdAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player first) {
        this.firstPlayer = first;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

    public Player getResponsiblePlayer() {
        return responsiblePlayer;
    }

    public void setResponsiblePlayer(Player responsiblePlayer) {
        this.responsiblePlayer = responsiblePlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
