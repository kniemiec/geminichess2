package services.fomalhaut.pieces

import services.fomalhaut.Move
import services.fomalhaut.fieldAvailability.{MoveValidator, OccupationChecker}
import services.fomalhaut.pieces.PieceType.PieceType

trait Piece {

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

  def getPieceType(): PieceType

  def getPieceCode(): Int

  def getAttackedFields(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): List[Int] = {
    var result: List[Int] = List()
    for(direction <- movePattern) result = result ::: moveUntilStop(from,direction,occupiedByEnemy,occupiedByOwn)
    result
  }

  def getAllMoves(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int], attackedByEnemy: List[Int] = List()): List[Move] = {
    val result: List[Int] = getAttackedFields(from,occupiedByEnemy,occupiedByOwn)

    result.filter( !OccupationChecker.isOccupiedByOwn(_, occupiedByOwn)).map(x => new Move(from,x,getPieceType(),getPieceType()))
  }

  def moveUntilStop(currentField: Int, direction: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): List[Int] = {
    val line = currentField / 8
    val row = currentField % 8
    val vDir = direction / MOVE_MATRIX_SIZE - 1
    val hDir = direction % MOVE_MATRIX_SIZE - 1
    if( MoveValidator.isValidMove(line + vDir, row+hDir)){
      val destField = (line+vDir)*8+row+hDir
      if( OccupationChecker.isOccupiedByEnemy(destField,occupiedByEnemy) || OccupationChecker.isOccupiedByOwn(destField,occupiedByOwn)) {
        destField :: Nil
      } else {
        destField :: moveUntilStop(destField,direction,occupiedByEnemy,occupiedByOwn)
      }
    }
    else Nil
  }

}