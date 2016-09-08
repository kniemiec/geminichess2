package services.fomalhaut.helper

import services.fomalhaut.pieces.PieceType
import services.fomalhaut.pieces._
import PieceType._
import services.fomalhaut.{Board, BoardSpecialEvents}


object FENParser {

  val whiteSymbols = "KQRBNP_"
  val blackSymbols = "kqrbn_p"

//    var whitePieces: List[Piece] = Nil
//    var blackPieces: List[Piece] = Nil
  var globalContext: BoardSpecialEvents = new BoardSpecialEvents(0,true,true,true,true,0,0,1)

    val WHITE_REDUCE_PATTERN = List( PieceType.QEEN,PieceType.KING,PieceType.ROOK,PieceType.BISHOP,PieceType.KNIGHT,PieceType.PAWN)
    val BLACK_REDUCE_PATTERN = List( PieceType.QEEN,PieceType.KING,PieceType.ROOK,PieceType.BISHOP,PieceType.KNIGHT,PieceType.BLACK_PAWN)


  val SEPARATOR : String = " "


  def unfoldNumbers(c: Char): List[Char] = {
    if(whiteSymbols.contains(c) || blackSymbols.contains(c)) List(c)
    else if(c == '/') List()
    else (for( i <-  1.to(c.toInt-'1'.toInt+1)) yield '1').toList
  }

  def parseFENDescriptiontToBoard(fen: String): Board = {
    val piecesPosition: Array[String] = fen.split(" ") // @TODO - tutaj moze byc VAL?
    val boardContext: BoardSpecialEvents = calculateContext(piecesPosition(1),piecesPosition(2),piecesPosition(3),piecesPosition(4),piecesPosition(5))
    globalContext = boardContext;

  //  initializeEmptyBoard(boardContext)

    //val figures =
    val fullFigures  =  piecesPosition(0).map( c => unfoldNumbers(c)).flatten
//    for((c, index) <- fullFigures.zipWithIndex){
//      updateBoard(c, index, boardContext)
//    }


    val whitePieces  : List[Piece] = (for((c, index) <- fullFigures.zipWithIndex if whiteSymbols.contains(c))
      yield createPieceFromIndex(whiteSymbols.indexOf(c),index,boardContext)
    ).toList

    val blackPieces  : List[Piece] = (for((c, index) <- fullFigures.zipWithIndex if blackSymbols.contains(c))
      yield createPieceFromIndex(blackSymbols.indexOf(c),index,boardContext)
    ).toList

    val whiteOnMove = calculateWhiteOnMove(piecesPosition(1)) // chyba tutaj nie ma nowego kontekstu - po co jest wogole ta linia
    val result: Board = new Board(WHITE_REDUCE_PATTERN.map(filterPiecesByType(_,whitePieces)),
              BLACK_REDUCE_PATTERN.map(filterPiecesByType(_,blackPieces)),
              boardContext)
    result
  }

