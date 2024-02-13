package chess;

public abstract class Tile {
    int tileCoordinate;

    Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Pieces getPiece();

    public static final class EmptyTile extends Tile{
        EmptyTile(int coordinate){
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
        Pieces pieceOnTile;

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
