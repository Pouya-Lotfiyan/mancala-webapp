package com.pouya.mancalaapi.repository;

import com.pouya.mancalaapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {


    public Player findByUsername(String username);

}
