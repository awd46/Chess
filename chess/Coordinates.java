package chess;

public class Coordinates {

	public static int parseSquare(String square){
		//convert chess notation to square number 1-64
		int file = square.charAt(0) - 'a' + 1;
		int rank = Character.getNumericValue(square.charAt(1));
		return (8 - rank) * 8 + file;
	}

    public static Object[] parseMove(String move){
		//splits the notation into the sourcesquare and the destsquare
		move = move.trim();
        String[] moveParts = move.split(" ");
		int sourceSqure = parseSquare(moveParts[0]);
		int destSquare = parseSquare(moveParts[1]);
		String promotionPieceType = null;
		if (moveParts.length > 2){
			promotionPieceType = parsePromotionPiece(move);
		}
		return new Object[]{sourceSqure, destSquare, promotionPieceType};
    }
	public static String parsePromotionPiece(String move){
		if(move.length() > 5 && Character.isLetter(move.charAt(5))){
			return move.substring(5);
		}
		return null;
	}
	public static boolean isResignMove(String move){
		return move.trim().equalsIgnoreCase("resign");
	}
	public static boolean isDrawRequest(String move){
		return move.trim().toLowerCase().endsWith("draw?");
	} 
}
