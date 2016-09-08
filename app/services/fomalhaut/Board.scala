package services.fomalhaut

import services.fomalhaut.pieces._



class Board (white: List[Piece], black: List[Piece], var boardSpecialEvents: BoardSpecialEvents) {


  val whitePiecesPosition: List[Piece] = white
  val blackPiecesPosition: List[Piece] = black

  val fieldsOccupiedByWhite: List[Int] = calculateOccupiedFields(whitePiecesPosition)
  val fieldsOccupiedByBlack: List[Int] = calculateOccupiedFields(blackPiecesPosition)

  val fieldsAttackedByWhite: List[Int] = generateFieldsAttackedByWhite()
  val fieldsAttackedByBlack: List[Int] = generateFieldsAttackedByBlack()


  def setBoardSpecialEvents(context: BoardSpecialEvents) = {
    boardSpecialEvents = context
  }

  def getBoardSpecialEvents(): BoardSpecialEvents = {
    boardSpecialEvents
  }

  def generateFieldsAttackedByWhite(): List[Int] = {
    whitePiecesPosition.map{
      (piece: Piece) => {
        piece.positions.map{
          position => piece.getAttackedFields(position,fieldsOccupiedByBlack,fieldsOccupiedByWhite)
        }.flatten
      }
    }.flatten
  }

  def generateFieldsAttackedByBlack(): List[Int] = {
    blackPiecesPosition.map{
      (piece: Piece) => {
        piece.positions.map{
          position => piece.getAttackedFields(position,fieldsOccupiedByWhite,fieldsOccupiedByBlack)
        }.flatten
      }
    }.flatten
  }

  def getBoardAfterMove(move: Move): Board = {
    generateBoardAfterMove(move)
  }

  def generateBoardAfterMove(move: Move): Board = {
    val newBoardContext:BoardSpecialEvents = boardSpecialEvents.produceContextAfterMove(move)
    if(isShortCastlingMove(move)){
      if(boardSpecialEvents.getColorToMove() == 0){
        val afterKing: List[Piece] = movePieces(whitePiecesPosition,move,newBoardContext)
        val white: List[Piece] = movePieces(afterKing,new Move(7,5,PieceType.ROOK,PieceType.ROOK),newBoardContext)
        new Board(white,blackPiecesPosition,newBoardContext)
      } else {
        val afterKing: List[Piece] = movePieces(blackPiecesPosition,move,newBoardContext)
        val black: List[Piece] = movePieces(afterKing,new Move(63,61,PieceType.ROOK,PieceType.ROOK),newBoardContext)
        new Board(whitePiecesPosition,black,newBoardContext)
      }
    } else if(isLongCastlingMove(move)){
      if(boardSpecialEvents.getColorToMove() == 0){
        val afterKing: List[Piece] = movePieces(whitePiecesPosition,move,newBoardContext)
        val white: List[Piece] = movePieces(afterKing,new Move(0,3,PieceType.ROOK,PieceType.ROOK),newBoardContext)
        new Board(white,blackPiecesPosition,newBoardContext)
      } else {
        val afterKing: List[Piece] = movePieces(blackPiecesPosition,move,newBoardContext)
        val black: List[Piece] = movePieces(afterKing,new Move(56,59,PieceType.ROOK,PieceType.ROOK),newBoardContext)
        new Board(whitePiecesPosition,black,newBoardContext)
      }
    } else if(isPawnPromotionMove(move)){
      if(boardSpecialEvents.getColorToMove() == 0){
        val withoutPawn: List[Piece] = removePieces(whitePiecesPosition,new Move(move.from,move.from,move.who,move.what),newBoardContext)
        val white: List[Piece] = addPieces(withoutPawn,move,newBoardContext)
        new Board(white,blackPiecesPosition,newBoardContext)
      } else {
        val withoutPawn: List[Piece] = removePieces(blackPiecesPosition,new Move(move.from,move.from,move.who,move.what),newBoardContext)
        val black: List[Piece] = addPieces(withoutPawn,move,newBoardContext)
        new Board(whitePiecesPosition,black,newBoardContext)
      }
    } else if(isEnPassantMove(move)){
      if(boardSpecialEvents.getColorToMove() == 0){
        val white: List[Piece] = movePieces(whitePiecesPosition,move,newBoardContext)
        val black: List[Piece] = removePieces(blackPiecesPosition,new Move(move.to,move.to-8,PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),newBoardContext)
        new Board(white,black,newBoardContext)
      } else {
        val white: List[Piece] = removePieces(whitePiecesPosition,new Move(move.to,move.to+8,PieceType.PAWN,PieceType.PAWN),newBoardContext)
        val black: List[Piece] = movePieces(blackPiecesPosition,move,newBoardContext)
        new Board(white,black,newBoardContext)
      }
    } else {
      if(boardSpecialEvents.getColorToMove() == 0){
        val black: List[Piece] = removePieces(blackPiecesPosition,move,newBoardContext)
        val white: List[Piece] = movePieces(whitePiecesPosition,move,newBoardContext)
        new Board(white,black,newBoardContext)
      } else {
        val white: List[Piece] = removePieces(whitePiecesPosition,move,newBoardContext)
        val black: List[Piece] = movePieces(blackPiecesPosition,move,newBoardContext)
        new Board(white,black,newBoardContext)
      }
    }
  }

