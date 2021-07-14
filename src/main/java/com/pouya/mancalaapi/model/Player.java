package com.pouya.mancalaapi.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "username", unique = true)
    private String username;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatdAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;



    public Player(){

    }

    public Player(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Player(String username){
        this.setUsername(username);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
