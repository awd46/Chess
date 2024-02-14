package chess;

public class Board {
    private final Tile[][] tiles;

    public Board(){
        tiles = new Tile[8][8];
        initializeBoard();
    }
    private void initializeBoard(){
        //method sets pieces in their starting positions
    }
}