package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMove,
                       final Collection<Move> blackStandardLegalMove){
        super(board,whiteStandardLegalMove,blackStandardLegalMove);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals,
                                                   final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
                            // checking kings side castle
            if(!this.board.getTile(61).isTileOccupied() &&
               !this.board.getTile(62).isTileOccupied()){
                final Tile rookTile = this.board.getTile(63);
                            // checking if kings side rook status of first move
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                            //checking if the final castled state does not get the pieces into attacking squares
                    if(Player.calculateAttacksOnTile(61,opponentLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(62,opponentLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType().isRook()){
                        // TODO add CASTLE MOVE! -->Done
                        kingCastles.add(new Move.KingSideCastleMove(this.board,
                                                                    this.playerKing,
                                                                    62,
                                                                     (Rook) rookTile.getPiece(),
                                                                     rookTile.getTileCoordinate(),
                                                                     61));
                    }
                }
            }
                            // checking Queen side Castle
            if(!this.board.getTile(59).isTileOccupied() &&
               !this.board.getTile(58).isTileOccupied() &&
               !this.board.getTile(57).isTileOccupied() ){
                final Tile rookTile = this.board.getTile(56);
                            // checking if kings side rook status of first move
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                            //checking if the final castled state does not get the pieces into attacking squares
                    if(Player.calculateAttacksOnTile(59,opponentLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(58,opponentLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(57,opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                        // TODO add CASTLE MOVE! --> Done
                        kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                        this.playerKing,
                                        58,
                                        (Rook) rookTile.getPiece(),
                                        rookTile.getTileCoordinate(),
                                        59));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
