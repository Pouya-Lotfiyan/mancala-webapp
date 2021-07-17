package com.pouya.mancalaapi.repository;

import com.pouya.mancalaapi.enums.GameStatus;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface GameRepository  extends JpaRepository<Game, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Game  g SET g.status = :status, g.winner = :winner WHERE g.id = :id")
    void updateWinnerAndStatus(@Param("id") long gameId,@Param("winner") Player winner, @Param("status") GameStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Game  g SET g.responsiblePlayer = :responsiblePlayer WHERE g.id = :id")
    void updateResponsiblePlayer(@Param("id") long id, @Param("responsiblePlayer") Player responsiblePlayer);
}
