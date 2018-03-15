package dkeep.test;
import org.junit.Test;
import static org.junit.Assert.*;

import dkeep.logic.*;

public class TestDungeonGameLogic {
    char map1[][] = {{ 'X', 'I', 'X', 'X', 'X' },
    { 'X', ' ', ' ', ' ', 'X' },
    { 'X', ' ', ' ', ' ', 'X' },
    { 'X', 'K', ' ', ' ', 'X' },
    { 'X', 'X', 'X', 'X', 'X' } };



    @Test
    public void testHeroChar(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        assertTrue('H' == game.hero.getChar());
    }

    @Test
    public void testGuardChar(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        assertTrue('G' == game.guard.getChar()|| 'g' == game.guard.getChar());
    }

    @Test
    public void testKeyChar(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        assertTrue('k' == game.key.getChar()|| '$' == game.key.getChar());
        game.key.setVisible(false);
        assertTrue(' ' == game.key.getChar());
    }

    @Test
    public void testMoveHeroIntoFreeCell(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard.setCoordinates(1,3);

        assertEquals(1, game.getHero().getLine());
        assertEquals(1, game.getHero().getColumn());
        game.moveHero('d');
        assertEquals(1, game.getHero().getLine());
        assertEquals(2, game.getHero().getColumn());
    }

    @Test
    public void testHeroMovesIntoWall(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard.setCoordinates(1,3);

        game.moveHero('a');
        assertEquals(1, game.getHero().getLine());
        assertEquals(1, game.getHero().getColumn());

        game.moveHero('w');
        assertEquals(1, game.getHero().getLine());
        assertEquals(1, game.getHero().getColumn());
    }


    @Test
    public void testHeroIsCapturedByGuard(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard.setCoordinates(1,3);

        assertFalse(game.isGameOver());
        game.moveHero('d');
        assertTrue(game.isGameOver());
    }

    @Test
    public void testHeroLeavesClosedDoor(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard.setCoordinates(1,3);

        assertEquals(1, game.getHero().getLine());
        assertEquals(1, game.getHero().getColumn());

        assertFalse(game.getMap().areDoorsOpen());
    }


    @Test
    public void testHeroOpensDoor(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard.setCoordinates(1,3);

        game.moveHero('s');
        game.moveHero('s');

        assertTrue(game.getMap().areDoorsOpen());
    }

    @Test
    public void testHeroGoThroughDoor(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard.setCoordinates(1,3);

        game.moveHero('s');
        game.moveHero('s');

        game.moveHero('w');
        game.moveHero('w');
        game.moveHero('w');

        assertTrue(game.getCurrent_state()== GameState.States.MAP_DONE);

    }

    @Test(timeout = 1000)
    public void testDrunkenGuardisAsleep(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard = new Drunken(1,3,'G');

        boolean asleep = false;
        boolean awake = false;
        int line, column;

        while(!asleep || !awake){
            line = game.guard.getLine();
            column = game.guard.getColumn();
            game.guard.moveChar();

            if(game.guard.isAsleep()) {
                asleep = true;
                assertEquals(line, game.guard.getLine());
                assertEquals(column, game.guard.getColumn());
            }
            else awake = true;
        }
    }

    @Test(timeout = 1000)
    public void testSuspiciousGuardisReverse(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard = new Suspicious(1,3,'G');

        boolean reverse = false;
        boolean normal = false;

        while(!normal || !reverse){
            game.guard.moveChar();

            if(game.guard.isReverse())
                normal = true;
            else reverse = true;
        }
    }

    @Test
    public void testHeroLosesWithGuardAsleep(){
        Map map = new Map(map1);
        GameState game = new GameState(map);
        game.guard = new Drunken(1,3,'G');

        game.guard.setAsleep(true);
        assertTrue(game.guard.isAsleep());
        game.moveHero('d');

        game.checkEvents();
        assertTrue(GameState.States.PLAYING == game.getCurrent_state());
    }
}