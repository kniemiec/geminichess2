package controllers

import javax.inject._

import play.api.libs.json.{__, Reads}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc._

import services.fomalhaut.controller.BoardTextRepresentation
import services.fomalhaut.helper
import services.fomalhaut.helper.FENParser
import services.fomalhaut.pieces.BoardTestHelper

@Singleton
class FenController @Inject() extends Controller {

  implicit val readsCreateItem = Json.reads[FenItem]

  def fenToBoard() = Action(parse.json) { implicit request =>
    request.body.validate[FenItem] match {
      case JsSuccess(fen, _) => Ok(Json.toJson(Json.toJson((new BoardTextRepresentation(FENParser.parseFENDescriptiontToBoard(fen.fen.toString()))).toString())))
      case JsError(errors) => BadRequest
    }
//    Ok(Json.toJson((new BoardTextRepresentation(FENParser.parseFENDescriptiontToBoard(request.body.toString))).toString()))
//    Ok(Json.toJson(BoardTestHelper.getBoardString(FENParser.parseFENDescriptiontToBoard(request.body.toString))))
  }

  def boardToFen() = Action { request => Ok(Json.toJson(request.body.toString())) }

}
