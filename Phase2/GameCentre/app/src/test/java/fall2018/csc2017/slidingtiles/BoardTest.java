package fall2018.csc2017.slidingtiles;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.UndoMoveList;

import static org.junit.Assert.*;


public class BoardTest {

    /** The board for testing. */
    Board board;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     * @param size the size of the board (number of rows/cols).
     */
    private void setUpCorrect(int size) {
        Board.NUM_ROWS = Board.NUM_COLS = size;
        List<Tile> tiles = makeTiles();
        board = new Board(tiles);
    }

    @Test
    public void testMakeMove() {
        setUpCorrect(4);
        Tile tile0 = board.getTile(0,0);
        Tile tile1 = board.getTile(0,1);
        board.makeMove(0,0,0,1);


        Assert.assertEquals(board.getTile(0,0), tile1);
        Assert.assertEquals(board.getTile(0,1), tile0);
        Assert.assertEquals(board.getMovesMade(), 1);

    }

    @Test
    public void testUndoMove(){
        setUpCorrect(3);
        Tile tile0 = board.getTile(0,0);
        Tile tile1 = board.getTile(0,1);
        board.makeMove(0,0,0,1);
        board.undoLastMove();

        Assert.assertEquals(board.getTile(0,0), tile0);
        Assert.assertEquals(board.getTile(0,1), tile1);
        Assert.assertEquals(board.getMovesMade(), 2);
    }

}