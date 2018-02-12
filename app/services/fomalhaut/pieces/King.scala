package services.fomalhaut.pieces

import services.fomalhaut.fieldAvailability.{MoveValidator, OccupationChecker}
import services.fomalhaut.pieces.PieceType.PieceType
import services.fomalhaut.{BoardSpecialEvents, Move}

/**
 * Created by kniemiec on 10.03.16.
 */
case class King(mPositions: List[Int], boardContext: BoardSpecialEvents) extends Piece {

  val positions: List[Int] = mPositions

  val SHORT_CASTLING = true;
  val LONG_CASTLING = false;

  val POSSIBLE_CASTLINGS = List (SHORT_CASTLING, LONG_CASTLING)

  val value: Int = 100000
  
  val movePattern: List[Int] = List(0,1,2,3,5,6,7,8)

  val WHITE_SHORT_CASTLING_PATTERN = List(5,6)
  val WHITE_LONG_CASTLING_PATTERN = List(3,2,1)

  val WHITE_KING_SHORT_CASTLING_FIELDS = List(5,6)
  val WHITE_KING_LONG_CASTLING_FIELDS = List(3,2)

  val WHITE_LONG_CASTLING_FIELD = 2
  val WHITE_SHORT_CASTLING_FIELD = 6

  val BLACK_SHORT_CASTLING_PATTERN = List(61,62)
  val BLACK_LONG_CASTLING_PATTERN = List(59,58,57)

  val BLACK_KING_SHORT_CASTLING_FIELDS = List(61,62)
  val BLACK_KING_LONG_CASTLING_FIELDS = List(59,58)

  val BLACK_LONG_CASTLING_FIELD = 58
  val BLACK_SHORT_CASTLING_FIELD = 62

  val WHITE_KING_POSITION = 4
  val BLACK_KING_POSITION = 60


   override def getAttackedFields(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]= List()): List[Int] = {
    for(direction <- movePattern if MoveValidator.isValidMove(calculateMoveY(from,direction),calculateMoveX(from,direction)))
      yield 8*calculateMoveY(from,direction)+calculateMoveX(from,direction)
  }

  override def getAllMoves(from: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int], attackedByEnemy: List[Int]): List[Move] = {
    var listOfAllPossibleMoves : List[Int] = List()
    listOfAllPossibleMoves = getAttackedFields(from,occupiedByEnemy,occupiedByOwn)
      .filter((to:Int ) => isValidKingMove( to , occupiedByEnemy,occupiedByOwn,attackedByEnemy))
    listOfAllPossibleMoves = listOfAllPossibleMoves ::: getCastlingMoves(from,occupiedByEnemy, occupiedByOwn,attackedByEnemy)
    listOfAllPossibleMoves.map{ to => new Move(from,to,getPieceType(),getPieceType())}
  }

  override def getPieceType(): PieceType = {
    PieceType.KING
  }


  override def getPieceCode(): Int = 75



  private def getCastlingPattern(isShortCastling: Boolean): List[Int] = {
    if(boardContext.getColorToMove() == WHITE_COLOR){
      if(isShortCastling) WHITE_SHORT_CASTLING_PATTERN
      else WHITE_LONG_CASTLING_PATTERN
    } else {
      if(isShortCastling) BLACK_SHORT_CASTLING_PATTERN
      else BLACK_LONG_CASTLING_PATTERN
    }
  }

  private def getKingCastlingMovesPattern(isShortCastling: Boolean): List[Int] = {
    if(boardContext.getColorToMove() == WHITE_COLOR){
      if(isShortCastling) WHITE_KING_SHORT_CASTLING_FIELDS
      else WHITE_KING_LONG_CASTLING_FIELDS
    } else {
      if(isShortCastling) BLACK_KING_SHORT_CASTLING_FIELDS
      else BLACK_KING_LONG_CASTLING_FIELDS
    }
  }

  private def getCastlingField(isShortCastling: Boolean): Int = {
    if(boardContext.getColorToMove() == WHITE_COLOR){
      if(isShortCastling) WHITE_SHORT_CASTLING_FIELD
      else WHITE_LONG_CASTLING_FIELD
    } else {
      if(isShortCastling) BLACK_SHORT_CASTLING_FIELD
      else BLACK_LONG_CASTLING_FIELD
    }
  }

  private def isCastlingValid(short: Boolean): Boolean = {
    if(boardContext.getColorToMove() == WHITE_COLOR){
      if(short) boardContext.isShortCastlingAllowedForWhite
      boardContext.isLongCastlingAllowedForWhite
    } else {
      if(short) boardContext.isShortCastlingAllowedForBlack
      else boardContext.isLongCastlingAllowedForBlack
    }
  }

  def isKingOnPosition(isShortCastling:Boolean, kingPosition: Int) : Boolean = {
    if(boardContext.getColorToMove() == WHITE_COLOR){
      kingPosition == WHITE_KING_POSITION
    } else {
      kingPosition == BLACK_KING_POSITION
    }
  }

  def areAllFieldsFree(mustBeFreeFields: List[Int],occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): Boolean = {
    mustBeFreeFields.map(freeField => isFieldFree(freeField, occupiedByEnemy, occupiedByOwn))
      .reduceLeft((A:Boolean,B:Boolean)=>(A && B))
  }


  private def getCastlingMoves(kingPosition: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int],attackedByEnemy: List[Int]): List[Int] = {
    val castlings = POSSIBLE_CASTLINGS.filter(isCastlingValid(_))

      val allowedCastlings = castlings.filter((X) =>
        (isKingOnPosition(X:Boolean,kingPosition) &&
                    areAllFieldsFree(getCastlingPattern(X:Boolean),occupiedByEnemy,occupiedByOwn) &&
                    areKingFieldsNotAttacked(getKingCastlingMovesPattern(X:Boolean),attackedByEnemy)))
    val result = allowedCastlings.map(getCastlingField(_))
    result
  }

