package test.fomalhaut

import services.fomalhaut.helper.FENParser
import services.fomalhaut.{Board, Move}
import org.scalatest.FlatSpec


/**
 * @author kniemiec
 */
class FENParserTest extends FlatSpec{

  val INITIAL_FEN : String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0"

  val E4_E6_FEN : String = "rnbqkbnr/pppp1ppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 1 1"

  val TWO_KINGS_FEN : String = "3k4/8/8/8/8/8/8/4K3 w - - 121 18"

  val TWO_R_K_FEN : String = "3k4/3r4/8/8/8/8/4R3/4K3 w - - 121 18"

  val WITH_ENPASSANT_FEN : String = "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e6 0 0"

  "An initial position " should " give such Board" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

//    printListofInt(board.fieldsOccupiedByWhite)
//    printListofInt(board.fieldsOccupiedByBlack)
//
//
//    printList(board.getAllWhiteMoves().flatten)
  }


  "An initial board " should " give such FEN result " in {
    val initialBoard: Board = FENParser.parseFENDescriptiontToBoard(INITIAL_FEN)
    val resultFen: String = FENParser.saveBoardtoFENDescription(initialBoard)
    println(resultFen)
    assert(resultFen.equals(INITIAL_FEN))
    val nextBoard : Board = FENParser.parseFENDescriptiontToBoard(resultFen)
    val nextFen : String = FENParser.saveBoardtoFENDescription(nextBoard)
    assert(nextFen.equals(INITIAL_FEN))
  }


  "A board after 1.e2e4 e7e6 " should " give such FEN result " in {
    val initialBoard: Board = FENParser.parseFENDescriptiontToBoard(E4_E6_FEN)
    val resultFen: String = FENParser.saveBoardtoFENDescription(initialBoard)
    assert(resultFen.equals(E4_E6_FEN))
    val nextBoard : Board = FENParser.parseFENDescriptiontToBoard(resultFen)
    val nextFen : String = FENParser.saveBoardtoFENDescription(nextBoard)
    assert(nextFen.equals(E4_E6_FEN))
  }

  "A board with two kings  " should " give two kings " in {
    val initialBoard: Board = FENParser.parseFENDescriptiontToBoard(TWO_KINGS_FEN)
    val resultFen: String = FENParser.saveBoardtoFENDescription(initialBoard)
    assert(resultFen.equals(TWO_KINGS_FEN))
  }

  "A board with king rook pairs  " should " give board king rook pairs " in {
    val initialBoard: Board = FENParser.parseFENDescriptiontToBoard(TWO_R_K_FEN)
    val resultFen: String = FENParser.saveBoardtoFENDescription(initialBoard)
    assert(resultFen.equals(TWO_R_K_FEN))
  }


  "A board with enpassant " should " board with enpassant " in {
    val initialBoard: Board = FENParser.parseFENDescriptiontToBoard(WITH_ENPASSANT_FEN)
    val resultFen: String = FENParser.saveBoardtoFENDescription(initialBoard)
    assert(resultFen.equals(WITH_ENPASSANT_FEN))
  }


  private def printListofInt(list: List[Int]){
    for( item <- list){
      println("("+item+")")
    }
  }

  private def printList(list: List[Move]){
    for( item <- list){
      println("("+item.from+","+item.to+")")
    }
  }
}
