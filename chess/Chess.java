// Alex Dodson and Matthew Lara
package chess;

import java.util.ArrayList;
import java.util.List;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black, BLACK, WHITE }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	private static final int WHITE_TURN = 0;
	private static final int BLACK_TURN = 1;
	private static int turn = WHITE_TURN;
	private static Board board;

	public static ReturnPlay play(String move) {
		ReturnPlay returnPlay = new ReturnPlay();
		Players currentPlayer = getCurrentPlayer(turn);
		System.out.println("Move : " + move);
		if(Coordinates.isResignMove(move)){
			returnPlay.message = (currentPlayer == Players.WHITE) ? ReturnPlay.Message.RESIGN_WHITE_WINS : ReturnPlay.Message.RESIGN_BLACK_WINS;
			return returnPlay;
		}
		if(Coordinates.isDrawRequest(move)){
			returnPlay.message = (currentPlayer == Players.WHITE) ? ReturnPlay.Message.DRAW : ReturnPlay.Message.DRAW;
			return returnPlay;
		}
		// Parse the move
		Object[] parsedMove = Coordinates.parseMove(move);
		int sourceSquare = (int) parsedMove[0];
		int destinationSquare = (int) parsedMove[1];
		String promotionPieceType = (String) parsedMove[2];
		System.out.println("Source square : " + sourceSquare);
		System.out.println("dest square : " + destinationSquare);
		// Get the tile object for the source square
		Tile sourceTile = board.getTile(sourceSquare);
		Tile destTile = board.getTile(destinationSquare);
		System.out.println("source tile is occupied" + sourceTile.isOccupied());
		System.out.println("Piece on source tile : " + sourceTile.getPiece());
		// Check if the move is legal
		Piece piece = sourceTile.getPiece();
		System.out.println("Piece color : " + (piece != null ? piece.getColor().toString() : "null"));
		if (piece == null){
			System.out.println("Piece is null");
			returnPlay.piecesOnBoard = ChessUtils.getPiecesOnBoard(board, currentPlayer);
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return returnPlay;
		}
		if(piece.getColor() != currentPlayer){
			System.out.println("invalid color");
			returnPlay.piecesOnBoard = ChessUtils.getPiecesOnBoard(board, currentPlayer);
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return returnPlay;
		}
		// Check if the move is valid for the piece
		List<Move> legalMoves = piece.calculateLegalMoves(board);
		boolean isValidMove = false;
		for (Move legalMove : legalMoves) {
			if (legalMove.getdestinationTile() == destinationSquare) {
				isValidMove = true;
				break;
			}
		}
		if (!isValidMove && !board.isCastlingMove(sourceTile, destTile) && !board.isEnPassantMove(sourceTile, destTile)) {
			System.out.println("Invalid move");
			returnPlay.piecesOnBoard = ChessUtils.getPiecesOnBoard(board, currentPlayer);
			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return returnPlay;
		}
		// Make the move on the board

		board.makeMove(sourceSquare, destinationSquare, move ,promotionPieceType);

		Players opponent = Players.BLACK;

		// Check for check, checkmate, stalemate
		
		//getting opponent player to check for check or mate
		if(currentPlayer == Players.WHITE)
		{
			opponent = Players.BLACK;
		}
		else if(currentPlayer == Players.BLACK)
		{
			opponent = Players.WHITE;
		}
		
		if (board.isInCheck(opponent)) {
			returnPlay.message = ReturnPlay.Message.CHECK;
			if (board.isInCheckMate(opponent)) {
				returnPlay.message = (currentPlayer == Players.WHITE) ? ReturnPlay.Message.CHECKMATE_WHITE_WINS : ReturnPlay.Message.CHECKMATE_BLACK_WINS;
			}
		} else if (board.isInCheckMate(opponent)) {
			returnPlay.message = (currentPlayer == Players.WHITE) ? ReturnPlay.Message.CHECKMATE_WHITE_WINS : ReturnPlay.Message.CHECKMATE_BLACK_WINS;
		}
		// Update the turn
		turn = (turn == WHITE_TURN) ? BLACK_TURN : WHITE_TURN; 
		// Populate the pieces on board in the return play
		returnPlay.piecesOnBoard = ChessUtils.getPiecesOnBoard(board, currentPlayer);


		return returnPlay;
	}
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		turn = WHITE_TURN;
		Players whitePlayer = Players.WHITE;
		Players blackPlayer = Players.BLACK;
		board = new Board(whitePlayer, blackPlayer);
	}

	private static Players getCurrentPlayer(int turn){
        return (turn == WHITE_TURN) ? Players.WHITE : Players.BLACK;
    }
}