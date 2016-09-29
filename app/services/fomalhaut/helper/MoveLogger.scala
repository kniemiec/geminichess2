package services.fomalhaut.helper

import services.fomalhaut.Move
import services.fomalhaut.pieces.PieceType
import services.fomalhaut.pieces.PieceType.PieceType

/**
 * Created by kniemiec on 06.01.16.
 */
object MoveLogger {

    val columnsList   = Array('a','b','c','d','e','f','g','h')

  def logMove(move: Move, value: Int) ={
      val colFrom = move.from % 8
      val rowFrom = move.from / 8 + 1
      val colTo = move.to % 8
      val rowTo = move.to / 8 + 1
      println(columnsList(colFrom).toString()+rowFrom.toString+":"+columnsList(colTo).toString()+rowTo.toString()+"->"+value)
    }

  def logMove(move: Move) ={
    val colFrom = move.from % 8
    val rowFrom = move.from / 8 + 1
    val colTo = move.to % 8
    val rowTo = move.to / 8 + 1
    println(columnsList(colFrom).toString()+rowFrom.toString+":"+columnsList(colTo).toString()+rowTo.toString())
  }

  def getFigureSymbol(who: PieceType) : String = who match{
      case PieceType.KNIGHT  => "N"
      case PieceType.KING => "K"
      case PieceType.BISHOP => "B"
      case PieceType.QEEN => "Q"
      case PieceType.ROOK => "R"
      case _ => ""
  }

  def convertToMove(move: Move): String = {
    val colFrom = move.from % 8
    val rowFrom = move.from / 8 + 1
    val colTo = move.to % 8
    val rowTo = move.to / 8 + 1
    val result = getFigureSymbol(move.who)+ columnsList(colFrom).toString()+rowFrom.toString+
        columnsList(colTo).toString()+rowTo.toString() + (if(move.who != move.what) getFigureSymbol(move.what) else "")
    result
  }



  def printPathEvaluation(moveList: Array[Move], moveValue : Int) = {
    for(move <- moveList if move != null){
      print(convertToMove(move))
      print(" ")
    }
    println(moveValue)
  }
  
  def printPathEvaluation(moveList: List[Move], moveValue : Int) = {
    moveList.foreach{ (move: Move ) => print(convertToMove(move))}
  }  
}
