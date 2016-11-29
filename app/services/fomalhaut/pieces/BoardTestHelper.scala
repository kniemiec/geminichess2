package services.fomalhaut.pieces

import services.fomalhaut.{Board, BoardSpecialEvents, Move}

/**
 * Created by kniemiec on 10.03.16.
 */
object BoardTestHelper {

  def compareUnnormalizedLists(expected: List[Move], result: List[Move]): Boolean = {
    compareNormalizedLists(normalizeMovesList(expected),normalizeMovesList(result))
  }

  def compareNormalizedLists(expected: List[Move], result: List[Move]): Boolean = expected match {
    case Nil => result == Nil
    case h :: t => (h.from == result.head.from) &&  (h.to == result.head.to) && compareNormalizedLists(t, result.tail)
  }

  def printListofInt(list: List[Int]){
    for( item <- list){
      print("("+item+")")
    }
    println("")
  }

  def printList(list: List[Move]){

    for( item <- normalizeMovesList(list)){
      printMove(item)
    }
  }

  def printMove(move: Move) = {
    println("("+move.from+","+move.to+")")
  }
  def normalizeMovesList(movesList: List[Move]): List[Move] = {
    movesList.sortWith(compareMoves)
  }

  def compareMoves(l: Move, r: Move): Boolean = {
    (l.from < r.from) || (l.from == r.from && l.to < r.to)
  }

  def printBoard(board: Board) = {
    println("White: ")
    printPieceColor(board.getWhitePiecesPosition())
    println("Black: ")
    printPieceColor(board.getBlackPiecesPosition())
  }

  def getBoardString(board: Board) : String = {
    var s = "";
    s = s +"White: "
    s = s + board.getWhitePiecesPosition().toString()
    s = s + "Black: "
    s = s + board.getBlackPiecesPosition().toString()
    s
  }



  def printPieceColor(pieces: List[Piece]) = {
    for(piece <- pieces){
      print("Piece: "+piece.getPieceType()+" ")
      printListofInt(piece.getPositions())
    }
  }

  def deepBoardCopy(board: Board, context: BoardSpecialEvents) : Board = {
    val whitePieces : List[Piece] = copyPiecesAndSubstituteContext(board.getWhitePiecesPosition(), context)
    val blackPieces : List[Piece] = copyPiecesAndSubstituteContext(board.getBlackPiecesPosition(),context)

    new Board(whitePieces,blackPieces,context)
  }



  def copyPiecesAndSubstituteContext(piecesList: List[Piece],context: BoardSpecialEvents): List[Piece] = {
    piecesList.map( replaceContext(_,context))
  }

  def replaceContext(piece: Piece,context: BoardSpecialEvents) : Piece = piece.getPieceType() match{
      case PieceType.QEEN =>  new Queen(piece.getPositions(),context)
      case PieceType.KING => new King(piece.getPositions(),context)
      case PieceType.ROOK => new Rook(piece.getPositions(),context)
      case PieceType.BISHOP => new Bishop(piece.getPositions(),context)
      case PieceType.KNIGHT => new Knight(piece.getPositions(),context)
      case PieceType.PAWN => new Pawn(piece.getPositions(),context)
      case PieceType.BLACK_PAWN => new BlackPawn(piece.getPositions(),context)
  }
}
