package chess;

public abstract class Tile {

    protected final int tileNumber;
    private Piece piece;

    Tile(int tileNumber){
        this.tileNumber = tileNumber;
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
        this.piece = piece;
    }

    public void clearPiece(){
        this.piece = null;
    }

    public static final class EmptyTile extends Tile{
        EmptyTile(final int tileNumber){
            super(tileNumber);
        }
        @Override
        public boolean isOccupied(){
            return false;
        }
        @Override
        public Piece getPiece(){
            return null;
        }
    }
    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;

        OccupiedTile(int tileNumber, Piece pieceOnTile){
            super(tileNumber);
            this.pieceOnTile = pieceOnTile;
        }
        @Override
        public boolean isOccupied(){
            return true;
        }
        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
    }
}