//  private def isCastlingAllowed(mustBeFreeFields: List[Int],occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): Boolean = {
//    mustBeFreeFields.map(freeField => isCastlingElligible(freeField, occupiedByEnemy, occupiedByOwn))
//        .reduceLeft((A:Boolean,B:Boolean)=>(A && B))
//  }

  private def areKingFieldsNotAttacked(kingsMoveFields: List[Int], listOfAttackedFields: List[Int]): Boolean = {
    kingsMoveFields.map(kingField => isFieldNotAttacked(kingField,listOfAttackedFields)).reduceLeft((A:Boolean,B:Boolean)=> (A && B))
  }

  private def isFieldNotAttacked(kingField: Int, listOfAttackedFields: List[Int]) : Boolean = {
    !listOfAttackedFields.contains(kingField)
  }

  private def isFieldFree(field: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int]): Boolean = {
    !OccupationChecker.isOccupiedByOwn(field, occupiedByOwn ) && !OccupationChecker.isOccupiedByEnemy(field, occupiedByEnemy)
  }

  private def isCastlingElligible(field: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int],listOfAttackedFields: List[Int]): Boolean = {
    !OccupationChecker.isOccupiedByOwn(field, occupiedByOwn ) && !OccupationChecker.isOccupiedByEnemy(field, occupiedByEnemy) && isFieldNotAttacked(field,listOfAttackedFields)
  }

  def isValidKingMove( to: Int, occupiedByEnemy: List[Int], occupiedByOwn: List[Int], listOfAttackedFields: List[Int]): Boolean = {
    !OccupationChecker.isOccupiedByOwn(to , occupiedByOwn ) && isFieldNotAttacked(to,listOfAttackedFields)
  }

    def calculateMoveX(from: Int, direction: Int): Int = {
    val l: Int = from % 8
    val ld: Int = direction % 3 - 1
    l+ld
  }

  def calculateMoveY(from: Int, direction: Int): Int = {
    val r: Int = from / 8
    val rd: Int = direction / 3 - 1
    r+rd
  }


}
