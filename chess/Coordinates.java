package chess;

public class Coordinates {

	public static int parseSquare(String square){
		//convert chess notation to square number 1-64
		int file = square.charAt(0) - 'a' +1;
		int rank = Character.getNumericValue(square.charAt(1));
		return(8 - rank) * 8 + file;
	}

    public static int[] parseMove(String move){
		//splits the notation into the sourcesquare and the destsquare
        String[] moveParts = move.split(" ");
		int sourceSqure = parseSquare(moveParts[0]);
		int destSquare = parseSquare(moveParts[1]);
		return new int[]{sourceSqure, destSquare};
    }
    
}
