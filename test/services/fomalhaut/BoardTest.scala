package test.fomalhaut

import services.fomalhaut.controller.BoardTextRepresentation
import services.fomalhaut.eva.MoveEvaluator
import services.fomalhaut.helper.FENParser
import services.fomalhaut.pieces._
import services.fomalhaut.{BoardSpecialEvents, Board, Move}
import org.scalatest.FlatSpec

class BoardTest extends FlatSpec{


  val INITIAL_BOARD_WHITE_MOVES = List(
      new Move(8,16, PieceType.PAWN,PieceType.PAWN),
      new Move(8,24, PieceType.PAWN,PieceType.PAWN),
      new Move(9,17, PieceType.PAWN,PieceType.PAWN),
      new Move(9,25, PieceType.PAWN,PieceType.PAWN),
      new Move(10,18, PieceType.PAWN,PieceType.PAWN),
      new Move(10,26, PieceType.PAWN,PieceType.PAWN),
      new Move(11,19, PieceType.PAWN,PieceType.PAWN),
      new Move(11,27, PieceType.PAWN,PieceType.PAWN),
      new Move(12,20, PieceType.PAWN,PieceType.PAWN),
      new Move(12,28, PieceType.PAWN,PieceType.PAWN),
      new Move(13,21, PieceType.PAWN,PieceType.PAWN),
      new Move(13,29, PieceType.PAWN,PieceType.PAWN),
      new Move(14,22, PieceType.PAWN,PieceType.PAWN),
      new Move(14,30, PieceType.PAWN,PieceType.PAWN),
      new Move(15,23, PieceType.PAWN,PieceType.PAWN),
      new Move(15,31, PieceType.PAWN,PieceType.PAWN),
      new Move(1,18, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(1,16, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(6,23, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(6,21, PieceType.KNIGHT,PieceType.KNIGHT)
      )

    val BOARD_WITH_INTERCHECK  = List(
      new Move(8,16, PieceType.PAWN,PieceType.PAWN),
      new Move(8,24, PieceType.PAWN,PieceType.PAWN),
      new Move(9,17, PieceType.PAWN,PieceType.PAWN),
      new Move(9,25, PieceType.PAWN,PieceType.PAWN),
      new Move(10,18, PieceType.PAWN,PieceType.PAWN),
      new Move(10,26, PieceType.PAWN,PieceType.PAWN),
      new Move(11,19, PieceType.PAWN,PieceType.PAWN),
      new Move(11,27, PieceType.PAWN,PieceType.PAWN),
      new Move(13,21, PieceType.PAWN,PieceType.PAWN),
      new Move(13,29, PieceType.PAWN,PieceType.PAWN),
      new Move(14,22, PieceType.PAWN,PieceType.PAWN),
      new Move(14,30, PieceType.PAWN,PieceType.PAWN),
      new Move(15,23, PieceType.PAWN,PieceType.PAWN),
      new Move(15,31, PieceType.PAWN,PieceType.PAWN),
      new Move(1,18, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(1,16, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(3,12,PieceType.QEEN,PieceType.QEEN),
      new Move(3,21,PieceType.QEEN,PieceType.QEEN),
      new Move(3,30,PieceType.QEEN,PieceType.QEEN),
      new Move(3,39,PieceType.QEEN,PieceType.QEEN),
      new Move(4,12,PieceType.KING,PieceType.KING),
      new Move(5,12,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,19,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,26,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,33,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,40,PieceType.BISHOP,PieceType.BISHOP),
      new Move(6,23, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(6,21, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(6,12, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(36,44,PieceType.PAWN,PieceType.PAWN),
      new Move(36,43,PieceType.PAWN,PieceType.PAWN)
      )

  val INITIAL_BOARD_BLACK_MOVES = List(
      new Move(48,40, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(48,32, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(49,41, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(49,33, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(50,42, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(50,34, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(51,43, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(51,35, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(52,44, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(52,36, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(53,45, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(53,37, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(54,46, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(54,38, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(55,47, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(55,39, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
      new Move(57,40, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(57,42, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(62,47, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(62,45, PieceType.KNIGHT,PieceType.KNIGHT)
      )


   val BOARD_AFTER_E4_E5= List (
      new Move(8,16, PieceType.PAWN,PieceType.PAWN),
      new Move(8,24, PieceType.PAWN,PieceType.PAWN),
      new Move(9,17, PieceType.PAWN,PieceType.PAWN),
      new Move(9,25, PieceType.PAWN,PieceType.PAWN),
      new Move(10,18, PieceType.PAWN,PieceType.PAWN),
      new Move(10,26, PieceType.PAWN,PieceType.PAWN),
      new Move(11,19, PieceType.PAWN,PieceType.PAWN),
      new Move(11,27, PieceType.PAWN,PieceType.PAWN),
      new Move(13,21, PieceType.PAWN,PieceType.PAWN),
      new Move(13,29, PieceType.PAWN,PieceType.PAWN),
      new Move(14,22, PieceType.PAWN,PieceType.PAWN),
      new Move(14,30, PieceType.PAWN,PieceType.PAWN),
      new Move(15,23, PieceType.PAWN,PieceType.PAWN),
      new Move(15,31, PieceType.PAWN,PieceType.PAWN),
      new Move(1,18, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(1,16, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(3,12,PieceType.QEEN,PieceType.QEEN),
      new Move(3,21,PieceType.QEEN,PieceType.QEEN),
      new Move(3,30,PieceType.QEEN,PieceType.QEEN),
      new Move(3,39,PieceType.QEEN,PieceType.QEEN),
      new Move(4,12,PieceType.KING,PieceType.KING),
      new Move(5,12,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,19,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,26,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,33,PieceType.BISHOP,PieceType.BISHOP),
      new Move(5,40,PieceType.BISHOP,PieceType.BISHOP),
      new Move(6,23, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(6,21, PieceType.KNIGHT,PieceType.KNIGHT),
      new Move(6,12, PieceType.KNIGHT,PieceType.KNIGHT)
   )

   val BOARD_WITHOUT_PAWNS_WHITE= List (
       new Move(0,8,PieceType.ROOK,PieceType.ROOK),
       new Move(0,16,PieceType.ROOK,PieceType.ROOK),
       new Move(0,24,PieceType.ROOK,PieceType.ROOK),
       new Move(0,32,PieceType.ROOK,PieceType.ROOK),
       new Move(0,40,PieceType.ROOK,PieceType.ROOK),
       new Move(0,48,PieceType.ROOK,PieceType.ROOK),
       new Move(0,56,PieceType.ROOK,PieceType.ROOK),
       new Move(7,15,PieceType.ROOK,PieceType.ROOK),
       new Move(7,23,PieceType.ROOK,PieceType.ROOK),
       new Move(7,31,PieceType.ROOK,PieceType.ROOK),
       new Move(7,39,PieceType.ROOK,PieceType.ROOK),
       new Move(7,47,PieceType.ROOK,PieceType.ROOK),
       new Move(7,55,PieceType.ROOK,PieceType.ROOK),
       new Move(7,63,PieceType.ROOK,PieceType.ROOK),
       new Move(1,16, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(1,18, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(1,11, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(6,23, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(6,21, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(6,12, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(2,9,PieceType.BISHOP,PieceType.BISHOP),
       new Move(2,16,PieceType.BISHOP,PieceType.BISHOP),
       new Move(2,11,PieceType.BISHOP,PieceType.BISHOP),
       new Move(2,20,PieceType.BISHOP,PieceType.BISHOP),
       new Move(2,29,PieceType.BISHOP,PieceType.BISHOP),
       new Move(2,38,PieceType.BISHOP,PieceType.BISHOP),
       new Move(2,47,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,12,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,19,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,26,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,33,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,40,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,14,PieceType.BISHOP,PieceType.BISHOP),
       new Move(5,23,PieceType.BISHOP,PieceType.BISHOP),
       new Move(4,12,PieceType.KING,PieceType.KING),
       new Move(4,13,PieceType.KING,PieceType.KING),
       new Move(3,11,PieceType.QEEN,PieceType.QEEN),
       new Move(3,19,PieceType.QEEN,PieceType.QEEN),
       new Move(3,27,PieceType.QEEN,PieceType.QEEN),
       new Move(3,35,PieceType.QEEN,PieceType.QEEN),
       new Move(3,43,PieceType.QEEN,PieceType.QEEN),
       new Move(3,51,PieceType.QEEN,PieceType.QEEN),
       new Move(3,59,PieceType.QEEN,PieceType.QEEN),
       new Move(3,10,PieceType.QEEN,PieceType.QEEN),
       new Move(3,17,PieceType.QEEN,PieceType.QEEN),
       new Move(3,24,PieceType.QEEN,PieceType.QEEN),
       new Move(3,12,PieceType.QEEN,PieceType.QEEN),
       new Move(3,21,PieceType.QEEN,PieceType.QEEN),
       new Move(3,30,PieceType.QEEN,PieceType.QEEN),
       new Move(3,39,PieceType.QEEN,PieceType.QEEN)
    )

   val BOARD_WITHOUT_PAWNS_BLACK= List (
       new Move(56,0,PieceType.ROOK,PieceType.ROOK),
       new Move(56,8,PieceType.ROOK,PieceType.ROOK),
       new Move(56,16,PieceType.ROOK,PieceType.ROOK),
       new Move(56,24,PieceType.ROOK,PieceType.ROOK),
       new Move(56,32,PieceType.ROOK,PieceType.ROOK),
       new Move(56,40,PieceType.ROOK,PieceType.ROOK),
       new Move(56,48,PieceType.ROOK,PieceType.ROOK),
       new Move(63,15,PieceType.ROOK,PieceType.ROOK),
       new Move(63,23,PieceType.ROOK,PieceType.ROOK),
       new Move(63,31,PieceType.ROOK,PieceType.ROOK),
       new Move(63,39,PieceType.ROOK,PieceType.ROOK),
       new Move(63,47,PieceType.ROOK,PieceType.ROOK),
       new Move(63,55,PieceType.ROOK,PieceType.ROOK),
       new Move(63,7,PieceType.ROOK,PieceType.ROOK),
       new Move(62,47, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(62,45, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(62,52, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(57,40, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(57,42, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(57,51, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(58,49,PieceType.BISHOP,PieceType.BISHOP),
       new Move(58,40,PieceType.BISHOP,PieceType.BISHOP),
       new Move(58,51,PieceType.BISHOP,PieceType.BISHOP),
       new Move(58,44,PieceType.BISHOP,PieceType.BISHOP),
       new Move(58,37,PieceType.BISHOP,PieceType.BISHOP),
       new Move(58,30,PieceType.BISHOP,PieceType.BISHOP),
       new Move(58,23,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,54,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,47,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,52,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,43,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,34,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,25,PieceType.BISHOP,PieceType.BISHOP),
       new Move(61,16,PieceType.BISHOP,PieceType.BISHOP),
       new Move(60,52,PieceType.KING,PieceType.KING),
       new Move(60,53,PieceType.KING,PieceType.KING),
       new Move(59,11,PieceType.QEEN,PieceType.QEEN),
       new Move(59,19,PieceType.QEEN,PieceType.QEEN),
       new Move(59,27,PieceType.QEEN,PieceType.QEEN),
       new Move(59,35,PieceType.QEEN,PieceType.QEEN),
       new Move(59,43,PieceType.QEEN,PieceType.QEEN),
       new Move(59,51,PieceType.QEEN,PieceType.QEEN),
       new Move(59,3,PieceType.QEEN,PieceType.QEEN),
       new Move(59,50,PieceType.QEEN,PieceType.QEEN),
       new Move(59,41,PieceType.QEEN,PieceType.QEEN),
       new Move(59,32,PieceType.QEEN,PieceType.QEEN),
       new Move(59,52,PieceType.QEEN,PieceType.QEEN),
       new Move(59,45,PieceType.QEEN,PieceType.QEEN),
       new Move(59,38,PieceType.QEEN,PieceType.QEEN),
       new Move(59,31,PieceType.QEEN,PieceType.QEEN)
    )

  val WHITE_SHORT_CASTLING_AND_ROOK_AND_R_PAWNS = List(
    new Move(0,1,PieceType.ROOK,PieceType.ROOK),
    new Move(0,2,PieceType.ROOK,PieceType.ROOK),
    new Move(0,3,PieceType.ROOK,PieceType.ROOK),
    new Move(7,6,PieceType.ROOK,PieceType.ROOK),
    new Move(7,5,PieceType.ROOK,PieceType.ROOK),
    new Move(8,16, PieceType.PAWN,PieceType.PAWN),
    new Move(15,23, PieceType.PAWN,PieceType.PAWN),
    new Move(8,24, PieceType.PAWN,PieceType.PAWN),
    new Move(15,31, PieceType.PAWN,PieceType.PAWN),
    new Move(4,6,PieceType.KING,PieceType.KING),
    new Move(4,5,PieceType.KING,PieceType.KING),
    new Move(4,12,PieceType.KING,PieceType.KING),
    new Move(4,13,PieceType.KING,PieceType.KING)
  )

   val WHITE_CASTLING_AND_ROOK_AND_R_PAWNS = List(
       new Move(0,1,PieceType.ROOK,PieceType.ROOK),
       new Move(0,2,PieceType.ROOK,PieceType.ROOK),
       new Move(0,3,PieceType.ROOK,PieceType.ROOK),
       new Move(7,6,PieceType.ROOK,PieceType.ROOK),
       new Move(7,5,PieceType.ROOK,PieceType.ROOK),
       new Move(8,16, PieceType.PAWN,PieceType.PAWN),
       new Move(15,23, PieceType.PAWN,PieceType.PAWN),
       new Move(8,24, PieceType.PAWN,PieceType.PAWN),
       new Move(15,31, PieceType.PAWN,PieceType.PAWN),
       new Move(4,2,PieceType.KING,PieceType.KING),
       new Move(4,6,PieceType.KING,PieceType.KING),
       new Move(4,3,PieceType.KING,PieceType.KING),
       new Move(4,5,PieceType.KING,PieceType.KING),
       new Move(4,12,PieceType.KING,PieceType.KING),
       new Move(4,11,PieceType.KING,PieceType.KING),
       new Move(4,13,PieceType.KING,PieceType.KING)
   )


  val WHITE_NO_CASTLING_AND_ROOK_AND_R_PAWNS = List(
    new Move(0,1,PieceType.ROOK,PieceType.ROOK),
    new Move(0,2,PieceType.ROOK,PieceType.ROOK),
    new Move(0,3,PieceType.ROOK,PieceType.ROOK),
    new Move(7,6,PieceType.ROOK,PieceType.ROOK),
    new Move(7,5,PieceType.ROOK,PieceType.ROOK),
    new Move(8,16, PieceType.PAWN,PieceType.PAWN),
    new Move(15,23, PieceType.PAWN,PieceType.PAWN),
    new Move(8,24, PieceType.PAWN,PieceType.PAWN),
    new Move(15,31, PieceType.PAWN,PieceType.PAWN),
    new Move(4,12,PieceType.KING,PieceType.KING)
  )

  val WHITE_CASTLING_NOT_ALLOWED_AND_ROOK_AND_R_PAWNS = List(
    new Move(0,1,PieceType.ROOK,PieceType.ROOK),
    new Move(0,2,PieceType.ROOK,PieceType.ROOK),
    new Move(0,3,PieceType.ROOK,PieceType.ROOK),
    new Move(7,6,PieceType.ROOK,PieceType.ROOK),
    new Move(7,5,PieceType.ROOK,PieceType.ROOK),
    new Move(4,3,PieceType.KING,PieceType.KING),
    new Move(4,5,PieceType.KING,PieceType.KING),
    new Move(8,16, PieceType.PAWN,PieceType.PAWN),
    new Move(15,23, PieceType.PAWN,PieceType.PAWN),
    new Move(8,24, PieceType.PAWN,PieceType.PAWN),
    new Move(15,31, PieceType.PAWN,PieceType.PAWN),
    new Move(4,12,PieceType.KING,PieceType.KING),
    new Move(4,11,PieceType.KING,PieceType.KING),
    new Move(4,13,PieceType.KING,PieceType.KING)
  )


  val WHITE_QUEEN_SIDE_CASTLING_AND_ROOK_AND_R_PAWNS = List(
    new Move(0,1,PieceType.ROOK,PieceType.ROOK),
    new Move(0,2,PieceType.ROOK,PieceType.ROOK),
    new Move(0,3,PieceType.ROOK,PieceType.ROOK),
    new Move(7,6,PieceType.ROOK,PieceType.ROOK),
    new Move(7,5,PieceType.ROOK,PieceType.ROOK),
    new Move(8,16, PieceType.PAWN,PieceType.PAWN),
    new Move(15,23, PieceType.PAWN,PieceType.PAWN),
    new Move(8,24, PieceType.PAWN,PieceType.PAWN),
    new Move(15,31, PieceType.PAWN,PieceType.PAWN),
    new Move(4,3,PieceType.KING,PieceType.KING),
    new Move(4,2,PieceType.KING,PieceType.KING),
    new Move(4,12,PieceType.KING,PieceType.KING),
    new Move(4,11,PieceType.KING,PieceType.KING)
  )

   val BLACK_CASTLING_AND_ROOK_AND_R_PAWNS = List(
       new Move(56,57,PieceType.ROOK,PieceType.ROOK),
       new Move(56,58,PieceType.ROOK,PieceType.ROOK),
       new Move(56,59,PieceType.ROOK,PieceType.ROOK),
       new Move(63,62,PieceType.ROOK,PieceType.ROOK),
       new Move(63,61,PieceType.ROOK,PieceType.ROOK),
       new Move(48,40, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(48,32, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(55,47, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(55,39, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(60,61,PieceType.KING,PieceType.KING),
       new Move(60,59,PieceType.KING,PieceType.KING),
       new Move(60,52,PieceType.KING,PieceType.KING),
       new Move(60,53,PieceType.KING,PieceType.KING),
       new Move(60,51,PieceType.KING,PieceType.KING),
       new Move(60,62,PieceType.KING,PieceType.KING),
       new Move(60,58,PieceType.KING,PieceType.KING)
   )

//      val WHITE_NO_CASTLING_AND_ROOK_AND_R_PAWNS = List(
//       new Move(0,1,PieceType.ROOK,PieceType.ROOK),
//       new Move(0,2,PieceType.ROOK,PieceType.ROOK),
//       new Move(0,3,PieceType.ROOK,PieceType.ROOK),
//       new Move(7,6,PieceType.ROOK,PieceType.ROOK),
//       new Move(7,5,PieceType.ROOK,PieceType.ROOK),
//       new Move(8,16, PieceType.PAWN,PieceType.PAWN),
//       new Move(15,23, PieceType.PAWN,PieceType.PAWN),
//       new Move(8,24, PieceType.PAWN,PieceType.PAWN),
//       new Move(15,31, PieceType.PAWN,PieceType.PAWN),
//       new Move(4,3,PieceType.KING,PieceType.KING),
//       new Move(4,5,PieceType.KING,PieceType.KING),
//       new Move(4,12,PieceType.KING,PieceType.KING),
//       new Move(4,11,PieceType.KING,PieceType.KING),
//       new Move(4,13,PieceType.KING,PieceType.KING)
//   )


   val CLOSE_ARMIES_WHITE = List(
       new Move(24,33, PieceType.PAWN,PieceType.PAWN),
       new Move(25,32, PieceType.PAWN,PieceType.PAWN),
       new Move(25,34, PieceType.PAWN,PieceType.PAWN),
       new Move(26,33, PieceType.PAWN,PieceType.PAWN),
       new Move(26,35, PieceType.PAWN,PieceType.PAWN),
       new Move(27,34, PieceType.PAWN,PieceType.PAWN),
       new Move(27,36, PieceType.PAWN,PieceType.PAWN),
       new Move(28,35, PieceType.PAWN,PieceType.PAWN),
       new Move(28,37, PieceType.PAWN,PieceType.PAWN),
       new Move(29,36, PieceType.PAWN,PieceType.PAWN),
       new Move(29,38, PieceType.PAWN,PieceType.PAWN),
       new Move(30,37, PieceType.PAWN,PieceType.PAWN),
       new Move(30,39, PieceType.PAWN,PieceType.PAWN),
       new Move(31,38, PieceType.PAWN,PieceType.PAWN),
       new Move(17,32, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(17,34, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(17,0, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(17,2, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(17,11, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(22,39, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(22,37, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(22,7, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(22,5, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(22,12, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(16,8,PieceType.ROOK,PieceType.ROOK),
       new Move(16,0,PieceType.ROOK,PieceType.ROOK),
       new Move(23,15,PieceType.ROOK,PieceType.ROOK),
       new Move(23,7,PieceType.ROOK,PieceType.ROOK),
       new Move(18,9,PieceType.BISHOP,PieceType.BISHOP),
       new Move(18,0,PieceType.BISHOP,PieceType.BISHOP),
       new Move(18,11,PieceType.BISHOP,PieceType.BISHOP),
       new Move(18,4,PieceType.BISHOP,PieceType.BISHOP),
       new Move(19,11,PieceType.QEEN,PieceType.QEEN),
       new Move(19,3,PieceType.QEEN,PieceType.QEEN),
       new Move(19,10,PieceType.QEEN,PieceType.QEEN),
       new Move(19,1,PieceType.QEEN,PieceType.QEEN),
       new Move(19,12,PieceType.QEEN,PieceType.QEEN),
       new Move(19,5,PieceType.QEEN,PieceType.QEEN),
       new Move(20,12,PieceType.KING,PieceType.KING),
       new Move(20,13,PieceType.KING,PieceType.KING),
       new Move(20,11,PieceType.KING,PieceType.KING),
       new Move(21,14,PieceType.BISHOP,PieceType.BISHOP),
       new Move(21,7,PieceType.BISHOP,PieceType.BISHOP),
       new Move(21,12,PieceType.BISHOP,PieceType.BISHOP),
       new Move(21,3,PieceType.BISHOP,PieceType.BISHOP)
   )

   val CLOSE_ARMIES_BLACK = List(
       new Move(32,25, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(33,24, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(33,26, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(34,25, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(34,27, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(35,26, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(35,28, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(36,27, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(36,29, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(37,28, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(37,30, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(38,29, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(38,31, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(39,30, PieceType.BLACK_PAWN,PieceType.BLACK_PAWN),
       new Move(41,24, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(41,26, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(41,56, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(41,58, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(41,51, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(46,31, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(46,29, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(46,63, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(46,61, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(46,52, PieceType.KNIGHT,PieceType.KNIGHT),
       new Move(40,48,PieceType.ROOK,PieceType.ROOK),
       new Move(40,56,PieceType.ROOK,PieceType.ROOK),
       new Move(47,55,PieceType.ROOK,PieceType.ROOK),
       new Move(47,63,PieceType.ROOK,PieceType.ROOK),
       new Move(42,49,PieceType.BISHOP,PieceType.BISHOP),
       new Move(42,56,PieceType.BISHOP,PieceType.BISHOP),
       new Move(42,51,PieceType.BISHOP,PieceType.BISHOP),
       new Move(42,60,PieceType.BISHOP,PieceType.BISHOP),
       new Move(43,51,PieceType.QEEN,PieceType.QEEN),
       new Move(43,59,PieceType.QEEN,PieceType.QEEN),
       new Move(43,50,PieceType.QEEN,PieceType.QEEN),
       new Move(43,57,PieceType.QEEN,PieceType.QEEN),
       new Move(43,52,PieceType.QEEN,PieceType.QEEN),
       new Move(43,61,PieceType.QEEN,PieceType.QEEN),
       new Move(44,52,PieceType.KING,PieceType.KING),
       new Move(44,53,PieceType.KING,PieceType.KING),
       new Move(44,51,PieceType.KING,PieceType.KING),
       new Move(45,52,PieceType.BISHOP,PieceType.BISHOP),
       new Move(45,59,PieceType.BISHOP,PieceType.BISHOP),
       new Move(45,54,PieceType.BISHOP,PieceType.BISHOP),
       new Move(45,63,PieceType.BISHOP,PieceType.BISHOP)
   )

 "An initial position " should " give such list of Moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,INITIAL_BOARD_WHITE_MOVES))
  }

 "A position after 1.e4 " should " give such list of Moves for black" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,INITIAL_BOARD_BLACK_MOVES))
  }

  "A board after 1.e4 e5 " should " give such list ov moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,BOARD_AFTER_E4_E5))
   }

  "A board without pawns" should " give such list of moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,BOARD_WITHOUT_PAWNS_WHITE))
   }

    "A board without pawns" should " give such list of moves for black" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,BOARD_WITHOUT_PAWNS_BLACK))
   }

    "A board with close armies " should " give such list of moves for white" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("8/8/rnbqkbnr/pppppppp/PPPPPPPP/RNBQKBNR/8/8 w - - 0 1")

//    BoardTestHelper.printList(board.getAllMoves())
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,CLOSE_ARMIES_WHITE))
   }

    "A board with close armies " should " give such list of moves for black" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("8/8/rnbqkbnr/pppppppp/PPPPPPPP/RNBQKBNR/8/8 b - - 0 1")
//    BoardTestHelper.printList(board.getAllMoves())
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,CLOSE_ARMIES_BLACK))
   }


 "An almost initial position " should " give such list of Moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 1")
    BoardTestHelper.printList(board.getAllMoves())
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,BOARD_WITH_INTERCHECK))
  }

 "White castling position " should " give castling allowed for both sides" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/P6P/R3K2R w KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,WHITE_CASTLING_AND_ROOK_AND_R_PAWNS))
  }


  "White castling position against black without pawns " should " give castling allowed for both queen side" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/8/8/8/8/8/P6P/R3K2R w KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,WHITE_SHORT_CASTLING_AND_ROOK_AND_R_PAWNS))
  }

  "Black castling position " should " give castling allowed for both sides" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("r3k2r/p6p/8/8/8/8/P6P/R3K2R b KQkq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,BLACK_CASTLING_AND_ROOK_AND_R_PAWNS))
  }

 "White castling position but castling not allowed " should "  give no castling moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/P6P/R3K2R w kq - 0 1")
    assert(BoardTestHelper.compareUnnormalizedLists(board.getAllMoves() ,WHITE_CASTLING_NOT_ALLOWED_AND_ROOK_AND_R_PAWNS))
  }

  "White castling position but king might be attacked " should "  give no castling moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("2rqkr2/8/8/8/8/8/P6P/R3K2R w KQkq - 0 1")
    val blackMoves: List[Move] = board.getAllBlackMoves().flatten
    val fieldsAttackedByBlack : List[Int] = blackMoves.groupBy(_.to).map(_._2.head).map(_.to).toList
    val old : BoardSpecialEvents = board.getBoardSpecialEvents()
//    board.setBoardSpecialEvents()
    val newBoard: Board = BoardTestHelper.deepBoardCopy(board,
      new BoardSpecialEvents(old.getColorToMove(),old.isShortCastlingAllowedForWhite,
      old.isLongCastlingAllowedForWhite,old.isShortCastlingAllowedForBlack,
      old.isLongCastlingAllowedForBlack,0,0,0))
    //groupBy(_.to).map(_._2.head).map(_.to).toList
    assert(BoardTestHelper.compareUnnormalizedLists(newBoard.getAllMoves() ,WHITE_NO_CASTLING_AND_ROOK_AND_R_PAWNS))

  }

  "White castling position but king might be attacked from one side " should "  give one castling moves" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("4kr2/8/8/8/8/8/P6P/R3K2R w KQkq - 0 1")
    val blackContext : BoardSpecialEvents = board.getBoardSpecialEvents()
    val boardWithProperContext = BoardTestHelper.deepBoardCopy(board,
      new BoardSpecialEvents(1,blackContext.isShortCastlingAllowedForWhite,
        blackContext.isLongCastlingAllowedForWhite,blackContext.isShortCastlingAllowedForBlack,
        blackContext.isLongCastlingAllowedForBlack,0,0,0)
    )
    val blackMoves: List[Move] = boardWithProperContext.getAllBlackMoves().flatten
    val fieldsAttackedByBlack : List[Int] = blackMoves.groupBy(_.to).map(_._2.head).map(_.to).toList
    val old : BoardSpecialEvents = board.getBoardSpecialEvents()
    //    board.setBoardSpecialEvents()
    val newBoard: Board = BoardTestHelper.deepBoardCopy(board,
      new BoardSpecialEvents(old.getColorToMove(),old.isShortCastlingAllowedForWhite,
        old.isLongCastlingAllowedForWhite,old.isShortCastlingAllowedForBlack,
        old.isLongCastlingAllowedForBlack,0,0,0))
    //groupBy(_.to).map(_._2.head).map(_.to).toList
    assert(BoardTestHelper.compareUnnormalizedLists(newBoard.getAllMoves() ,WHITE_QUEEN_SIDE_CASTLING_AND_ROOK_AND_R_PAWNS))

  }

   "Board after 1.e4 " should " looks like " in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
    val boardAfterMove = board.getBoardAfterMove(new Move(12,28,PieceType.PAWN,PieceType.PAWN))

  }

 "initial boards " should " generate 20 new positions " in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 43 0 1")
    val allPossibleBoards : List[Board] = board.generateAllReachableBoards()

    assert(allPossibleBoards.size == 20)
  }

  "pawn promotion " should " generate 9 new positions " in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("8/7P/8/8/8/7K/k7/8 w - 0 0 1")
    val allPossibleBoards : List[Board] = board.generateAllReachableBoards()
    assert(allPossibleBoards.size == 9)
  }

 "White castling position " should " should give 16 new positions" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("rnbqkbnr/pppppppp/8/8/8/8/P6P/R3K2R w KQkq - 0 1")
    val allPossibleBoards : List[Board] = board.generateAllReachableBoards()

    assert(allPossibleBoards.size == 16)
  }

  "White pawn attack " should " should give 8 new positions" in {
    val board: Board = FENParser.parseFENDescriptiontToBoard("8/2p1p3/3P4/8/8/8/K6k/8 w - - 0 1")
    val allPossibleBoards : List[Board] = board.generateAllReachableBoards()
    assert(allPossibleBoards.size == 8)
  }

  "position after move Xk " should " be like " in {
    val initialPosition: Board = FENParser.parseFENDescriptiontToBoard("8/8/8/8/2K5/7R/1k5R/8 w - 43 0 1")
    BoardTestHelper.printBoard(initialPosition)
    val newBoard: Board = initialPosition.generateBoardAfterMove(new Move(15, 9, PieceType.ROOK, PieceType.ROOK))
    BoardTestHelper.printBoard(newBoard)
  }

  "in this position black king " should " be attacked " in {
     val board: Board = FENParser.parseFENDescriptiontToBoard("k7/1P6/1K6/8/8/6B1/5p2/8 b - 43 0 1")
     BoardTestHelper.printBoard(board)
     assert(board.isBlackKingAttacked())
  }


}
