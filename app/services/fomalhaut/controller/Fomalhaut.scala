package services.fomalhaut.controller

/**
 * Created by kniemiec on 18.02.16.
 */
object Fomalhaut {

  def main(args: Array[String]) : Unit = {
    println("welcome in chess program")
    new CommandInterface().playGame()

  }

}
