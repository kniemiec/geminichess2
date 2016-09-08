package test.fomalhaut.pieces

import services.fomalhaut.pieces.Queen
import org.scalatest.FlatSpec
import services.fomalhaut.{BoardSpecialEvents, Move}

class QueenTest extends FlatSpec{
  
  "Queen from b1  " should " go to fields " in {
    val queen = new Queen(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAllMoves(1,Nil,Nil).map((m:Move) => m.to) == List(0, 2, 3, 4, 5, 6, 7, 8, 9, 17, 25, 33, 41, 49, 57, 10, 19, 28, 37, 46, 55))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }


  "Queen from b1  " should " attack to fields " in {
    val queen = new Queen(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,Nil,Nil) == List(0, 2, 3, 4, 5, 6, 7, 8, 9, 17, 25, 33, 41, 49, 57, 10, 19, 28, 37, 46, 55))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))

  }

  "Queen from b1  with enemies on a2, b2, c2,c1 " should " attack fields " in {
    val queen = new Queen(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,List(8,9),List(2,10)) == List(0, 2,  8, 9, 10))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))

  }

  "Queen from b1  with enemies on a2, b2, c2,c1 " should " go to  fields " in {
    val queen = new Queen(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAllMoves(1,List(8,9),List(2,10)).map((m:Move) => m.to) == List(0, 8, 9))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))

  }

}