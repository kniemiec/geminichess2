package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import services.fomalhaut.controller.{BoardAction, BoardActionConverter}
import services.fomalhaut.controller.BoardAction.BoardAction
import services.fomalhaut.engine.Engine

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class MoveController @Inject() extends Controller {
  val INITIAL_FEN : String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 1 0"

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def move( userMove: String)  = Action { request =>
    processUserRequest(request.session,userMove)
//    request.session.get("fenData").map(generateResponse(request.session,userMove,_))
//      .getOrElse {
//      Ok("Welcome in game!").withSession( "fenData" -> "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
//    }
  }

  def lastMove = Action {
    Ok("last move")
  }

  private def processUserRequest(session: Session, userAction : String): Result = {
    BoardActionConverter.parseStringToBoardAction(userAction) match {
      case BoardAction.NEW => Ok("Welcome in new game!").withSession("fenData" -> INITIAL_FEN)
      case BoardAction.DRAW => Ok("You proposed a draw").withSession(session - "fenData")
      case BoardAction.RESIGN => Ok("I Won!").withSession(session - "fenData")
      case _ => session.get("fenData").map(generateResponse(session, userAction, _))
            .getOrElse { Ok("Incorrect command")
      }
    }
  }

  private def generateResponse(session: Session, userMove: String, fenData: String) : Result  = {
    val ( nextMove : String, currentFen : String) = getEngineMove(fenData,userMove)
    println(s"new fen generated :"+currentFen)
    Ok(Json.obj( "move" ->  nextMove )).withSession(session + ("fenData" -> currentFen))
  }

  private def getEngineMove(fenData : String, userMove: String) : (String, String) = {
    println(s"calculations for position :"+fenData)
    val engine : Engine = new Engine(fenData)
    engine.getResponseMove(userMove)

//    fen => Ok(Json.obj( "move" ->  getEngineMove(fen,userMove)))
  }

}
