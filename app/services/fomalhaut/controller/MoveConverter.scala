package services.fomalhaut.controller

import services.fomalhaut.pieces.{PieceType, Bishop}
import services.fomalhaut.Move
import PieceType.PieceType

object MoveConverter {


  val ZERO_NUMBER : Int = 49

  val ZERO_LETTER : Int = 97

  val PIECE_TYPE_MARK : Int = 65

  val QUEEN_NUMBER: Int = 72
  val KING_NUMBER: Int = 75
  val BISHOP_NUMBER: Int = 71
  val ROOK_NUMBER: Int = 87
  val KNIGHT_NUMBER: Int = 83
  val PAWN_NUMBER: Int = 80



  def convertTextToMove(userCommand: String): Move = {

    validateCommand(userCommand)
  }

  def validateCommand(userCommand: String): Move = {
    if(userCommand.length < 4) throw new IncorrectCommand("Incorrect move description")
    if(userCommand.length > 6) throw new IncorrectCommand("Incorrect move description")
    val userCommandAsChar : Array[Char] = userCommand.toCharArray()
    val fromColumn : Int = userCommandAsChar(0).toInt - ZERO_LETTER
    println(fromColumn)
    if(fromColumn < 0 || fromColumn > 7 ){
      // Piece
      val pieceType : PieceType = getPieceType(userCommandAsChar(0).toInt)
      val fromColumn : Int = userCommandAsChar(1).toInt - ZERO_LETTER
      validateRange(fromColumn)
      val fromRow : Int = userCommandAsChar(2).toInt - ZERO_NUMBER
      validateRange(fromRow)
      val toColumn : Int = userCommandAsChar(3).toInt - ZERO_LETTER
      validateRange(toColumn)
      val toRow : Int = userCommandAsChar(4).toInt - ZERO_NUMBER
      validateRange(toRow)
      new Move(fromRow*8+fromColumn,toRow*8+toColumn,pieceType,pieceType)
    } else {
      // Pawn
      validateRange(fromColumn)
      val fromRow : Int = userCommandAsChar(1).toInt - ZERO_NUMBER
      validateRange(fromRow)
      val toColumn : Int = userCommandAsChar(2).toInt - ZERO_LETTER
      validateRange(toColumn)
      val toRow : Int = userCommandAsChar(3).toInt - ZERO_NUMBER
      validateRange(toRow)
      if(toRow == 7){
        if(userCommandAsChar.length != 5) throw new IncorrectCommand("missing destination Piece in promotion move")
        val pieceType : PieceType = getPieceType(userCommandAsChar(4).toInt)

        new Move(fromRow*8+fromColumn,toRow*8+toColumn,PieceType.PAWN,pieceType)
      } else {
        new Move(fromColumn+fromRow*8,toRow*8+toColumn,PieceType.PAWN,PieceType.PAWN)
      }
    }
  }

  def validateRange(fromColumn: Int) = {
    if(fromColumn< 0 || fromColumn > 7) throw new IncorrectCommand("Incorrect move description")
  }

  private def getPieceType(toInt: Int): PieceType = {
    val pieceType = toInt match {
      case QUEEN_NUMBER => PieceType.QEEN
      case KING_NUMBER => PieceType.KING
      case BISHOP_NUMBER => PieceType.BISHOP
      case ROOK_NUMBER => PieceType.ROOK
      case KNIGHT_NUMBER => PieceType.KNIGHT
      case default => throw new IncorrectCommand("Incorrect piece type")
    }
    pieceType
  }
}