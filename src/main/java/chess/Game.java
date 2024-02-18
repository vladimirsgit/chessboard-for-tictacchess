package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Piece[][] gameBoard = new Piece[9][9];
    private ArrayList<FullMove> fullMoves = new ArrayList<>();
    private Piece enPassantCandidate = null;

    public Game(){
        initializePawns();
        initializerestOfPieces();
    }


    public Piece[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Piece[][] gameBoard){
        this.gameBoard = gameBoard;
    }

    public List<FullMove> getFullMoves() {
        return fullMoves;
    }

    public void setFullMoves(ArrayList<FullMove> fullMoves) {
        this.fullMoves = fullMoves;
    }

    public Piece getEnPassantCandidate() {
        return enPassantCandidate;
    }

    public void setEnPassantCandidate(Piece enPassantCandidate) {
        this.enPassantCandidate = enPassantCandidate;
    }

    public void initializePawns(){
        for(int horizontal = 1; horizontal < 9; horizontal++){
            this.gameBoard[2][horizontal] = new Pawn(Piece.WHITE, "P");
            this.gameBoard[7][horizontal] = new Pawn(Piece.BLACK, "p");
        }
    }
    public void initializerestOfPieces(){
        //rooks
        this.gameBoard[1][1] = this.gameBoard[1][8] = new Rook(Piece.WHITE, "R");
        this.gameBoard[8][1] = this.gameBoard[8][8] = new Rook(Piece.BLACK, "r");
        //knights
        this.gameBoard[1][2] = this.gameBoard[1][7] = new Knight(Piece.WHITE, "N");
        this.gameBoard[8][2] = this.gameBoard[8][7] = new Knight(Piece.BLACK, "n");
        //bishops
        this.gameBoard[1][3] = this.gameBoard[1][6] = new Bishop(Piece.WHITE, "B");
        this.gameBoard[8][3] = this.gameBoard[8][6] = new Bishop(Piece.BLACK, "b");
        //kings
        this.gameBoard[1][4] = new King(Piece.WHITE, "K");
        this.gameBoard[8][4] = new King(Piece.BLACK, "k");
        //queens
        this.gameBoard[1][5] = new Queen(Piece.WHITE, "Q");
        this.gameBoard[8][5] = new Queen(Piece.BLACK, "q");
    }

    public void printGameBoard(){
        for(int i = 9; i >= 1; i--){
            for(int j = 0; j <= 8; j++){
                if(i != 9 && j == 0){
                    System.out.print(i + " ");
                    continue;
                }
                if(i == 9){
                    System.out.print(j + " ");
                    continue;
                }
                Piece currPiece = this.gameBoard[i][j];
                if(currPiece != null){
                    System.out.print(currPiece.getSymbol() + " ");
                } else System.out.print("# ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }



    public Piece getPieceAtPosition(Position position){
        return this.gameBoard[position.getVerticalCoord()][position.getHorizontalCoord()];
    }

    public void addFullMoveInFullMoveArrayList(FullMove fullMove){
        this.fullMoves.add(fullMove);
    }

    public boolean isSquareEmpty(Position position){
        return this.gameBoard[position.getVerticalCoord()][position.getHorizontalCoord()] == null;
    }
    public FullMove getLastMove(){
        return this.fullMoves.get(fullMoves.size() - 1);
    }

    public void movePieceAtCertainPosition(Position from, Position to){
        this.gameBoard[to.getVerticalCoord()][to.getHorizontalCoord()] = this.gameBoard[from.getVerticalCoord()][from.getHorizontalCoord()];
        this.gameBoard[from.getVerticalCoord()][from.getHorizontalCoord()] = null;
    }
}
