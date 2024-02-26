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

    private void initializeBoard() {
        // Initialize black pieces
        tiles[0][0] = new Tile(1, new Rook(1, blackPlayer));
        tiles[0][1] = new Tile(2, new Knight(2, blackPlayer));
        tiles[0][2] = new Tile(3, new Bishop(3, blackPlayer));
        tiles[0][3] = new Tile(4, new Queen(4, blackPlayer));
        tiles[0][4] = new Tile(5, new King(5, blackPlayer));
        tiles[0][5] = new Tile(6, new Bishop(6, blackPlayer));
        tiles[0][6] = new Tile(7, new Knight(7, blackPlayer));
        tiles[0][7] = new Tile(8, new Rook(8, blackPlayer));
        for (int file = 0; file < 8; file++) {
            tiles[1][file] = new Tile((1 * 8) + (file + 1), new Pawn((1 * 8) + (file + 1), blackPlayer));
        }

        // Initialize white pieces
        tiles[7][0] = new Tile(57, new Rook(57, whitePlayer));
        tiles[7][1] = new Tile(58, new Knight(58, whitePlayer));
        tiles[7][2] = new Tile(59, new Bishop(59, whitePlayer));
        tiles[7][3] = new Tile(60, new Queen(60, whitePlayer));
        tiles[7][4] = new Tile(61, new King(61, whitePlayer));
        tiles[7][5] = new Tile(62, new Bishop(62, whitePlayer));
        tiles[7][6] = new Tile(63, new Knight(63, whitePlayer));
        tiles[7][7] = new Tile(64, new Rook(64, whitePlayer));
        for (int file = 0; file < 8; file++) {
            tiles[6][file] = new Tile((6 * 8) + (file + 1), new Pawn((6 * 8) + (file + 1), whitePlayer));
        }

        // Initialize empty tiles for the middle of the board
        for (int rank = 2; rank < 6; rank++) {
            for (int file = 0; file < 8; file++) {
                tiles[rank][file] = new Tile((rank * 8) + (file + 1), null);
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
            sourceTile.clearPiece();
            destinationTile.setPiece(piece);
            piece.setTileCoordinate(destinationTileNumber);
            this.printBoardState();
        }else if(isCastlingMove(sourceTile, destinationTile)){
            handleCastling(sourceTileNumber, destinationTileNumber);
        }else if(isEnPassantMove(sourceTile, destinationTile)){
            handleEnPassant(sourceTileNumber, destinationTileNumber);
        }else if (piece instanceof Pawn &&(destinationTileNumber <= 8 || destinationTileNumber >= 57)){
            String promotionPieceType = Coordinates.parsePromotionPiece(move);
            if(promotionPieceType!= null){
                handlePawnPromotion(sourceTileNumber, destinationTileNumber, promotionPieceType);
            }else{
                handlePawnPromotion(sourceTileNumber, destinationTileNumber, "q");
            }
        }
    }

    public void handleCastling(int sourceTileNumber, int destinationTileNumber){
        System.out.println("made it to castling");
        Tile sourceTile = getTile(sourceTileNumber);
        Tile destinationTile = getTile(destinationTileNumber);
        Piece king = sourceTile.getPiece();
        if(!(king instanceof King) || !isCastlingMove(sourceTile, destinationTile)){
            return;
        }
        int rank = (sourceTileNumber - 1) / 8;
        Tile rookSourceTile, rookDestinationTile;
        if(destinationTileNumber % 8 == 6){
            rookSourceTile = getTile(8 * rank + 7);
            rookDestinationTile = getTile(8 * rank + 5);
        }else{
            rookSourceTile = getTile(8 * rank);
            rookDestinationTile = getTile(8 * rank + 3);
        }
        Piece rook = rookSourceTile.getPiece();
        sourceTile.clearPiece();
        destinationTile.setPiece(king);
        king.setTileCoordinate(destinationTileNumber);
        rookSourceTile.clearPiece();
        rookDestinationTile.setPiece(rook);
        rook.setTileCoordinate(rookDestinationTile.getTileNumber());
        this.printBoardState();
    }
    
    public void handleEnPassant(int sourceTileNumber, int destinationTileNumber){
        System.out.println("made it to enpassant");
        Tile sourceTile = getTile(sourceTileNumber);
        Tile destinationTile = getTile(destinationTileNumber);
        Piece pawn = sourceTile.getPiece();
        if(!(pawn instanceof Pawn) || !isEnPassantMove(sourceTile, destinationTile)){
            return;
        }
        int capturedPawnTileNumber = destinationTileNumber + (pawn.getColor().getDirection() * -8);
        Tile capturedPawnTile = getTile(capturedPawnTileNumber);
        capturedPawnTile.clearPiece();
        this.printBoardState();
    }

    public void handlePawnPromotion(int sourceTileNumber, int destinationTileNumber, String promotionPieceType){
        System.out.println("made it to pawn promotion");
        Tile destinationTile = getTile(destinationTileNumber);
        Piece pawn = destinationTile.getPiece();
        if(!(pawn instanceof Pawn)){
            return;
        }
        Piece newPiece;
        switch(promotionPieceType.toLowerCase()){
            case "q":
                newPiece = new Queen(destinationTileNumber, pawn.getColor());
                break;
            case "r":
                newPiece = new Rook(destinationTileNumber, pawn.getColor());
                break;
            case "n":
                newPiece = new Knight(destinationTileNumber, pawn.getColor());
                break;
            case "b":
                newPiece = new Bishop(destinationTileNumber, pawn.getColor());
                break;
            default:
                newPiece = new Queen(destinationTileNumber, pawn.getColor());
                break;
        }
        destinationTile.setPiece(newPiece);
        this.printBoardState();
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
        System.out.println("source : " + sourceFile + "Dest file : " + destinationFile);
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
            rookTile = getTile(8 * rank + 7);
        }else{
            rookTile = getTile(8 * rank);
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

    public void printBoardState() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                String pieceRepresentation = (tile.isOccupied()) ? tile.getPiece().getClass().getSimpleName().substring(0, 2) + tile.getPiece().getColor().toString().charAt(0) : "##";
                System.out.print(pieceRepresentation + " ");
            }
            System.out.println(8 - i); // Print row number at the end
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
}