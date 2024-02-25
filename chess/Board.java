package chess;

import java.util.List;
import java.util.ArrayList;

public class Board {
    private final Tile[][] tiles;
    private final Players whitePlayer;
    private final Players blackPlayer;

    public Board(Players whitePlayer, Players blackPlayer){
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
        tiles[0][0] = new Tile.OccupiedTile(1, new Rook(1, blackPlayer));
        tiles[0][1] = new Tile.OccupiedTile(2, new Knight(2, blackPlayer));
        tiles[0][2] = new Tile.OccupiedTile(3, new Bishop(3, blackPlayer));
        tiles[0][3] = new Tile.OccupiedTile(4, new Queen(4, blackPlayer));
        tiles[0][4] = new Tile.OccupiedTile(5, new King(5, blackPlayer));
        tiles[0][5] = new Tile.OccupiedTile(6, new Bishop(6, blackPlayer));
        tiles[0][6] = new Tile.OccupiedTile(7, new Knight(7, blackPlayer));
        tiles[0][7] = new Tile.OccupiedTile(8, new Rook(8, blackPlayer));
        for(int file = 0; file < 8; file++){
            tiles[1][file] = new Tile.OccupiedTile((1 * 8) + (file + 1), new Pawn((1 * 8) + (file + 1), blackPlayer));
            //place the rest of white pieces
        }
        tiles[7][0] = new Tile.OccupiedTile(57, new Rook(57, whitePlayer));
        tiles[7][1] = new Tile.OccupiedTile(58, new Knight(58, whitePlayer));
        tiles[7][2] = new Tile.OccupiedTile(59, new Bishop(59, whitePlayer));
        tiles[7][3] = new Tile.OccupiedTile(60, new Queen(60, whitePlayer));
        tiles[7][4] = new Tile.OccupiedTile(61, new King(61, whitePlayer));
        tiles[7][5] = new Tile.OccupiedTile(62, new Bishop(62, whitePlayer));
        tiles[7][6] = new Tile.OccupiedTile(63, new Knight(63, whitePlayer));
        tiles[7][7] = new Tile.OccupiedTile(64, new Rook(64, whitePlayer));
        for(int file = 0; file < 8; file++){
            tiles[6][file] = new Tile.OccupiedTile((6 * 8) + (file + 1), new Pawn((6 * 8) + (file + 1), whitePlayer));
            //place the rest of the black pieces
        }
        //fill in empty tiles
        for(int rank = 2; rank < 6; rank++){
            for(int file = 0; file < 8; file++){
                tiles[rank][file] = new Tile.OccupiedTile((rank * 8) + (file + 1), null);
            }
        }
    }

    public void makeMove(int sourceTileNumber, int destinationTileNumber, String move){
        Tile sourceTile = getTile(sourceTileNumber);
        Tile destinationTile = getTile(destinationTileNumber);
        Piece piece = sourceTile.getPiece();
        if(piece == null){
            return;
        }
        //new stuff
        List<Move> legalMoves = piece.calculateLegalMoves(this);
        boolean isLegalMove = false;
        for(Move legalMove : legalMoves){
            if(legalMove.getdestinationTile() == destinationTileNumber){
                isLegalMove = true;
                break;
            }
        }
        if(isLegalMove){
            /*if(destinationTile.isOccupied()){
                destinationTile.clearPiece();
            }*/
            sourceTile.clearPiece();
            destinationTile.setPiece(piece);
            piece.setTileCoordinate(destinationTileNumber);
        }else{
            return;
        }
    }

