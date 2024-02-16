package chess;

public class Pieces {

    protected final int tileCoordinate;
    protected final Player color;
    
    Pieces(final int tileCoordinate, final Player color){
        this.tileCoordinate = tileCoordinate;
        this.color = color;
    }

}

class Pawn extends Pieces{

    Pawn(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Knight extends Pieces{

    Knight(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Rook extends Pieces{

    Rook(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Bishop extends Pieces{

    Bishop(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class King extends Pieces{

    King(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}
class Queen extends Pieces{

    Queen(int tileCoordinate, Player color) {
        super(tileCoordinate, color);
        //TODO Auto-generated constructor stub
    }

}