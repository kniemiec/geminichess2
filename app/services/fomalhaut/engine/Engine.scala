package services.fomalhaut.engine

import services.fomalhaut.Board
import services.fomalhaut.Move
import services.fomalhaut.eva.MoveEvaluator
import services.fomalhaut.controller.MoveConverter

import services.fomalhaut.helper.FENParser

/**
 * Created by kniemiec on 11.02.16.
 */
class Engine(val initialFenData : String) {

  val board : Board = FENParser.parseFENDescriptiontToBoard(initialFenData)

  def validateMove( move: Move) : Boolean = {
    val allMoves : List[Move] = board.getAllMoves()
    allMoves.contains(move)
  }

  def getResponseMove(move: Move) = {

    val newBoard : Board = getBoardAfterUserMove(move)
    val bestMove : Move = MoveEvaluator.findBestMove(newBoard)
    val finalBoard : Board = newBoard.getBoardAfterMove(bestMove)
    val finalFen : String = FENParser.saveBoardtoFENDescription(finalBoard)
    (bestMove.toString, finalFen)
  }


  def getBoardAfterUserMove(userMove: Move): Board = {
    return board.generateBoardAfterMove(userMove)
  }

  def isGameFinished(board: Board): Boolean = {
    false
  }

  def getNewBoard(board: Board): Board = {
    board.generateBoardAfterMove(MoveEvaluator.findBestMove(board))
  }



}
