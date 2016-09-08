package services.fomalhaut

import services.fomalhaut.pieces.PieceType
import services.fomalhaut.helper.MoveLogger
import PieceType._

class Move (f: Int, t: Int, whoMoved: PieceType, whatPromoted: PieceType){

  val from: Int = f
  val to: Int = t

  val who: PieceType = whoMoved
  val what: PieceType = whatPromoted


  def canEqual(other: Any): Boolean = other.isInstanceOf[Move]

  override def equals(other: Any): Boolean = other match {
    case that: Move =>
      (that canEqual this) &&
        from == that.from &&
        to == that.to &&
        who == that.who &&
        what == that.what
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(from, to, who, what)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }


  override def toString = MoveLogger.convertToMove(this)
}