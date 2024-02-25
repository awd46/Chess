package chess;

import java.util.ArrayList;

public class ChessUtils {
    public static ReturnPiece.PieceType getPieceType(Piece piece, Players player) {
        if (piece instanceof Pawn) {
            return piece.getColor() == player ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
        } else if (piece instanceof Rook) {
            return piece.getColor() == player ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
        } else if (piece instanceof Knight) {
            return piece.getColor() == player ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
        } else if (piece instanceof Bishop) {
            return piece.getColor() == player ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
        } else if (piece instanceof Queen) {
            return piece.getColor() == player ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        } else if (piece instanceof King) {
            return piece.getColor() == player ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
        }
        return null; // Ideally, this should never happen
    }

    public static ArrayList<ReturnPiece> getPiecesOnBoard(Board board, Players player){
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Tile tile = board.getTile(i * 8 + j + 1);
                if(tile.isOccupied()){
                    Piece piece = tile.getPiece();
                    ReturnPiece returnPiece = new ReturnPiece();
                    returnPiece.pieceType = getPieceType(piece, player);
                    returnPiece.pieceFile = ReturnPiece.PieceFile.values()[j];
                    returnPiece.pieceRank = i + 1;
                    pieces.add(returnPiece);
                }
            }
        }
        return pieces;
    }
}
