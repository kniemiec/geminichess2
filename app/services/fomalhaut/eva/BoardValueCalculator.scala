package services.fomalhaut.eva

import services.fomalhaut.Board
import services.fomalhaut.pieces.{PieceType, Pawn, BlackPawn, Piece}
import services.fomalhaut.pieces.King

object BoardValueCalculator {


  val LIST_OF_VALUATION_FUNCTIONS = List( piecesPower(_), calculatePawnsAdvancementValue(_), calculateKingsBalance(_) )
  
  
  def executeValuation(valFunc: (Board) => Int, a:Board): Int = {
    valFunc(a)    
  }
  
  private def calculatePiecesValue(piece: Piece): Int = {
    if(piece.getPositions().nonEmpty){
      piece.getPositions().map((X:Int ) => piece.getValue()).reduce(_ + _)
    } else {
      0
    }
  }
  
  private def calculatePawnAdvancementValue(piece: Piece, color: Int): Int = {
    if(piece.getPositions().nonEmpty){
      piece.getPositions().map((X:Int ) => BoardValuationPatterns.getPawnAdvancementValuesPattern(X, color)).reduce(_ + _)
    } else {
      0
    }
  }

  private def getKingPosition(board: Board, color: Int): List[List[Int]] = {
    if(color == 0){
      board.getWhitePiecesPosition().filter(_.getPieceType() == PieceType.KING).map(_.getPositions())
    } else {
      board.getBlackPiecesPosition().filter(_.getPieceType() == PieceType.KING).map(_.getPositions())
    }
  }

  
  private def calculatePawnsAdvancementValue(board: Board): Int = {
    val white: List[Piece] = board.getWhitePiecesPosition()
    val black: List[Piece] = board.getBlackPiecesPosition()
    
     white.filter((piece: Piece) => piece.isInstanceOf[Pawn] ).map(calculatePawnAdvancementValue(_,0)).reduce(_ + _) - 
       black.filter((piece: Piece) => piece.isInstanceOf[BlackPawn] ).map(calculatePawnAdvancementValue(_,1)).reduce(_ + _)
  }



  
  private def piecesPower(board: Board): Int = {
    val white: List[Piece] = board.getWhitePiecesPosition()
    val black: List[Piece] = board.getBlackPiecesPosition()
    
    white.map(calculatePiecesValue(_)).reduce(_ + _) - black.map(calculatePiecesValue(_)).reduce(_ + _)
  }

  private def isKingOnBoard(board: Board, color: Int) : Int = {
    val kingPosition: List[Int] =  getKingPosition(board,color).head
    val multiplicationFactor : Int = 1 - 2 * color
    if(kingPosition.size > 0 ) multiplicationFactor * 100000
    else 0
  }

  def calculateKingsBalance(board: Board) : Int = {
    List(0, 1).map(isKingOnBoard(board, _)).reduce(_ + _)
  }


  def calculateBoardValue(board: Board): Int = {
    LIST_OF_VALUATION_FUNCTIONS.map(executeValuation(_, board)).reduce(_+_)
  }

}