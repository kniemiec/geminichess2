package controllers

import org.scalatestplus.play._
import play.api.test._
import play.api.mvc.{Result, Results}
import play.test.Helpers._

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by kniemiec on 29.11.16.
  */
class FenControllerTest extends PlaySpec with Results {

/*
  "FenController#fenToBoard" should {
    "should be valid" in {
      val controller = new FenController()
      val result: Future[Result] = controller.boardToFen().apply(FakeRequest(Helpers.POST,
        controllers.routes.FenController.boardToFen().url,
        FakeHeaders(),
        "{"fen" : "cos"}"

        ))

      result.onComplete({
        case Success(result) => {
          val bodyText: String = result.toString()
          print(bodyText)
          bodyText mustBe "ok"
        }
        case Failure(_) => {
          print("failure")
        }
      })
    }
  }
*/
}