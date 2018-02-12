package services.fomalhaut.fieldAvailability

object OccupationChecker {

  def isOccupiedByOwn( x: Int, occupiedByOwn: List[Int]): Boolean = {
    occupiedByOwn.contains(x)
  }

  def isOccupiedByEnemy( x: Int, occupiedByEnemy: List[Int]): Boolean = {
    occupiedByEnemy.contains(x)
  }

  def isOccupiedBy(field : Int, occupied: List[Int]): Boolean = occupied match {
    case Nil => false
    case y :: ys => y == field || isOccupiedBy( field, ys)
  }

}
