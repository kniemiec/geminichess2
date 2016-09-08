package test.fomalhaut.pieces

import services.fomalhaut.{Move,BoardSpecialEvents}
import services.fomalhaut.pieces.{BlackPawn, Pawn}

import org.scalatest.FlatSpec

/**
 * @author kniemiec
 */
class PawnTest  extends FlatSpec{
  
  "white pawn from e2  " should " attack to fields d3 and f3" in {
    val pawn = new Pawn(List(12), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(12,Nil,Nil) == List( 19, 21))
  }
  
    "white pawn from e2  " should " can go to e3 only" in {
    val pawn = new Pawn(List(12), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(12,Nil,List(28)).map((m:Move) => m.to) == List( 20))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }
  
    "white pawn from e2  " should " attack and go to fields d3,f3,e3 and e4" in {
    val pawn = new Pawn(List(12), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(12,List(19,21),Nil).map((m:Move) => m.to).sorted == List( 20, 28,19,21).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }
  
  "white pawn from e3  " should " go to field e4 " in {
    val pawn = new Pawn(List(20), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(20,Nil,Nil).map((m:Move) => m.to) == List( 28))
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }
  
  "white pawn from e7  " should " go to field e8 and be promoted " in {
    val pawn = new Pawn(List(52), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(52,Nil,Nil).map((m:Move) => m.to).sorted == List( 60,60,60,60).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }  

  "black pawn from e3  " should " attack fields d2 and f2 " in {
    val pawn = new BlackPawn(List(20), new BoardSpecialEvents(1,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(20,Nil,Nil).sorted == List( 11,13).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }    

    "black pawn from e3  " should " go to  e2 and attack d2 and f2 " in {
    val pawn = new BlackPawn(List(20), new BoardSpecialEvents(1,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(20,List(11,13),Nil).map((m:Move) => m.to).sorted == List( 12,11,13).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }   
  
  "black pawn from e2  " should " go to field e1 " in {
    val pawn = new BlackPawn(List(12), new BoardSpecialEvents(1,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(12,Nil,Nil).map((m:Move) => m.to).sorted == List( 4,4,4,4).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }    
  
  "black pawn from e2  " should " attack and go  to field d1, f1 and e1 " in {
    val pawn = new BlackPawn(List(12), new BoardSpecialEvents(1,true,true,true,true,0,0,0))
    assert(pawn.getAllMoves(12,List(3,5),Nil).map((m: Move) => m.to).sorted == List( 4,4,4,4,3,3,3,3,5,5,5,5).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))    
  }

  "white pawn from a2  " should " attack to fields b3" in {
    val pawn = new Pawn(List(12), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(8,Nil,Nil) == List( 17))
  }

  "white pawn from h2  " should " attack to fields g3" in {
    val pawn = new Pawn(List(12), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(15,Nil,Nil) == List( 22))
  }

  "white pawn from b7  " should " attack to fields a8" in {
    val pawn = new Pawn(List(49), new BoardSpecialEvents(0,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(49,Nil,Nil) == List( 56,58))
  }

  "black pawn from a3  " should " attack only b2" in {
    val pawn = new BlackPawn(List(12), new BoardSpecialEvents(1,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(16,Nil,Nil).sorted == List( 9).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }

  "black pawn from h3  " should " attack only g2 " in {
    val pawn = new BlackPawn(List(12), new BoardSpecialEvents(1,true,true,true,true,0,0,0))
    assert(pawn.getAttackedFields(23,Nil,Nil).sorted == List( 14).sorted)
    //assert(board.whitePiecesPosition == List(new Queen(List(3))))
  }



}
