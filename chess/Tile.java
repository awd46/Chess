package chess;


public abstract class Tile {

    protected int tileNumber;
    protected Piece piece;

    public Tile(int tileNumber){
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
        System.out.println("setting piece on dest tile: " + piece);
        this.piece = piece;
        /*if(piece != null){
            piece.setTileCoordinate(this.tileNumber);
        }*/
    }

    public void clearPiece(){
        System.out.println("clearing piece from this tile: " + tileNumber);
        this.piece = null;
    }

    /*public static final class EmptyTile extends Tile{
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
    }*/
    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;

        OccupiedTile(int tileNumber, Piece pieceOnTile){
            super(tileNumber);
            this.pieceOnTile = pieceOnTile;
        }
        @Override
        public boolean isOccupied(){
            return pieceOnTile != null;
        }
        @Override
        public Piece getPiece(){
            return pieceOnTile;
        }
    }
}