  private def isShortCastlingMove(move: Move): Boolean  = {
    move.who == PieceType.KING && (move.to - move.from == 2)
  }

  private def isLongCastlingMove(move: Move): Boolean  = {
    move.who == PieceType.KING && (move.from - move.to == 3)
  }


  private def isPawnPromotionMove(move: Move):Boolean = {
    (move.who == PieceType.BLACK_PAWN && move.to / 8 == 0) ||
      (move.who == PieceType.PAWN && move.to / 8 == 7)
  }

  private def isEnPassantMove(move: Move): Boolean = {
    (move.who == PieceType.BLACK_PAWN && !((fieldsOccupiedByBlack ::: fieldsOccupiedByWhite).contains(move.to))) ||
      (move.who == PieceType.PAWN && !((fieldsOccupiedByBlack ::: fieldsOccupiedByWhite).contains(move.to)))
  }

  def generateAllReachableBoards(): List[Board] = {
    val allPossibleMoves : List[Move] = getAllMoves()
    val allAttackedFields : List[Int] = allPossibleMoves.groupBy(_.to).map(_._2.head).map(_.to).toList
    allPossibleMoves.map(generateBoardAfterMove(_))
  }

  def getWhitePiecesPosition(): List[Piece] = {
    whitePiecesPosition
  }

  def getBlackPiecesPosition(): List[Piece] = {
    blackPiecesPosition
  }

  def movePieces(pieces: List[Piece], move: Move, boardContext: BoardSpecialEvents): List[Piece] = {
    for(piece <- pieces) yield movePiece(piece, move,boardContext)
  }

  def removePieces(pieces: List[Piece], move: Move, boardContext: BoardSpecialEvents): List[Piece] = {
    for(piece <- pieces) yield removePiece(piece, move,boardContext)
  }

  def addPieces(pieces: List[Piece], move: Move, boardContext: BoardSpecialEvents): List[Piece] = {
    pieces.map{
      (piece: Piece)  => {
        if(piece.getPieceType() == move.what) addPiece(piece,boardContext, piece.getPositions() :::  List(move.to))
        else addPiece(piece,boardContext,piece.getPositions())
      }
    }
  }

  private def addPiece(piece: Piece,boardContext: BoardSpecialEvents, newPositions: List[Int]): Piece = piece match {
    case Queen(list,context) => new Queen(newPositions,boardContext)
    case Rook(list,context) => new Rook(newPositions,boardContext)
    case Bishop(list,context) => new Bishop(newPositions,boardContext)
    case Knight(list,context) => new Knight(newPositions,boardContext)
    case King(list,context) => new King(newPositions,boardContext)
    case Pawn(list,context) => new Pawn(newPositions,boardContext)
    case BlackPawn(list,context) => new BlackPawn(newPositions,boardContext)
  }

