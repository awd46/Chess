package chess;

import java.util.List;
import java.util.ArrayList;

public class Piece {

    protected final int tileCoordinate;
    protected final Player color;
    
    Piece(final int tileCoordinate, final Player color){
        this.tileCoordinate = tileCoordinate;
        this.color = color;
    }
    public abstract List<Move> calculateLegalMoves(Board board);
}

class Pawn extends Piece{

    public Pawn(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
    }
    public List<Move> calculateLegalMoves(Board board){
        List<Move> legalMoves = new ArrayList<>();
        int currentTile = this.tileCoordinate;
        int direction = this.color == Player.WHITE ? 1 : -1;
        //check one square ahead
        int destinationTile = currentTile + (8 * direction);
        if(!board.getTile(destinationTile).isOccupied()){
            legalMoves.add(new Move(currentTile, destinationTile));
            //check two squares ahead if the pawn is on it's starting rank
            if((this.color == Player.WHITE && currentTile >= 9 && currentTile <= 16) || (this.color == Player.BLACK && currentTile >= 49 && currentTile <= 56)){
                destinationTile = currentTile + (16 * direction);
                if(!board.getTile(destinationTile).isOccupied()){
                    legalMoves.add(new Move(currentTile, destinationTile));
                }
            }
        }
        //check diagonals for captures
        int[] candidateOffsets = (this.color == Player.WHITE) ? new int[] {-7, -9} : new int[] {7, 9};
        for(int offset : candidateOffsets){
            destinationTile = currentTile + (offset * direction);
            if(BoardUtils.isValidTile(destinationTile)){
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

    Knight(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Rook extends Piece{

    Rook(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Bishop extends Piece{

    Bishop(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class King extends Piece{

    King(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Queen extends Piece{

    Queen(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}