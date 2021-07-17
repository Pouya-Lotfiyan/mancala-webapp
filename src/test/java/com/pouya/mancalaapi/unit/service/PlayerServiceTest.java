package com.pouya.mancalaapi.unit.service;

import com.pouya.mancalaapi.dto.request.CreationPlayerResponseDto;
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
        CreationPlayerResponseDto player = new CreationPlayerResponseDto("gholi");
        // TODO generate username by faker
        Player savedPlayer = playerService.insertOne(player);
        Assertions.assertNotNull(savedPlayer);

    }



}
