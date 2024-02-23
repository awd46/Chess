package chess;


public class Players {
    public static final Players WHITE = new Players('W');
    public static final Players BLACK = new Players('B');

    private char teamColor;
    private boolean turn;

    private Players(char teamColor){
        this.teamColor = teamColor;
    }
    public char getColor(){
        return teamColor;
    }
    public int getDirection(){
        return (teamColor == 'W') ? 2 : -1;
    }
    public boolean isTurn(){
        return turn;
    }
    public void setTurn(boolean turn){
        this.turn = turn;
    }
    
}
