package services.fomalhaut.eva

import services.fomalhaut.pieces.Piece

object BoardValuationPatterns {
  
  
  val pawnAdvancementValuesPattern: Array[Int] = Array(
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        5,5,5,10,10,5,5,5,
        10,10,14,20,20,15,10,10,
        15,20,20,30,30,20,20,15,
        50,50,50,50,50,50,50,50,
        100,100,100,100,100,100,100,100,
        0,0,0,0,0,0,0,0
      )
  
  def getPawnAdvancementValuesPattern(index: Int, color: Int): Int = {
    if(color == 0){
      pawnAdvancementValuesPattern(index)
    } else {
      pawnAdvancementValuesPattern(63-index)  
    }
  }

  
}