package chess;

public abstract class Tile {

    protected final int tileCoordinate;

    Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Pieces getPiece();

    public static final class EmptyTile extends Tile{
        EmptyTile(final int coordinate){
            super(coordinate);
        }
        @Override
        public boolean isTileOccupied(){
            return false;
        }
        @Override
        public Pieces getPiece(){
            return null;
        }
    }
    public static final class OccupiedTile extends Tile{
        private final Pieces pieceOnTile;

        OccupiedTile(int tileCoordinate, Pieces pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }
        @Override
        public boolean isTileOccupied(){
            return true;
        }
        @Override
        public Pieces getPiece(){
            return this.pieceOnTile;
        }
    }
}