        /* 
        //handling castling
        else if(piece instanceof King && isCastlingMove(sourceTile, destinationTile)){
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
        }
            else if(isEnPassantMove(sourceTile, destinationTile)){
                int capturedPawnTileNumber = destinationTileNumber + (piece.getColor().getDirection() * -8);
                Tile capturedPawnTile = getTile(capturedPawnTileNumber);
                capturedPawnTile.clearPiece();
        }

        //handling pawn promotion
        else if(piece instanceof Pawn){
            int destinationRank = (destinationTileNumber - 1) / 8 + 1;
            if(destinationRank == 8 || destinationRank == 1){
                String promotionPiece = Coordinates.parsePromotionPiece(move);
                if(promotionPiece != null){
                    switch(promotionPiece.toLowerCase()){
                        case "q":
                            destinationTile.setPiece(new Queen(destinationTileNumber, piece.getColor()));
                            break;
                        case "r":
                            destinationTile.setPiece(new Rook(destinationTileNumber, piece.getColor()));
                            break;
                        case "n":
                            destinationTile.setPiece(new Knight(destinationTileNumber, piece.getColor()));
                            break;
                        case "b":
                            destinationTile.setPiece(new Bishop(destinationTileNumber, piece.getColor()));
                            break;
                        default:
                            destinationTile.setPiece(new Queen(destinationTileNumber, piece.getColor()));
                    }
                }
            }
        }else{
            return;
        }
    }*/
    
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

    private boolean isEnPassantMove(Tile sourceTile, Tile destinationTile){
        Piece piece = sourceTile.getPiece();
        if(piece instanceof Pawn){
            int sourceRank = sourceTile.getTileNumber() / 8;
            int destinationRank = destinationTile.getTileNumber() / 8;
            int sourceFile = sourceTile.getTileNumber() % 8;
            int destinationFile = destinationTile.getTileNumber() % 8;
            if(Math.abs(destinationFile - sourceFile) == 1 && Math.abs(destinationRank - sourceRank) == 1 && destinationTile.isEmpty()){
                Tile adjacentTile = getTile(destinationTile.getTileNumber() - (8 * piece.getColor().getDirection()));
                Piece adjacentPiece = adjacentTile.getPiece();
                return adjacentPiece instanceof Pawn && adjacentPiece.getColor() != piece.getColor();
            }
        }
        return false;
    }

    //begin working on checking for check and checkmate
    public boolean isInCheck(Players player){
        int playerKingPosition = findKing(player);
        List<Piece> opponentPieces = getAllPieces(getOpponent(player));
        for(Piece piece : opponentPieces){
            List<Move> legalMoves = piece.calculateLegalMoves(this);
            for(Move move : legalMoves){
                if(move.getdestinationTile() == playerKingPosition){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isInCheckMate(Players player){
        if(!isInCheck(player)){
            return false;
        }
        List<Move> legalMoves = getAllLegalMoves(player);
        for(Move move : legalMoves){
            Board newBoard = this.clone();
            newBoard.makeMove(move.getcurrentTile(), move.getdestinationTile(), null);
            if(!newBoard.isInCheck(player)){
                return false;
            }
        }
        return true;
    }
    private int findKing(Players player){
        List<Piece> pieces = getAllPieces(player);
        for(Piece piece : pieces){
            if(piece instanceof King){
                return piece.getTileCoordinate();
            }
        }
        return -1;
    }
    private List<Piece> getAllPieces(Players player){
        List<Piece> pieces = new ArrayList<>();
        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile.isOccupied() && tile.getPiece().getColor() == player){
                    pieces.add(tile.getPiece());
                }
            }
        }
        return pieces;
    }
    private List<Move> getAllLegalMoves(Players player){
        List<Move> legalMoves = new ArrayList<>();
        List<Piece> pieces = getAllPieces(player);
        for(Piece piece : pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return legalMoves;
    }
    private Players getOpponent(Players player){
        return (player == whitePlayer) ? blackPlayer : whitePlayer;
    }
    protected Board clone(){
        Board newBoard = new Board(whitePlayer, blackPlayer);
        for(int i = 0; i < 8; i++){
            System.arraycopy(tiles[i], 0, newBoard.tiles[i], 0, 8);
        }
        return newBoard;
    }
}