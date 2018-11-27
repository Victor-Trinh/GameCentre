package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.BoardManager;
import fall2018.csc2017.Move;
import fall2018.csc2017.Tile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTilesBoardManager implements BoardManager {

    /**
     * The board being managed.
     */
    private SlidingTilesBoard board;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    SlidingTilesBoardManager(SlidingTilesBoard board) {
        this.board = board;
    }

    @Override
    public SlidingTilesBoard getBoard() {
        return board;
    }

    /**
     * Helper method for generating all the sliding tiles for the board.
     *
     * @param size size of the board
     * @return the generated shuffled tiles
     */
    private List<SlidingTile> generateTiles(int size) {
        List<SlidingTile> tiles = new ArrayList<>();
        int numTiles = (int) Math.pow(size, 2);
        for (int tileNum = 0; tileNum < numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum));
        }

        // set the last tile to a blank tile
        tiles.get(tiles.size() - 1).setToBlankTile();

        SlidingTile t = tiles.remove(tiles.size() - 2);
        tiles.add(t);

        Collections.shuffle(tiles);
        makeSolvable(size, tiles);  // make sure the board is solvable
        return tiles;
    }

    /**
     * Manage a new shuffled board with numbered tiles.
     *
     * @param size         size of the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SlidingTilesBoardManager(int size, int maxUndoMoves) {
        this.board = new SlidingTilesBoard(size, generateTiles(size), maxUndoMoves);
    }

    /**
     * Manage a new shuffled board with image tiles.
     *
     * @param size         size of the board
     * @param maxUndoMoves the maximum undos that the user can do
     */
    SlidingTilesBoardManager(int size, int maxUndoMoves, byte[] image) {
        this.board = new SlidingTilesBoard(size, generateTiles(size), maxUndoMoves, image);
    }

    /**
     * Change the tiles so that it is solvable if it isn't. Leave it alone otherwise.
     * source: wikipedia https://en.wikipedia.org/wiki/15_puzzle
     *
     * @param size  size of the board
     * @param tiles a list of tiles
     */
    private void makeSolvable(int size, List<SlidingTile> tiles) {
        int inv = 0;
        int rowDist = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(j).getId() < tiles.get(i).getId()
                        && tiles.get(j).getId() != tiles.size() - 1
                        && tiles.get(i).getId() != tiles.size() - 1) {
                    inv++;
                }
            }
            if (tiles.get(i).getId() == tiles.size() - 1) {
                rowDist = size - 1 - i / size;
            }
        }
        if ((inv + rowDist) % 2 == 1) {
            Collections.swap(tiles, tiles.size() - 1, tiles.size() - 3);
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    public boolean puzzleSolved() {
        boolean solved = true;

        Tile lastTile = null;
        for (Tile tile : board) {
            if (lastTile != null && lastTile.compareTo(tile) <= 0) {
                solved = false;
                break;
            }
            lastTile = tile;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param m the move to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    public boolean isValidMove(Move m) {
        SlidingTilesMove move = (SlidingTilesMove) m;
        return move.getRow1() != move.getRow2() || move.getCol1() != move.getCol2();
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param move the move to make
     */
    @Override
    public void touchMove(Move move) {
        if (this.puzzleSolved()) { // don't let user shift tiles if game is finished
            return;
        }
        board.makeMove(move);
    }

}
