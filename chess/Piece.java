package chess;

import java.util.List;
import java.util.ArrayList;

public abstract class Piece {

    protected int tileCoordinate;
    protected final Players color;
    private boolean hasMoved = false;

    public boolean hasMoved(){
        return this.hasMoved;
    }

    public void move(){
        this.hasMoved = true;
    }

    public void setMoves(boolean hasMoved){
        this.hasMoved = hasMoved;
    }
    
    public Piece(int tileCoordinate, Players color){
        this.tileCoordinate = tileCoordinate;
        this.color = color;
    }
    
    public int getTileCoordinate(){
        return tileCoordinate;
    }

    public Players getColor(){
        return color;
    }

    public abstract List<Move> calculateLegalMoves(Board board);
    
    public void setTileCoordinate(int tileCoordinate){
        System.out.println("Updating piece's tile coorsinate: " + tileCoordinate);
        this.tileCoordinate = tileCoordinate;
    }

}

class Pawn extends Piece{

    public Pawn(int tileCoordinate, Players color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int direction = this.color == Players.BLACK ? 1 : -1;
        //check one square ahead
        int destinationTile = currentTile + (8 * direction);
        if(!board.getTile(destinationTile).isOccupied()){
            legalMoves.add(new Move(currentTile, destinationTile));
            //check two squares ahead if the pawn is on it's starting rank
            if((this.color == Players.BLACK && currentTile >= 9 && currentTile <= 16) || (this.color == Players.WHITE && currentTile >= 49 && currentTile <= 56)){
                destinationTile = currentTile + (16 * direction);
                if(!board.getTile(destinationTile).isOccupied()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }
            }
        }
        //check diagonals for captures
        int[] candidateOffsets = (this.color == Players.BLACK) ? new int[] {-7, -9} : new int[] {7, 9};
        for(int offset : candidateOffsets){
            destinationTile = currentTile + (offset * direction);
            if(Board.isValidTile(destinationTile)){
                Tile destinationTileObj = board.getTile(destinationTile);
                if(destinationTileObj.isOccupied()){
                    Piece pieceOnDestination = destinationTileObj.getPiece();
                    if(pieceOnDestination.color != this.color){
                        legalMoves.add(new Move(currentTile, destinationTile));
                    }
                }
            }
        }
        return legalMoves;
    }
}

class Knight extends Piece{

    Knight(int tileCoordinate, Players color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int[] candidateOffsets = {-17, -15, -10, -6, 6, 10, 15, 17};
        for(int offset : candidateOffsets){
            int destinationTile = currentTile + offset;
            if(Board.isValidTile(destinationTile)){
                Tile destinationTileObj = board.getTile(destinationTile);
                if(!destinationTileObj.isOccupied() || destinationTileObj.getPiece().getColor() != this.color){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }
            }
        }
        return legalMoves;
    }
}

class Rook extends Piece{

    Rook(int tileCoordinate, Players color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int[] candidateOffsets = {-8, 8, -1, 1};
        for(int offset : candidateOffsets){
            int destinationTile = currentTile;
            while(Board.isValidTile(destinationTile + offset)){
                destinationTile += offset;
                Tile destinationTileObj = board.getTile(destinationTile);
                if(!destinationTileObj.isOccupied()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }else{
                    Piece pieceOnDestination = destinationTileObj.getPiece();
                    if(pieceOnDestination.getColor() != this.getColor()){
                        legalMoves.add(new Move(currentTile, destinationTile));
                    }
                    break;
                }
            }
        }
        return legalMoves;
    }
}

class Bishop extends Piece{

    Bishop(int tileCoordinate, Players color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int[] candidateOffsets = {-9, -7, 7, 9};
        for(int offset : candidateOffsets){
            int destinationTile = currentTile;
            while(Board.isValidTile(destinationTile + offset)){
                destinationTile += offset;
                Tile destinationTileObj = board.getTile(destinationTile);
                if(!destinationTileObj.isOccupied()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }else{
                    Piece pieceOnDestination = destinationTileObj.getPiece();
                    if(pieceOnDestination.getColor() != this.getColor()){
                        legalMoves.add(new Move(currentTile, destinationTile));
                    }
                    break;
                }
            }
        }
        return legalMoves;
    }
}

class King extends Piece{

    King(int tileCoordinate, Players color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int[] candidateOffsets = {-9, -8, -7, -1, 1, 7, 8, 9};
        for(int offset : candidateOffsets){
            int destinationTile = currentTile + offset;
            if(Board.isValidTile(destinationTile)){
                Tile destinationTileObj = board.getTile(destinationTile);
                if(!destinationTileObj.isOccupied() || destinationTileObj.getPiece().getColor() != this.getColor()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }
            }
        }
        return legalMoves;
    }
}

class Queen extends Piece{

    Queen(int tileCoordinate, Players color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int[] horizontalOffsets = {-8, 8, -1, 1};
        int[] diagonalOffsets = {-9, -7, 7, 9};
        for(int offset : horizontalOffsets){
            int destinationTile = currentTile + offset;
            while(Board.isValidTile(destinationTile)){
                Tile destinationTileObj = board.getTile(destinationTile);
                if(!destinationTileObj.isOccupied()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }else{
                    Piece pieceOnDestination = destinationTileObj.getPiece();
                    if(pieceOnDestination.getColor() != this.getColor()){
                        legalMoves.add(new Move(currentTile, destinationTile));
                    }
                    break;
                }
                destinationTile += offset;
            }
        }
        for(int offset : diagonalOffsets){
            int destinationTile = currentTile;
            while(Board.isValidTile(destinationTile + offset)){
                destinationTile += offset;
                Tile destinationTileObj = board.getTile(destinationTile);
                if(!destinationTileObj.isOccupied()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }else{
                    Piece pieceOnDestination = destinationTileObj.getPiece();
                    if(pieceOnDestination.getColor() != this.getColor()){
                        legalMoves.add(new Move(currentTile, destinationTile));
                    }
                    break;
                }
            }
        }
        return legalMoves;
    }
}