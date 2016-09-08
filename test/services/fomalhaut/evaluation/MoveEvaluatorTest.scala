package test.fomalhaut.evaluation

import services.fomalhaut.Board
import services.fomalhaut.controller.BoardTextRepresentation
import services.fomalhaut.{BoardSpecialEvents, Move}
import services.fomalhaut.eva.MoveEvaluator
import services.fomalhaut.helper.{FENParser, MoveLogger}
import services.fomalhaut.pieces.{BoardTestHelper, Piece, PieceType}
import PieceType._
import org.scalatest.{FlatSpec, Ignore}

class MoveEvaluatorTest extends FlatSpec{

//  "move 1.e4 " should " be evaluated to 10 " in {
//     val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
//
//     assert(MoveEvaluator.evaluateMove(initialPosition, new Move(12,28,PieceType.PAWN,PieceType.PAWN),MoveEvaluator.MAX_DEPTH) == 10)
//  }

//  "initial position " should " give us move " in {
//    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
//
//    val move: Move = MoveEvaluator.findBestMove(initialPosition)
//    MoveLogger.logMove(move)
//  }



  "position mate in two moves" should " give move Wg8g2 " in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("6R1/8/8/8/2K5/7R/1k6/8 w - 43 0 1")
    
    val boardText : BoardTextRepresentation = new BoardTextRepresentation(initialPosition)

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(62,14,PieceType.ROOK,PieceType.ROOK)))
  }

  "In this position " should "be checkmate in one after move Wh6xh5"  in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("8/8/7R/p4Npn/6p1/7k/r7/6K1 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(47,39,PieceType.ROOK,PieceType.ROOK)))

  }

  "In this position " should "be checkmate in one after move Wf3xf4"  in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("8/8/3KNkp1/8/5r1p/5R2/8/8 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(21,29,PieceType.ROOK,PieceType.ROOK)))

  }

  "In this position " should "be checkmate in one after move Se5:g6"  in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("8/8/7p/4NR2/6pk/r6b/7K/8 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(36,46,PieceType.KNIGHT,PieceType.KNIGHT)))

  }

  "In this position " should "be checkmate in two after move f6f7"  in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("6k1/8/4pP1K/3pB2B/3P3P/8/6r1/8 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(45,53,PieceType.PAWN,PieceType.PAWN)))
  }


  "In this position " should "be checkmate in two after move Gg3"  in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("1B6/1B6/8/8/6p1/7p/8/4K1k1 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(57,22,PieceType.BISHOP,PieceType.BISHOP)))

  }

  "In this position " should "be checkmate in two after move Gc8:b7"  in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("k1B5/8/PK6/3b4/8/6B1/5p2/8 w - 43 0 1")

    val bestMove: Move = MoveEvaluator.findBestMove(initialPosition)
    assert(bestMove.equals(new Move(58,49,PieceType.BISHOP,PieceType.BISHOP)))

  }
}
