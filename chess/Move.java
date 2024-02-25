package chess;

public class Move {

    private final int currentTile;
    private final int destinationTile;

    public Move(int currentTile, int destinationTile){
        this.currentTile = currentTile;
        this.destinationTile = destinationTile;
    }

    public int getcurrentTile(){
        return currentTile;
    }

    public int getdestinationTile(){
        return destinationTile;
    }
    
}

