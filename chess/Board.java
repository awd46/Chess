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

    private void initializeBoard(){
        //method sets pieces in their starting positions
        for(int file = 0; file < 8; file++){
            tiles[1][file] = new OccupiedTile(file + 1, new Pawn(whitePlayer));
            //place the rest of white pieces
        }
        for(itn file = 0; file < 8; file++){
            tiles[6][file] = new OccupiedTile(file + 1, new Pawn(blackPlayer))
            //place the rest of the black pieces
        }
        //fill in empty tiles
        for(int rank = 2; rank < 6; rank++){
            for(int file = 0; file < 8; file++){
                tile[rank][file] = newEmptyTile(rank * 8 + (file + 1));
            }
        }
    }

    public Tile getTile(int file, int rank){
        //get a tile based on its rank and file
        return tiles[rank - 1][file - 'a'];
    }

    public void setTile(int file, int rank, Tile tile){
        //set a tile based on its file and rank
        tiles[rank - 1][files - 'a'] = tile;
    }

}