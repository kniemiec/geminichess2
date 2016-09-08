package services.fomalhaut.pieces

import services.fomalhaut.pieces.PieceType.PieceType
import services.fomalhaut.{BoardSpecialEvents, Move}

/**
 * Created by kniemiec on 10.03.16.
 */
case class Knight(mPositions: List[Int], boardContext: BoardSpecialEvents) extends Piece {

  val positions: List[Int] = mPositions

  val value: Int = 300

  val movePattern: List[Int] = List(1,3,5,9,15,19,21,23)

  override def getPieceType(): PieceType = {
    PieceType.KNIGHT
  }


  override def getPieceCode(): Int = 83

  override def getAttackedFields(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): List[Int] = {
    for(direction <- movePattern if isValidMove(calculateMoveY(from,direction),calculateMoveX(from,direction)))
      yield 8*calculateMoveY(from,direction)+calculateMoveX(from,direction)
  }

  override def getAllMoves(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int], attackedByEnemy: List[Int]): List[Move] = {
    getAttackedFields(from,occupiedByEnemy,occupiedByOwn)
      .filter((to: Int) => isValidKnightMove(to, occupiedByOwn))
      .map{ to => new Move(from,to,getPieceType(),getPieceType())}
  }

   def isValidKnightMove( to : Int,  occupiedByOwn: List[Int]): Boolean = {
     !isOccupiedByOwn(to , occupiedByOwn )
   }

  def calculateMoveX(from: Int, direction: Int): Int = {
    val l: Int = from % 8
    val ld: Int = direction % 5 - 2
    l+ld
  }

  def calculateMoveY(from: Int, direction: Int): Int = {
    val r: Int = from / 8
    val rd: Int = direction / 5 - 2
    r+rd
  }


}
