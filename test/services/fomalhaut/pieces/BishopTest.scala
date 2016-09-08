package test.fomalhaut.pieces

import services.fomalhaut.pieces.Bishop
import services.fomalhaut.Move
import org.scalatest.FlatSpec

import services.fomalhaut.BoardSpecialEvents

class BishopTest extends FlatSpec{
  
  "Bishop from b1  " should " go to fields " in {
    val queen = new Bishop(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAllMoves(1,Nil,Nil).map((m:Move) => m.to)  == List( 8, 10, 19, 28, 37, 46, 55))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }  

  "Bishop from b1  " should " attack fields " in {
    val queen = new Bishop(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,Nil,Nil) == List( 8, 10, 19, 28, 37, 46, 55))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }  

  "Bishop from b1 with enemy at a2 and e2 " should " attack fields " in {
    val queen = new Bishop(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,List(8,10),Nil) == List(8,10 ))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }  

  "Bishop from b1  with enemy at a2 and c2 " should " go to fields " in {
    val queen = new Bishop(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAllMoves(1,List(8,10),Nil).map((m:Move) => m.to) == List(8,10 ))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }  
  
  "Bishop from b1  with own at a2 and c2 " should " go nowhere " in {
    val queen = new Bishop(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAllMoves(1,Nil,List(8,10)).map((m:Move) => m.to) == List( ))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }  

    "Bishop from b1  with own at a2 and c2 " should " attack fields a2 anc c2 " in {
    val queen = new Bishop(List(1), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(queen.getAttackedFields(1,Nil,List(8,10)) == List(8,10 ))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
    
  }  


}