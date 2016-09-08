package test.fomalhaut.evaluation


import services.fomalhaut.helper.FENParser
import org.scalatest.FlatSpec

import services.fomalhaut._
import services.fomalhaut.eva.BoardValueCalculator;

class BoardValueCalculatortest extends FlatSpec{
"initial position" should " give value 0 " in {
     val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
     assert(BoardValueCalculator.calculateBoardValue(initialPosition) == 0)
  }

"position without black pawn" should " give value 100 " in {
     val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/ppp1pppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
     assert(BoardValueCalculator.calculateBoardValue(initialPosition) == 100)
  }

"position after e4" should " give value 10 " in {
     val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq 43 0 1")
     assert(BoardValueCalculator.calculateBoardValue(initialPosition) == 20)
  }

}