  private def removePiece(piece: Piece, move: Move, boardContext: BoardSpecialEvents): Piece = piece match {
    case Queen(list,context) => new Queen(piece.getPositions().filter(_ != move.to),boardContext)
    case King(list,context) => new King(piece.getPositions().filter(_ != move.to),boardContext)
    case Rook(list,context) => new Rook(piece.getPositions().filter(_ != move.to),boardContext)
    case Bishop(list,context) => new Bishop(piece.getPositions().filter(_ != move.to),boardContext)
    case Knight(list,context) => new Knight(piece.getPositions().filter(_ != move.to),boardContext)
    case Pawn(list,context) => new Pawn(piece.getPositions().filter(_ != move.to),boardContext)
    case BlackPawn(list,context) => new BlackPawn(piece.getPositions().filter(_ != move.to),boardContext)
  }


  private def movePiece(piece: Piece, move: Move, boardContext: BoardSpecialEvents): Piece = piece match {
    case Queen(list,context) => new Queen(
      piece.positions.map((from: Int) => if(from == move.from) move.to else from),boardContext)
    case King(list,context) => new King(
      piece.positions.map((from: Int) => if(from == move.from) move.to else from),boardContext)
    case Rook(list,context) => new Rook(
      piece.getPositions().map((from: Int) => if(from == move.from) move.to else from),boardContext)
    case Bishop(list,context) => new Bishop(
      piece.positions.map((from: Int) => if(from == move.from) move.to else from),boardContext)
    case Knight(list,context) => new Knight(
      piece.positions.map((from: Int) => if(from == move.from) move.to else from),boardContext)
    case Pawn(list,context) => new Pawn(
      piece.getPositions().map((from: Int) => {if(from == move.from) move.to else from}),boardContext)
    case BlackPawn(list,context) => new BlackPawn(
      piece.getPositions().map((from: Int) => {if(from == move.from) move.to else from}),boardContext)
  }

  def calculateOccupiedFields(piecesList: List[Piece]) : List[Int] =  {
    var result: List[Int] = List()
    for(pieceType <- piecesList){
      result = result ::: pieceType.positions.toList
    }
    result
  }

  private def prepareNewList(positions: List[Int], move: Move): List[Int] = positions match {
    case Nil => Nil
    case head :: tail => if(head == move.from) move.to :: tail else head :: prepareNewList(tail, move)
  }

  def isKingMated(): Boolean = {
    if(boardSpecialEvents.colorToMove == 0){
      isBlackKingAttacked()
    } else {
      isWhiteKingAttacked()
    }
  }

  def isWhiteKingAttacked(): Boolean = {
    val kingPosition : List[Piece] =
      for { whitePiece <- whitePiecesPosition if (whitePiece.getPieceType() == PieceType.KING)}
        yield whitePiece

    val position: Int = kingPosition.head.getPositions().head
    fieldsAttackedByBlack.contains(position)
  }

  def isBlackKingAttacked(): Boolean = {
    val kingPosition : List[Piece] =
      for { piece <- blackPiecesPosition if (piece.getPieceType() == PieceType.KING)}
        yield piece
    val position: Int = kingPosition.head.getPositions().head
    fieldsAttackedByWhite.contains(position)
  }

  def getAllWhiteMoves(): List[List[Move]] = {
    if(isBlackKingAttacked()) Nil
    else {
      val result = whitePiecesPosition.map {
        (piece: Piece) => {
          piece.positions.map {
            position => piece.getAllMoves(position, fieldsOccupiedByBlack, fieldsOccupiedByWhite, fieldsAttackedByBlack)
          }
        }
      }
      if (result.isEmpty) Nil
      else result.flatten
    }
  }

  def getAllBlackMoves(): List[List[Move]] = {
    if(isWhiteKingAttacked()) Nil
    val result  = blackPiecesPosition.map{
      (piece: Piece) => {
        piece.positions.map{
          position => piece.getAllMoves(position,fieldsOccupiedByWhite,fieldsOccupiedByBlack,fieldsAttackedByWhite)
        }
      }
    }
    if(result.isEmpty) Nil
    else result.flatten
  }

  def getAllMoves(): List[Move] = {
    if(boardSpecialEvents.getColorToMove() == 0){
      val allWhiteMoves =  getAllWhiteMoves()
      if(allWhiteMoves == Nil || allWhiteMoves.isEmpty) List[Move]()
      else allWhiteMoves.flatten
    } else {
      val allBlackMoves = getAllBlackMoves()
      if(allBlackMoves == Nil || allBlackMoves.isEmpty) List[Move]()
      else allBlackMoves.flatten
    }
  }

}
