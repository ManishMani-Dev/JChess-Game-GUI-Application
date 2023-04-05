package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {7,8,9,16};

    public Pawn(final Alliance pieceAlliance,final int piecePosition) {
        super(PieceType.PAWN,piecePosition, pieceAlliance,true);
    }

    public Pawn(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.PAWN,piecePosition, pieceAlliance,isFirstMove);
    }
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE){
            final int candidateDestinationCoordinate =this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }
            if(currentCandidateOffset ==8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                //TODO more work to do here {deal with promotion} !!! ---> completed

                if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                    legalMoves.add(new PawnPromotion(new PawnMove(board,this,candidateDestinationCoordinate)));
                }else{
                    legalMoves.add(new PawnMove(board,this,candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                        ( (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                            (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()) ) ) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()*8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                   !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new PawnJump(board,this,candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset ==7 &&
                        !( (BoardUtils.EIGHTH_COLOUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                           (BoardUtils.FIRST_COLOUMN[this.piecePosition]  && this.pieceAlliance.isBlack()) ) ){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        // TODO more to do here {attacking into pawn promotion}---> completed
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate)));
                        }else{
                            legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
                        }
                    }
                }
                else if( board.getEnPassantPawn() != null){
                    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))){
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new PawnEnPassantAttackMove(board,this, candidateDestinationCoordinate,pieceOnCandidate));
                        }
                    }
                }
            } else if (currentCandidateOffset ==9 &&
                    !( (BoardUtils.FIRST_COLOUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                        (BoardUtils.EIGHTH_COLOUMN[this.piecePosition]  && this.pieceAlliance.isBlack()) ) ){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        // TODO more to do here {attacking into pawn promotion}---> completed
                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate)));
                        }else{
                            legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
                        }
                    }
                }
                else if( board.getEnPassantPawn() != null){
                    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))){
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                            legalMoves.add(new PawnEnPassantAttackMove(board,this, candidateDestinationCoordinate,pieceOnCandidate));
                        }
                    }

                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().pieceAlliance, move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }
    public Piece getPromotionPiece(){
        return new Queen(this.pieceAlliance,this.piecePosition,false);
    }
}
