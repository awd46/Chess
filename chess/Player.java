package chess;


public class Player {
    public static final Player WHITE = new Player('W');
    public static final Player BLACK = new Player('B');

    private char teamColor;
    private boolean turn;

    private Player(char teamColor){
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
