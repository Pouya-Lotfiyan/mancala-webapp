package com.pouya.mancalaapi.model;

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
    private Player FirstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id")
    private Player SecondPlayer;

    @OneToOne(mappedBy = "game")
    private Board board;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatdAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
