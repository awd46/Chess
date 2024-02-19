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
        tiles[0][0] = new Tile.OccupiedTile(1, new Rook(1, whitePlayer));
        tiles[0][1] = new Tile.OccupiedTile(1, new Knight(2, whitePlayer));
        tiles[0][2] = new Tile.OccupiedTile(1, new Bishop(3, whitePlayer));
        tiles[0][3] = new Tile.OccupiedTile(1, new Queen(4, whitePlayer));
        tiles[0][4] = new Tile.OccupiedTile(1, new King(5, whitePlayer));
        tiles[0][5] = new Tile.OccupiedTile(1, new Bishop(6, whitePlayer));
        tiles[0][6] = new Tile.OccupiedTile(1, new Knight(7, whitePlayer));
        tiles[0][7] = new Tile.OccupiedTile(1, new Rook(8, whitePlayer));
        for(int file = 0; file < 8; file++){
            tiles[1][file] = new Tile.OccupiedTile((1 * 8) + (file + 1), new Pawn((1 * 8) + (file + 1), whitePlayer));
            //place the rest of white pieces
        }
        tiles[7][0] = new Tile.OccupiedTile(1, new Rook(57, blackPlayer));
        tiles[7][1] = new Tile.OccupiedTile(1, new Knight(58, blackPlayer));
        tiles[7][2] = new Tile.OccupiedTile(1, new Bishop(59, blackPlayer));
        tiles[7][3] = new Tile.OccupiedTile(1, new Queen(60, blackPlayer));
        tiles[7][4] = new Tile.OccupiedTile(1, new King(61, blackPlayer));
        tiles[7][5] = new Tile.OccupiedTile(1, new Bishop(62, blackPlayer));
        tiles[7][6] = new Tile.OccupiedTile(1, new Knight(63, blackPlayer));
        tiles[7][7] = new Tile.OccupiedTile(1, new Rook(64, blackPlayer));
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

    public void makeMove(int sourceTileNumber, int destinationTileNumber){
        Tile sourceTile = getTile(sourceTileNumber);
        Tile destinationTile = getTile(destinationTileNumber);
        Piece piece = sourceTile.getPiece();
        //handling castling
        if(piece instanceof King && isCastlingMove(sourceTile, destinationTile)){
            int sourceFile = (sourceTileNumber - 1) % 8;
            int destinationFile = (destinationTileNumber - 1) % 8;
            int rank = (sourceTileNumber - 1) / 8;
            //move the King
            sourceTile.clearPiece();
            destinationTile.setPiece(piece);
            piece.setTileCoordinate(destinationTileNumber);
            //move the Rook
            Tile rookSourceTile, rookDestinationTile;
            if(destinationFile == 6){
                rookSourceTile = getTile(8 * rank + 7);
                rookDestinationTile = getTile(8 * rank + 5);
            }else{
                rookSourceTile = getTile(8 * rank);
                rookDestinationTile = getTile(8 * rank + 3);
            }
            Piece rook = rookSourceTile.getPiece();
            rookSourceTile.clearPiece();
            rookDestinationTile.setPiece(rook);
            rook.setTileCoordinate(rookDestinationTile.getTileNumber());
        }else{
                //update source tile to be empty
            sourceTile.clearPiece();
            //set destination tile with the moved piece
            destinationTile.setPiece(piece);
            //update the pieces tile coordinate to reflect its new position
            piece.setTileCoordinate(destinationTileNumber);
        }
        //handling en passant
        if(){

        }
        //handling pawn promotion
        if(){

        }

        //update source tile to be empty
        sourceTile.clearPiece();
        //set destination tile with the moved piece
        destinationTile.setPiece(piece);
        //update the pieces tile coordinate to reflect its new position
        piece.setTileCoordinate(destinationTileNumber);
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

    private boolean isCastlingMove(Tile sourceTile, Tile destinationTile){
        //check if piece being moved is king
        Piece piece = sourceTile.getPiece();
        if(!(piece instanceof King) || piece.hasMoved()){
            return false;
        }
        int sourceFile = (sourceTile.getTileNumber() - 1) % 8;
        int destinationFile = (destinationTile.getTileNumber() - 1) % 8;
        if(Math.abs(destinationFile - sourceFile) != 2){
            return false;
        }
        int rank = (sourceTile.getTileNumber() - 1) / 8;
        int startFile = Math.min(sourceFile, destinationFile);
        int endFile = Math.max(sourceFile, destinationFile);
        for(int file = startFile + 1; file < endFile; file++){
            Tile tile = tiles[rank][file];
            if(tile.isOccupied()){
                return false;
            }
        }
        Tile rookTile;
        if(destinationFile == 6){
            rookTile = tiles[rank][7];
        }else{
            rookTile = tiles[rank][0];
        }
        Piece rook = rookTile.getPiece();
        if(!(rook instanceof Rook) || rook.hasMoved()){
            return false;
        }
        return true;
    }
}