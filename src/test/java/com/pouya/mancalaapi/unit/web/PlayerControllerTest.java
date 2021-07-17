package com.pouya.mancalaapi.unit.web;


import com.pouya.mancalaapi.controller.PlayerController;
import com.pouya.mancalaapi.dto.request.CreationPlayerResponseDto;
import com.pouya.mancalaapi.model.Player;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.pouya.mancalaapi.service.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest  extends BaseWebTest {

    @MockBean
    private PlayerService playerService;

    @Test
     void getAllShouldReturnSuccessStatus() throws Exception{
       mockMvc.perform(get("/api/player")).andExpect(status().isOk());
    }

    @Test
     void getOneByIdShouldSuccessStatus() throws Exception{
        mockMvc.perform(get("/api/player/1")).andExpect(status().isOk());

    }

    @Test
    void postPlayerShouldReturnSuccess() throws Exception {
        CreationPlayerResponseDto player = new CreationPlayerResponseDto( "test");
        when(playerService.insertOne(any(CreationPlayerResponseDto.class))).thenReturn(new Player(1, player.getUsername()));
        MockHttpServletRequestBuilder requestBuilder = post("/api/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
    @Test
    void postPlayerShouldReturnPlayer() throws Exception {
        CreationPlayerResponseDto player = new CreationPlayerResponseDto("test");
        when(playerService.insertOne(any(CreationPlayerResponseDto.class))).thenReturn(new Player(1, player.getUsername()));

        MockHttpServletRequestBuilder requestBuilder = post("/api/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player));

        String body = mockMvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString();

        Player responsePlayer = objectMapper.readValue(body, Player.class);
        Assertions.assertNotNull(responsePlayer);
    }



}
