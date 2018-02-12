package services.fomalhaut.pieces

import services.fomalhaut.fieldAvailability.{MoveValidator, OccupationChecker}
import services.fomalhaut.pieces.PieceType.PieceType
import services.fomalhaut.{BoardSpecialEvents, Move}

/**
 * Created by kniemiec on 10.03.16.
 */
case class BlackPawn(mPositions: List[Int], boardSpecialEvents: BoardSpecialEvents) extends Piece {

//  BoardTestHelper.printListofInt(mPositions)

  override val MOVE_MATRIX_SIZE: Int = 5
  override val MOVE_OFFSET: Int = 2

  val EN_PASSANT_NOT_ALLOWED = 0

  val WHITE_MOVE_BY_2: Int = 22
  val WHITE_MOVE_BY_1: Int = 17
  val WHITE_ATTACK_LEFT: Int = 16
  val WHITE_ATTACK_RIGHT: Int = 18

  val BLACK_MOVE_BY_2: Int = 2
  val BLACK_MOVE_BY_1: Int = 7
  val BLACK_ATTACK_LEFT: Int = 6
  val BLACK_ATTACK_RIGHT: Int = 8

  val WHITE_INITIAL_RANK: Int = 1
  val BLACK_INITIAL_RANK: Int = 6

  val WHITE_PROMOTION_RANK: Int = 7
  val BLACK_PROMOTION_RANK: Int = 0

  val positions: List[Int] = mPositions

  val value: Int = 100

  val WHITE_INITIAL_MOVE_PATTERN: List[Int] = List(WHITE_MOVE_BY_1, WHITE_MOVE_BY_2)
  val WHITE_NORMAL_MOVE_PATTERN: List[Int] = List(WHITE_MOVE_BY_1 )
  val WHITE_ATTACK_PATTERN: List[Int] = List(WHITE_ATTACK_LEFT,WHITE_ATTACK_RIGHT)

  val BLACK_INITIAL_MOVE_PATTERN: List[Int] = List(BLACK_MOVE_BY_1, BLACK_MOVE_BY_2)
  val BLACK_NORMAL_MOVE_PATTERN: List[Int] = List(BLACK_MOVE_BY_1)
  val BLACK_ATTACK_PATTERN: List[Int] = List(BLACK_ATTACK_LEFT,BLACK_ATTACK_RIGHT)

  val movePattern: List[Int] = Nil

  private def getDynamicMovePattern(line: Int): List[Int] = {
    if(boardSpecialEvents.getColorToMove() == WHITE_COLOR){
      if(line == WHITE_INITIAL_RANK)WHITE_INITIAL_MOVE_PATTERN
      else WHITE_NORMAL_MOVE_PATTERN
    } else {
      if(line == BLACK_INITIAL_RANK)BLACK_INITIAL_MOVE_PATTERN
      else BLACK_NORMAL_MOVE_PATTERN
    }
  }

  override def getPieceType(): PieceType = {
    PieceType.BLACK_PAWN
  }



  override def getPieceCode(): Int = 80

  override def getAttackedFields(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): List[Int] = {
    List(calculateMove(from,getLeftAttackPattern()),calculateMove(from,getRightAttackPattern())).
      filter(_  != INVALID_MOVE)
  }


  override def getAllMoves(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int], attackedByEnemy: List[Int]= List()): List[Move] = {
    val dynamicMovePattern: List[Int] = getDynamicMovePattern(from / 8)
    val listOfAttackedMoves = getNormalMoves(from,occupiedByEnemy,occupiedByOwn,dynamicMovePattern) :::
       getLeftAttack(from,occupiedByEnemy) :::
       getRightAttack(from,occupiedByEnemy)

    val listOfMoves = listOfAttackedMoves.map{ to => new Move(from,to,getPieceType(),getPieceType())}
    if(listOfMoves.nonEmpty) listOfMoves.map(popuplateWithPromotionMove(_)).reduceLeft((A:List[Move],B:List[Move])=>(A ::: B))
    else Nil

//    var result: List[Int] = List()
//    for(direction <- movePattern) result = result ::: moveUntilStop(from,direction,occupiedByEnemy,occupiedByOwn)
//    result.map(x => new Move(from,x,getPieceType(),getPieceType()))
  }

    private def popuplateWithPromotionMove(move: Move): List[Move] = {
    if(boardSpecialEvents.getColorToMove() == WHITE_COLOR){
      if(WHITE_PROMOTION_RANK == move.to / 8) List(new Move(move.from,move.to,getPieceType(),PieceType.QEEN),
                                                   new Move(move.from,move.to,getPieceType(),PieceType.ROOK),
                                                   new Move(move.from,move.to,getPieceType(),PieceType.BISHOP),
                                                   new Move(move.from,move.to,getPieceType(),PieceType.KNIGHT))
      else List(move)
    } else {
      if(BLACK_PROMOTION_RANK == move.to / 8) List(new Move(move.from,move.to,getPieceType(),PieceType.QEEN),
                                                   new Move(move.from,move.to,getPieceType(),PieceType.ROOK),
                                                   new Move(move.from,move.to,getPieceType(),PieceType.BISHOP),
                                                   new Move(move.from,move.to,getPieceType(),PieceType.KNIGHT))
      else List(move)
    }
  }

  private def popuplateWithPromotionMoves(to: Int): List[Int] = {
    if(boardSpecialEvents.getColorToMove() == WHITE_COLOR){
      if(WHITE_PROMOTION_RANK == to / 8) List.fill(4)(to)
      else List(to)
    } else {
      if(BLACK_PROMOTION_RANK == to / 8) List.fill(4)(to)
      else List(to)
    }
  }


  private def getLeftAttack(from: Int,occupiedByEnemy: List[Int]): List[Int] = {
    val dir = getLeftAttackPattern()
    val destMove = calculateMove(from,dir)
    if(destMove != INVALID_MOVE && (OccupationChecker.isOccupiedByEnemy(destMove,occupiedByEnemy) || isEnPassantField(destMove))) List(destMove)
    else Nil
  }

  private def getRightAttack(from: Int,occupiedByEnemy: List[Int]): List[Int] = {
    val dir = getRightAttackPattern()
    val destMove = calculateMove(from,dir)
    if(destMove != INVALID_MOVE && (OccupationChecker.isOccupiedByEnemy(destMove,occupiedByEnemy) || isEnPassantField(destMove))) List(destMove)
    else Nil
  }

  private def isEnPassantField(destField : Int): Boolean = {
    destField == boardSpecialEvents.getEnPassantMove()
  }

  private def getRightAttackPattern(): Int = {
      BLACK_ATTACK_RIGHT
  }

  private def getLeftAttackPattern(): Int = {
      BLACK_ATTACK_LEFT
  }

  def getNormalMoves(from: Int,occupiedByEnemy: List[Int], occupiedByOwn: List[Int], movesList: List[Int]): List[Int] = {
    movesList.map( (X:Int) => calculateMove(from,X:Int)).
      filter((X:Int) => X > -1).
      filter( (X:Int) => isFreeField(X:Int,occupiedByEnemy,occupiedByOwn))
  }


  private def isFreeField(field: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): Boolean = {
    !OccupationChecker.isOccupiedBy(field, occupiedByEnemy ::: occupiedByOwn)
  }

  private def old_calculateMove(from: Int, direction: Int): Int = {
    val newLine = calculateM(from,direction,_/_)
    val newRow = calculateM(from,direction,_%_)
    if(MoveValidator.isValidMove(newLine,newRow)) 8*newLine+newRow
    else INVALID_MOVE
  }

  private def calculateMove(from: Int, direction: Int): Int = {

    val newLine = calculateM(from,direction,_/_)
    val newRow = calculateM(from,direction,_%_)
    if(MoveValidator.isValidMove(newLine,newRow)) 8*newLine+newRow
    else INVALID_MOVE
//    8 * calculateM(from,direction,_/_) +calculateM(from,direction,_%_)
  }


  def calculateM(from: Int, direction: Int, divisionF: (Int,Int) => Int): Int = {
    val l: Int = divisionF(from,8)
    val ld: Int = divisionF(direction,MOVE_MATRIX_SIZE) - MOVE_OFFSET
    l+ld
  }

}
