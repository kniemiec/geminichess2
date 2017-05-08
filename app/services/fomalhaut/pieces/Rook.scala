package services.fomalhaut.pieces

import services.fomalhaut.BoardSpecialEvents
import services.fomalhaut.pieces.PieceType.PieceType

/**
 * Created by kniemiec on 10.03.16.
 */
case class Rook(mPositions: List[Int],boardContext: BoardSpecialEvents) extends Piece {

  val positions: List[Int] = mPositions

  val value: Int = 400

  val movePattern: List[Int] = List(1,3,5,7)

  override def getPieceType(): PieceType = {
    PieceType.ROOK
  }

  override def getPieceCode(): Int = 82
}
