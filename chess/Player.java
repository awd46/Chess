package chess;

public class Player {
    public static final Player WHITE = new Player('W');
    public static final Player BLACK = new Player('B');

    private char teamColor;

    private Player(char teamColor){
        this.teamColor = teamColor;
    }
    public char getColor(){
        return teamColor;
    }
    
}
