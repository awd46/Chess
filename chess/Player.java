package chess;

public class Player {
    
    private char teamColor;

    public Player(char teamColor){
        this.teamColor = teamColor;
    }

    public char getColor(){
        return teamColor;
    }
    
}
