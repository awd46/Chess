package chess;

public class Tile {

    protected int tileNumber;
    protected Piece piece; // null indicates that the tile is empty

    public Tile(int tileNumber, Piece piece){
        this.tileNumber = tileNumber;
        this.piece = piece;
    }

    public int getTileNumber(){
        return tileNumber;
    }

    public boolean isOccupied(){
        return piece != null;
    }

    public boolean isEmpty(){
        return piece == null;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        // If piece is null, it effectively clears the tile, making it empty.
        this.piece = piece;
    }

    public void clearPiece(){
        this.piece = null; // Clearing the piece from the tile, making it empty.
    }
}
