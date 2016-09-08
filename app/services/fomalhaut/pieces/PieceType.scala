package services.fomalhaut.pieces

/**
 * Created by kniemiec on 10.03.16.
 */
object PieceType extends Enumeration {
  type PieceType  = Value
  val KING, QEEN, ROOK, BISHOP, KNIGHT, PAWN, BLACK_PAWN, ANY = Value

}
