package services.fomalhaut.engine

import org.scalatest.FlatSpec
import services.fomalhaut.Board
import services.fomalhaut.controller.MoveConverter
import services.fomalhaut.helper.FENParser
import services.fomalhaut.pieces.BoardTestHelper

/**
  * Created by kniemiec on 29.09.16.
  */
class EngineTest   extends FlatSpec{

  val INITIAL_POSITION : String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

  "In initial position " should " e2e4 should be valid move" in {
    val engine = new Engine(INITIAL_POSITION)
    assert(engine.validateMove(MoveConverter.convertTextToMove("e2e4")))

  }

  "In initial position " should " e2e5 should be invalid move" in {
    val engine = new Engine(INITIAL_POSITION)
    assert(!engine.validateMove(MoveConverter.convertTextToMove("e2e5")))

  }

  "In initial position " should " Ke1e2 should be invalid move" in {
    val engine = new Engine(INITIAL_POSITION)
    assert(!engine.validateMove(MoveConverter.convertTextToMove("Ke1e2")))

  }


}
