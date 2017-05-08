package services.fomalhaut.pieces

import services.fomalhaut.BoardSpecialEvents
import services.fomalhaut.pieces.PieceType.PieceType

/**
 * Created by kniemiec on 10.03.16.
 */
case class Queen(mPositions: List[Int],boardContext: BoardSpecialEvents) extends Piece {



  val positions: List[Int] = mPositions

  val value: Int = 800

  val movePattern: List[Int] = List(0,1,2,3,5,6,7,8)

  override def getPieceType(): PieceType = {
    PieceType.QEEN
  }

  override def getPieceCode(): Int = 81
}
