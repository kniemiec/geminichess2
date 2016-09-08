package services.fomalhaut.pieces

import services.fomalhaut.Move
import services.fomalhaut.pieces.PieceType.PieceType

abstract class Piece {

  val INVALID_MOVE: Int = -1

  val BLACK_COLOR: Int = 1
  val WHITE_COLOR: Int = 0

  val MOVE_MATRIX_SIZE: Int = 3
  val MOVE_OFFSET: Int = 1

  val value: Int

  val positions: List[Int]

  val movePattern: List[Int]

  def getPositions(): List[Int] = {
    positions
  }

  def getValue(): Integer = {
    value
  }

  def getPieceType(): PieceType = {
    PieceType.ANY
  }

  def getPieceCode(): Int = {
    0
  }

  def getAttackedFields(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): List[Int] = {
    var result: List[Int] = List()
    for(direction <- movePattern) result = result ::: moveUntilStop(from,direction,occupiedByEnemy,occupiedByOwn)
    result
  }

  def getAllMoves(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int], attackedByEnemy: List[Int] = List()): List[Move] = {
    val result: List[Int] = getAttackedFields(from,occupiedByEnemy,occupiedByOwn)

    result.filter( !isOccupiedByOwn(_, occupiedByOwn)).map(x => new Move(from,x,getPieceType(),getPieceType()))
  }

  def moveUntilStop(currentField: Int, direction: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): List[Int] = {
    val line = currentField / 8
    val row = currentField % 8
    val vDir = direction / MOVE_MATRIX_SIZE - 1
    val hDir = direction % MOVE_MATRIX_SIZE - 1
    if( isValidMove(line + vDir, row+hDir)){
      val destField = (line+vDir)*8+row+hDir
      if( isOccupiedByEnemy(destField,occupiedByEnemy) || isOccupiedByOwn(destField,occupiedByOwn)) {
        destField :: Nil
      } else {
        destField :: moveUntilStop(destField,direction,occupiedByEnemy,occupiedByOwn)
      }
    }
    else Nil
  }

  def isOccupiedByOwn( x: Int, occupiedByOwn: List[Int]): Boolean = {
    occupiedByOwn.contains(x)
  }

  def isOccupiedByEnemy( x: Int, occupiedByEnemy: List[Int]): Boolean = {
    occupiedByEnemy.contains(x)
  }

  def isOccupiedBy(field : Int, occupied: List[Int]): Boolean = occupied match {
    case Nil => false
    case y :: ys => y == field || isOccupiedBy( field, ys)
  }

  protected def isValidMove( line: Int, row: Int): Boolean = {
    ( line < 8 ) && ( line  >= 0 ) && ( row < 8 ) && ( row >= 0 )
  }

  protected def isValidMove( to : Int): Boolean = {
    ( (to % 8) < 8 ) && ( (to % 8)  >= 0 ) && ( (to / 8 )< 8 ) && ( (to /8)  >= 0 )
  }

}