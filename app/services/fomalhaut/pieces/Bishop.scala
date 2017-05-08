package services.fomalhaut.pieces

import services.fomalhaut.BoardSpecialEvents
import services.fomalhaut.pieces.PieceType.PieceType

/**
 * Created by kniemiec on 10.03.16.
 */
case class Bishop(mPositions: List[Int], boardContext: BoardSpecialEvents) extends Piece {

  val positions: List[Int] = mPositions

  val value: Int = 300

  val movePattern: List[Int] = List(0,2,6,8)

  override def getPieceType(): PieceType = {
    PieceType.BISHOP
  }

  override def getPieceCode(): Int = 66

}
