package services.fomalhaut.controller

import services.fomalhaut.controller.BoardAction.BoardAction

/**
  * Created by kniemiec on 05.09.16.
  */
object BoardActionConverter {
  def parseStringToBoardAction(userAction: String): BoardAction = userAction match{
    case "new" => BoardAction.NEW
    case "draw" => BoardAction.DRAW
    case "resign" => BoardAction.RESIGN
    case  _ => BoardAction.NOT_BOARD_ACTION
  }

}
