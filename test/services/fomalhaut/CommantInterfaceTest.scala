package test.fomalhaut

import services.fomalhaut.Move
import services.fomalhaut.controller.{CommandInterface, IncorrectCommand, MoveConverter}
import services.fomalhaut.pieces.{BoardTestHelper, PieceType}
import org.scalatest.FlatSpec


/**
 * Created by kniemiec on 18.02.16.
 */
class CommantInterfaceTest extends FlatSpec{

  "Move d2d4 " should " be valid " in {
    val resultMove: Move = MoveConverter.validateCommand("d2d4")
    BoardTestHelper.printMove(resultMove)
    assert(resultMove.equals(new Move(11,27,PieceType.PAWN,PieceType.PAWN)))
  }


  "Move Sg1f3 " should " be valid " in {
    val resultMove: Move = MoveConverter.validateCommand("Sg1f3")
    BoardTestHelper.printMove(resultMove)
    assert(resultMove.equals(new Move(6,21,PieceType.KNIGHT,PieceType.KNIGHT)))
  }

  "Move Gf1a6 " should " be valid " in {
    val resultMove: Move = MoveConverter.validateCommand("Gf1a6")
    BoardTestHelper.printMove(resultMove)
    assert(resultMove.equals(new Move(5,40,PieceType.BISHOP,PieceType.BISHOP)))
  }


  "Move Sg8f6 " should " be valid " in {
    val resultMove: Move = MoveConverter.validateCommand("Sg8f6")
    BoardTestHelper.printMove(resultMove)
    assert(resultMove.equals(new Move(62,45,PieceType.KNIGHT,PieceType.KNIGHT)))
  }

  "Move d7d8 " should " has missing destination piece " in {
    intercept[IncorrectCommand] {
      val resultMove: Move = MoveConverter.validateCommand("d7d8")
      BoardTestHelper.printMove(resultMove)
    }
//    assert(resultMove.equals(new Move(51,59,PieceType.PAWN,PieceType.QEEN)))
  }

  "Move d7d8H " should " is valid promotion move" in {
    val resultMove: Move = MoveConverter.validateCommand("d7d8H")
    BoardTestHelper.printMove(resultMove)
    assert(resultMove.equals(new Move(51,59,PieceType.PAWN,PieceType.QEEN)))
  }
}
