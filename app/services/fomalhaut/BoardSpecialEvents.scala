package services.fomalhaut

import services.fomalhaut.pieces.PieceType

class BoardSpecialEvents(val colorToMove: Int,
                          val isShortCastlingAllowedForWhite: Boolean,
                          val isLongCastlingAllowedForWhite: Boolean,
                          val isShortCastlingAllowedForBlack: Boolean,
                          val isLongCastlingAllowedForBlack: Boolean,
                          val enPassantMove: Int,
                         val halfMoves: Int,
                         val fullMoves: Int) {


  val EN_PASSANT_NOT_ALLOWED: Int = 0
  
  def getEnPassantMove(): Int = {
    return enPassantMove
  }

  def getColorToMove(): Int = {
    colorToMove
  }
  
  def isWhiteKingAttacked(): Boolean = {
    false
  }
  
  def isBlackKingAttacked(): Boolean = {
    false
  }

  private def enPassangAfterMove(move: Move, color: Int):Int = {
    if(move.what == PieceType.PAWN || move.what == PieceType.BLACK_PAWN){
      if(color == 0){
        if(move.to - move.from == 16 ) move.to - 8
        else 0
      } else {
        if(move.from - move.to == 16) move.to + 8
        else 0      
      }
    } else 0
  }
  
  private def isCastlingAllowed(move: Move, rookLocation: Int): Boolean = {
    if(move.what == PieceType.KING){
      false
    } else if(move.what == PieceType.ROOK && move.from == rookLocation){
      false
    } else {
      true
    }
  }
  
  
  def produceContextAfterMove(move: Move): BoardSpecialEvents = {
    new BoardSpecialEvents(
        1-colorToMove,
        isShortCastlingAllowedForWhite && isCastlingAllowed(move,7),
        isLongCastlingAllowedForWhite && isCastlingAllowed(move,0),
        isShortCastlingAllowedForBlack && isCastlingAllowed(move,63),
        isLongCastlingAllowedForBlack && isCastlingAllowed(move,56),
        enPassangAfterMove(move, colorToMove),
      halfMoves+1,
      fullMoves + 1-colorToMove )
  }

}