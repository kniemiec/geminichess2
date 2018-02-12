package services.fomalhaut.fieldAvailability

object MoveValidator {
  def isValidMove( line: Int, row: Int): Boolean = {
    ( line < 8 ) && ( line  >= 0 ) && ( row < 8 ) && ( row >= 0 )
  }

  def isValidMove( to : Int): Boolean = {
    ( (to % 8) < 8 ) && ( (to % 8)  >= 0 ) && ( (to / 8 )< 8 ) && ( (to /8)  >= 0 )
  }

}
