package chess;

public class Notation {

    public static int[] parseMove(String move){
        String[] moveParts = move.split(" ");
        String sourceSquare = moveParts[0];
		String destSquare = moveParts[1];

		//convert the notation "e3" to coordinates (4,2)
		int sourceRank = parseRank(sourceSquare.charAt(1));
		int sourceFile = parseFile(sourceSquare.charAt(0));
		int destRank = parseRank(destSquare.charAt(1));
		int destFile = parseFile(destSquare.charAt(0));
		
        return new int[]{sourceRank, sourceFile, destRank, destFile};
    }
    private static int parseRank(char rankChar){
		return Character.getNumericValue(rankChar)- 1;
	}
	private static int parseFile(char fileChar){
		return fileChar - 'a';
	}
    
}