  def reducePiecesByType(p1: Piece, p2: Piece) : Piece = p1 match {
    case Queen(positions,context) => new Queen(p1.getPositions() ::: p2.getPositions(),globalContext)
    case King(positions,context) => new King(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Rook(positions,context) => new Rook(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Bishop(positions,context) => new Bishop(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Knight(positions,context) => new Knight(p1.getPositions() ::: p2.getPositions(),globalContext)
    case Pawn(positions,context) => new Pawn(p1.getPositions() ::: p2.getPositions(),globalContext)
    case BlackPawn(positions,context) => new BlackPawn(p1.getPositions() ::: p2.getPositions(),globalContext)
  }

  def createEmptyPieceByType(pieceType: PieceType) : Piece = pieceType match {
    case PieceType.QEEN => new Queen(List(),globalContext)
    case PieceType.KING=> new King(List(),globalContext)
    case PieceType.ROOK => new Rook(List(),globalContext)
    case PieceType.BISHOP => new Bishop(List(),globalContext)
    case PieceType.KNIGHT => new Knight(List(),globalContext)
    case PieceType.PAWN => new Pawn(List(),globalContext)
    case PieceType.BLACK_PAWN => new BlackPawn(List(),globalContext)
  }


  def filterPiecesByType(pieceType: PieceType, piecesList: List[Piece]) : Piece = {
    val result : Option[Piece] = piecesList.filter( _.getPieceType() == pieceType).reduceOption(reducePiecesByType(_,_))
    result.getOrElse(createEmptyPieceByType(pieceType))
//    res.get
  }


//  def initializeEmptyBoard(context: BoardSpecialEvents) = {
//    whitePieces = List( Queen(List(),context),King(List(),context),Rook(List(),context),Bishop(List(),context),Knight(List(),context),Pawn(List(),context))
//    blackPieces = List( Queen(List(),context),King(List(),context),Rook(List(),context),Bishop(List(),context),Knight(List(),context),BlackPawn(List(),context))
//  }

  def calculateContext(onMove: String,castlingS: String, enpassant: String, fullMoves: String, halfMoves: String): BoardSpecialEvents = {
    val whiteOnMove:Int = calculateWhiteOnMove(onMove);
    val isShortCastlingAllowedForWhite = castlingS.indexOf("K") > -1
    val isLongCastlingAllowedForWhite = castlingS.indexOf("Q") > -1
    val isLongCastlingAllowedForBlack = castlingS.indexOf("q") > -1
    val isShortCastlingAllowedFoBlack = castlingS.indexOf("k") > -1
    var enPassantMove = 0
    if(enpassant == "-"){
      enPassantMove = 0;
    } else {
      enPassantMove = convertFromField(enpassant)
    }
    val full = fullMoves.toInt
    val half = halfMoves.toInt
    new BoardSpecialEvents(whiteOnMove,isShortCastlingAllowedForWhite,isLongCastlingAllowedForWhite,
        isShortCastlingAllowedFoBlack,isLongCastlingAllowedForBlack,enPassantMove,full,half)
  }



  def addWhoseMove(context: BoardSpecialEvents): String = {
      if(context.getColorToMove() == 0)  s"w"
      else s"b"
  }


  def addCastlings(context: BoardSpecialEvents): String = {
    val shortWhite : String = if(context.isShortCastlingAllowedForWhite) "K" else ""
    val longWhite: String = if(context.isLongCastlingAllowedForWhite) "Q" else ""
    val shortBlack : String = if(context.isShortCastlingAllowedForBlack) "k" else ""
    val longBlack: String = if(context.isLongCastlingAllowedForBlack) "q" else ""
    val result: String = shortWhite+longWhite+shortBlack+longBlack
    if(result.isEmpty)  s"-" else result
  }

  def convertFromField(field: String): Integer= {
    val row : Int = field.charAt(0).toInt-97
    val line : Int = field.substring(1).toInt
    line*8+row
  }

  def convertToField(move: Int): String = {
    val row : Int = move % 8 + 97
    val line : Int = move / 8
    row.asInstanceOf[Char]+line.toString
  }

  def addEnPassant(context: BoardSpecialEvents): String = {
    if(context.enPassantMove == 0) "-"
    else convertToField(context.enPassantMove)
  }

  def halfMoves(context : BoardSpecialEvents) : String = {
    context.halfMoves.toString
  }

  def fullMoves(context : BoardSpecialEvents) : String = {
    context.fullMoves.toString
  }

  def decodeContext(context: BoardSpecialEvents) : (String, String, String, String, String) = {
    (addWhoseMove(context), addCastlings(context), addEnPassant(context), halfMoves(context), fullMoves(context))
  }



  def calculateWhiteOnMove(c: String): Int = {
    if(c == "w") 0
    else 1
  }



//  def updateBoard(c: Char, index : Int, context: BoardSpecialEvents) = {
//      val pos = (7 - index / 8) * 8 + index % 8 // calculate valid FEN position based on index - just a trick
//      if(whiteSymbols.contains(c)){
//        whitePieces = whitePieces ::: List(createPieceFromIndex(whiteSymbols.indexOf(c),pos,context))
//      } else if(blackSymbols.contains(c)){
//        blackPieces = blackPieces ::: List(createPieceFromIndex(blackSymbols.indexOf(c),pos,context))
//      }
//  }

  def calculatePiecesPlacementFENPart(whitePiecesList:  List[(Int,PieceType)], blackPiecesList :  List[(Int,PieceType)]): String = {
    var i : Int = 0
    var whiteList : List[(Int,PieceType)] = whitePiecesList
    var blackList : List[(Int,PieceType)] = blackPiecesList
    var rowCounter = 0
    var lineCounter = 0
    var emptyLength = 0
    var result: String = ""
    var resultLine: String = ""
    for (i <- 0.until(64)) {
      if (i % 8 == 0 && i > 0) {
        if (emptyLength > 0) resultLine = resultLine + emptyLength.toString
        if( lineCounter >  0)resultLine = resultLine + "/"
        result = resultLine + result
        resultLine = ""
        emptyLength = 0
        rowCounter = 0
        lineCounter = lineCounter + 1
      }
      val whiteHead = whiteList.headOption.getOrElse(null)
      val blackHead = blackList.headOption.getOrElse(null)
      if (whiteHead != null && whiteHead._1 == lineCounter * 8 + rowCounter) {
        if (emptyLength > 0) resultLine = resultLine + emptyLength.toString
        resultLine = resultLine + whiteSymbols.charAt(createIndexFromPiece(whiteHead._2))
        whiteList = whiteList.tail
        emptyLength = 0
      } else if (blackHead != null && blackHead._1 == lineCounter * 8 + rowCounter) {
        if (emptyLength > 0) resultLine = resultLine + emptyLength.toString
        resultLine = resultLine + blackSymbols.charAt(createIndexFromPiece(blackHead._2))
        blackList = blackList.tail
        emptyLength = 0
      } else {
        emptyLength = emptyLength + 1
      }
      rowCounter = rowCounter + 1
    }
    if(emptyLength>0) resultLine = resultLine + emptyLength.toString
    result = resultLine + "/" + result
    result
  }


  def calculatePiecesPlacement(board: Board): String = {
    var whiteFieldPieceList : List[(Int,PieceType)] =
      board.getWhitePiecesPosition().map((piece : Piece) => { piece.getPositions().map( (_, piece.getPieceType())) }).flatten.sortWith( _._1 < _._1)
    var blackFieldPieceList : List[(Int,PieceType)]=
      board.getBlackPiecesPosition().map((piece : Piece) => { piece.getPositions().map( (_, piece.getPieceType())) }).flatten.sortWith( _._1 < _._1)
    calculatePiecesPlacementFENPart(whiteFieldPieceList,blackFieldPieceList)
  }

  def saveBoardtoFENDescription(board: Board): String = {
    var result : String = ""
    val ( whoseMove : String, castlings: String,  enPassant: String, halfMoves : String, fullMoves : String)
      = decodeContext(board.getBoardSpecialEvents())
    val piecesPlacement : String = calculatePiecesPlacement(board)

    piecesPlacement + SEPARATOR + whoseMove + SEPARATOR + castlings + SEPARATOR + enPassant + SEPARATOR + halfMoves + SEPARATOR + fullMoves
  }

  def createPieceFromIndex(pieceIndex: Int, index: Int, context: BoardSpecialEvents): Piece = {
    val pos = (7 - index / 8) * 8 + index % 8 // calculate valid FEN position based on index - just a trick
    pieceIndex match {
      case 0 => new King(List(pos),context)
      case 1 => new Queen(List(pos),context)
      case 2 => new Rook(List(pos),context)
      case 3 => new Bishop(List(pos),context)
      case 4 => new Knight(List(pos),context)
      case 5 => new Pawn(List(pos),context)
      case 6 => new BlackPawn(List(pos),context)
    }
  }

  def createIndexFromPiece(pieceType: PieceType): Int = {
    pieceType match {
      case KING => 0
      case QEEN => 1
      case ROOK => 2
      case BISHOP => 3
      case KNIGHT => 4
      case PAWN => 5
      case BLACK_PAWN => 6
    }
  }


}
