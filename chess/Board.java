package chess;

public class Board {
    private final Tile[][] tiles;
    private final Player whitePlayer;
    private final Player blackPlayer;

    public Board(Player whitePlayer, Player blackPlayer){
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        tiles = new Tile[8][8];
        initializeBoard();
    }

    public static boolean isValidTile(int tileNumber){
        return tileNumber >= 1 && tileNumber <= 64;
    }

    private void initializeBoard(){
        //method sets pieces in their starting positions
        for(int file = 0; file < 8; file++){
            tiles[1][file] = new Tile.OccupiedTile((1 * 8) + (file + 1), new Pawn((1 * 8) + (file + 1), whitePlayer));
            //place the rest of white pieces
        }
        for(int file = 0; file < 8; file++){
            tiles[6][file] = new Tile.OccupiedTile((6 * 8) + (file + 1), new Pawn((6 * 8) + (file + 1), blackPlayer));
            //place the rest of the black pieces
        }
        //fill in empty tiles
        for(int rank = 2; rank < 6; rank++){
            for(int file = 0; file < 8; file++){
                tiles[rank][file] = new Tile.EmptyTile((rank * 8) + (file + 1));
            }
        }
    }

    public Tile getTile(int tileNumber){
        //convert tileNumber to row and colum indices
        int row = (tileNumber - 1) / 8;
        int column = (tileNumber - 1) % 8;
        return tiles[row][column];
    }

    public void setTile(int tileNumber, Tile tile){
        //convert tileNumber to row and column indices
        int row = (tileNumber - 1) / 8;
        int column = (tileNumber - 1) % 8;
        tiles[row][column] = tile;
    }
}