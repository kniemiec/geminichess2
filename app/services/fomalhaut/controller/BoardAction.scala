package services.fomalhaut.controller

/**
  * Created by kniemiec on 05.09.16.
  */
object BoardAction extends Enumeration{
  type BoardAction = Value
  val NEW, DRAW, RESIGN, NOT_BOARD_ACTION = Value

}
