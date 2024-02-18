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