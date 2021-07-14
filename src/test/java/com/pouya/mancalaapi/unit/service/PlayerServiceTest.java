package com.pouya.mancalaapi.unit.service;

import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.service.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Test
    void createPlayer(){
        Player player = new Player();
        // TODO generate username by faker
        player.setUsername("gholi");
        Player savedPlayer = playerService.insertOne(player);
        Assertions.assertNotNull(savedPlayer);

    }



}
