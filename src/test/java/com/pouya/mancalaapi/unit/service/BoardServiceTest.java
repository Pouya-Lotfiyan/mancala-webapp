package com.pouya.mancalaapi.unit.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pouya.mancalaapi.enums.GameStatus;
import com.pouya.mancalaapi.model.Board;
import com.pouya.mancalaapi.model.Game;
import com.pouya.mancalaapi.model.Pit;
import com.pouya.mancalaapi.model.Player;
import com.pouya.mancalaapi.service.BoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    private static ObjectMapper objectMapper;

    private static List<Pit> emptyOneSidePitList;


    @BeforeAll
    static void setUp(){
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @BeforeAll
    static void loadFixtures() throws Exception{
        emptyOneSidePitList = objectMapper.readValue(new File("src/test/resources/emptyOneSidePitList.json"), new TypeReference<List<Pit>>(){});
    }


    @Test
    void shouldGetGameStatusCorrectly(){
        GameStatus status = this.boardService.getGameStatus(emptyOneSidePitList);
        Assertions.assertEquals(status, GameStatus.DONE);
    }

    @Test
    void shouldReturnWinnerCorrectly(){
        Player playerOne = new Player(1,"playerOne");
        Player playerTwo = new Player(2, "playerTwo");
        Pit playerOnePit = new Pit(10, playerOne, null, 7, true);
        Pit playerTwoPit = new Pit(9, playerTwo, null, 14, true);
        List<Pit> pits = new ArrayList<>();
        pits.add(playerOnePit);
        pits.add(playerTwoPit);
        Player winner = this.boardService.determineWinner(pits);
        System.out.println("winner id" + playerOne.getId());
        Assertions.assertEquals(playerOne, winner);
    }

    @Test
    void shouldRetrunPlayerMancalPitCorrectly(){
        Player playerOne = new Player(1,"playerOne");
        Player tagetPlayer = new Player(2, "playerTwo");
        Pit targetPit = new Pit(9, tagetPlayer, null, 14, true);

        ArrayList<Pit> pits = new ArrayList<>();
        pits.add(new Pit(10, playerOne, null, 7, true));
        pits.add(targetPit);
        pits.add(new Pit(10, playerOne, null, 6, false));
        pits.add(new Pit(9, tagetPlayer, null, 13, false));
        Pit returnedPit = this.boardService.getMancalPitPlayer(pits, tagetPlayer);
        Assertions.assertEquals(targetPit, returnedPit);
    }

    @Test
    void shouldCreateBoardWithCorrectPitsSize(){
        Board board = this.boardService.createBoard(new Game());
        Assertions.assertEquals(14, board.getPits().size());
    }

    @Test
    void shouldNotCreateBoardWithEmptyGame(){
        try {
            Board board = this.boardService.createBoard(null);
            Assertions.fail( "should NotCreate Board With Empty Game" );
        }catch (ResponseStatusException ignored){}
    }

    @Test
    void createdBoardShouldHasTwoMancalPit(){
        Board board = this.boardService.createBoard(new Game());
        List<Pit> mancalaPits = board.getPits().stream().filter(Pit::isMancalaPit).collect(Collectors.toList());
        Assertions.assertEquals(mancalaPits.size(), 2);
    }

    // todo this class still need more unit tests

}
