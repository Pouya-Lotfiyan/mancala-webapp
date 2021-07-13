package com.pouya.mancalaapi.repository;

import com.pouya.mancalaapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {


    public Player findByUsername(String username);

}
