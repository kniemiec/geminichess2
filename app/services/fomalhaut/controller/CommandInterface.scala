package services.fomalhaut.controller


import services.fomalhaut.{Move, Board}


/**
 * Created by kniemiec on 11.02.16.
 */
class CommandInterface {


  def displayBoard(board: Board) = {
    val boardTest: BoardTextRepresentation = new BoardTextRepresentation(board)
    boardTest.printBoard()
  }


  def getUserMove(): Move = {
    val userCommand = scala.io.StdIn.readLine()
    MoveConverter.convertTextToMove(userCommand)
  }

  def gameFinished(board: Board) = {

  }

  def playGame() = {

    val gameController: GameController = new GameController()
    var currentBoard: Board = gameController.getInitialBoard()
    displayBoard(currentBoard)
    while(!gameController.isGameFinished(currentBoard)){
      val userMove: Move = getUserMove()
      var newBoard : Board = gameController.getBoardAfterUserMove(currentBoard,userMove)
      displayBoard(newBoard)
      if(!gameController.isGameFinished(newBoard)) {        
        newBoard = gameController.getNewBoard(newBoard)
        println("response calculated")
      }
      currentBoard = newBoard
      displayBoard(currentBoard)
    }
//    gameFinished(currentBoard)
  }

}
