package test.fomalhaut.pieces

import services.fomalhaut.pieces.Knight
import org.scalatest.FlatSpec

import services.fomalhaut.{Move, BoardSpecialEvents}

class KnightTest extends FlatSpec{
  
  "Knight from b1  " should " attack to fields " in {
    val knight = new Knight(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(knight.getAttackedFields(1,Nil,Nil).sorted == List(18,16,11).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }    
  
  "Knight from b1 with occupied a3, c1 and c2 and c3" should " attack fields " in {
    val queen = new Knight(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,List(2,16),List(10,18)).sorted == List(16,11,18).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

  "Knight from b1  " should " go to fields " in {
    val knight = new Knight(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(knight.getAllMoves(1,Nil,Nil).map((m: Move) => m.to).sorted == List(18,16,11).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))

  }

  "Knight from b1 with occupied a3, c1 and c2 and c3" should " can go to fields " in {
    val knight = new Knight(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(knight.getAllMoves(1,List(2,16),List(10,18)).map((m: Move) => m.to).sorted == List(16,11).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

}
