package com.pouya.mancalaapi.repository;

import com.pouya.mancalaapi.model.Board;
import com.pouya.mancalaapi.model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PitRepository extends JpaRepository<Pit, Long> {

    public List<Pit> findByBoardOrderByIndex(Board board);

    @Modifying
    @Query("UPDATE Pit p SET p.currentStones = :currentStones WHERE p.id = :id")
    void update(@Param("id") long id, @Param("currentStones") int currentStones);
}