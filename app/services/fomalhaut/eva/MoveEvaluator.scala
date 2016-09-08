package services.fomalhaut.eva

import services.fomalhaut.helper.MoveLogger
import services.fomalhaut._
import services.fomalhaut.pieces.BoardTestHelper

object MoveEvaluator {
  
  val MAX_DEPTH : Int = 4
  
  val MAX_BLACK_VALUE = -200000
  
  val MAX_WHITE_VALUE = 200000

  var bestPathMoves : Array[Move] = Array[Move]()

  var currentlyAnalyzedPathMoves : Array[List[Move]] = Array.ofDim[List[Move]](MAX_DEPTH+1)

  var bestPathMovesMarker : Int = 0
  
  def generateBoardsList(board: Board): List[Board] = {
    board.generateAllReachableBoards()    
  }


  def findBestMove(board: Board): Move = {
    initializePathTracker()
    if(board.getBoardSpecialEvents().getColorToMove() == 0){
      findBestWhiteMove(board)
    } else {
      findBestBlackMove(board)
    }
  }

  def findBestWhiteMove(board: Board): Move = {
    val movesList: List[Move] = board.getAllMoves()
      var maxValues = MAX_BLACK_VALUE

      var moveFound : Move = null
      for (move <- movesList) {
//        val evaluated = evaluateMoveForWhite(board, move, 1)
      val (evaluated,isMated) = evaluateMoveForWhite(board,move,1) 

        if (!isMated && evaluated > maxValues) {
          maxValues = evaluated
          moveFound = move
          pushMove(move,0, evaluated)
          //        MoveLogger.logMove(move,maxValues)
        }
      }
      moveFound
  }

  def findBestBlackMove(board: Board): Move = {
    val movesList : List[Move] = board.getAllMoves()
    
    var maxValues = MAX_WHITE_VALUE
    var moveFound = movesList(1)
    for(move <- movesList){
      MoveLogger.logMove(move)
//      val evaluated = evaluateMoveForBlack(board, move, 1)
      val (evaluated,isMated) = evaluateMoveForBlack(board,move,1)

      if(!isMated && evaluated < maxValues){
        maxValues = evaluated
        moveFound = move
        pushMove(move,0, evaluated)
      }
    }
    moveFound
  }

  def evaluateMoveForBlack(board: Board, move: Move, depth : Int): (Int,Boolean) = {
    val newBoard : Board = board.generateBoardAfterMove(move)
    if(board.isKingMated()) (MAX_WHITE_VALUE, true) 
    else (findMaxValue(newBoard,depth),false)
  }

  def evaluateMoveForWhite(board: Board, move: Move, depth : Int): (Int,Boolean) = {
    val newBoard : Board = board.generateBoardAfterMove(move)
    if(board.isKingMated()) (MAX_BLACK_VALUE, true)
    else (findMinValue(newBoard,depth), false)
  }

  def findMaxValue(board: Board, depth: Int) : Int = {
    if(depth == MAX_DEPTH){
        BoardValueCalculator.calculateBoardValue(board)
    } else {
        var maxValue = MAX_BLACK_VALUE
        val movesList : List[Move] = board.getAllMoves()
        if(movesList.isEmpty){
          if(board.isWhiteKingAttacked()) {
            maxValue =  MAX_BLACK_VALUE/depth
          } else if(board.isBlackKingAttacked()){
            maxValue = MAX_WHITE_VALUE/depth
          } else maxValue = 0
        } else { 
          for(move <- movesList){
            val newBoard : Board = board.generateBoardAfterMove(move)
            val (score,isMated) = if(newBoard.isKingMated()) (MAX_BLACK_VALUE/depth, true) else (findMinValue(newBoard, depth+1), false) 
            if(!isMated && score > maxValue){
              pushMove(move,depth,score)
              maxValue = score
            }
          }
          if(maxValue == MAX_BLACK_VALUE) clear(depth)
        }
        maxValue
    }
  }


  def findMinValue(board: Board, depth: Int) : Int = {
    if(depth == MAX_DEPTH){
      BoardValueCalculator.calculateBoardValue(board)
    } else {
        var minValue : Int = MAX_WHITE_VALUE
        val movesList : List[Move] = board.getAllMoves()
        if(movesList.isEmpty) {
          if(board.isBlackKingAttacked()) {
            minValue = MAX_WHITE_VALUE / depth
          } else if(board.isWhiteKingAttacked()){
            minValue = MAX_BLACK_VALUE / depth 
          } else minValue = 0
        } else {
          for(move <- movesList){
            val newBoard : Board = board.generateBoardAfterMove(move)
            val (score, isMated) = if(newBoard.isKingMated())  (MAX_WHITE_VALUE/depth, true) else (findMaxValue(newBoard, depth+1),false)
            if(!isMated && score < minValue){
              pushMove(move,depth,score)
              minValue = score
            }
          }
          if(minValue == MAX_WHITE_VALUE) clear(depth)
        }
        minValue
    }
  }

  def initializePathTracker() = {
    for(i <- 0 to MAX_DEPTH){
      currentlyAnalyzedPathMoves(i) = List[Move]()
    }
  }


  def pushMove(move: Move, depth: Int, score: Int) = {
    if (depth == 0) {
      val top : List[Move] = move :: currentlyAnalyzedPathMoves(depth + 1) // list of moves without first
      currentlyAnalyzedPathMoves(depth) = top
      copyCurrentToBest(score)
      clear(0)
    } else if(depth == MAX_DEPTH - 1) {
         currentlyAnalyzedPathMoves(depth) = move :: List[Move]() 
    } else {
      pushInternal( move,depth,1)
    }
  }

  def clear(marker: Int) = {
    for(i <- marker to MAX_DEPTH) {
      currentlyAnalyzedPathMoves(i) = List[Move]()
    }
  }

    def pushInternal(move: Move, depth: Int, marker: Int) : Unit = {
      if(marker == depth) {
        currentlyAnalyzedPathMoves(depth) = move :: currentlyAnalyzedPathMoves(depth + 1)
        clear(depth + 1)
      } else {
        pushInternal(move,depth, marker+1)
      }
    }

  def copyCurrentToBest(score: Int) = {    
      bestPathMoves = new Array[Move](currentlyAnalyzedPathMoves(0).length)
      currentlyAnalyzedPathMoves(0).copyToArray(bestPathMoves)
  }

}
