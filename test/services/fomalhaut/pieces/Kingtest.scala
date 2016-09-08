package test.fomalhaut.pieces

import services.fomalhaut.{BoardSpecialEvents, Move}
import services.fomalhaut.pieces.King
import org.scalatest.FlatSpec

class Kingtest extends FlatSpec {
  "King from b1  " should " attack fields " in {
    val king = new King(List(1),new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(king.getAttackedFields(1,Nil,Nil) == List(0,2,8,9,10))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }  
  
  "King from b1 with occupied c1 and c2 " should " attack fields " in {
    val king = new King(List(1),new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(king.getAttackedFields(1,List(2),List(10)) == List(0,2,8,9,10))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

  "King from b1  " should " go to  fields a1, a2, b2, c1,c2" in {
    val king = new King(List(1),new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(king.getAllMoves(1,Nil,Nil).map((m:Move) => m.to) == List(0,2,8,9,10))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

  "King from b1 with occupied c1 and c2 " should " go to fields a1,c1,a2,b2" in {
    val king = new King(List(1),new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(king.getAllMoves(1,List(2),List(10)).map((m:Move) => m.to) == List(0,2,8,9))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

}