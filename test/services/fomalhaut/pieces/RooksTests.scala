package test.fomalhaut.pieces;

import services.fomalhaut.pieces.Rook
import org.scalatest.FlatSpec
import services.fomalhaut.{BoardSpecialEvents, Move}

class RooksTests extends FlatSpec {

    "Rook from b1  " should " go to fields " in {
        val queen = new Rook(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
        assert(queen.getAllMoves(1,Nil,Nil).map((m:Move) => m.to) == List(0, 2, 3, 4, 5, 6, 7,  9, 17, 25, 33, 41, 49, 57))
        //assert(board.whitePiecesPosition == List(new Queen(List(3))))
        
      }  
    
    "Rook from b1 with occupied a3, c1 and c2 and c3" should " go to fields " in {
    val queen = new Rook(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAllMoves(1,List(9,16),List(2,18)).map((m:Move) => m.to) == List(0, 9))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

  "Rook from b1  " should " attack fields " in {
    val queen = new Rook(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,Nil,Nil) == List(0, 2, 3, 4, 5, 6, 7,  9, 17, 25, 33, 41, 49, 57))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))

  }

  "Rook from b1 with occupied a3, c1 and c2 and c3" should " attack fields " in {
    val queen = new Rook(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,List(9,16),List(2,18)) == List(0, 2,9))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

}

