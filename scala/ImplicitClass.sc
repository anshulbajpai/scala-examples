object ImplicitClass{
  case class Person(name : String, age : Int)

  implicit class PersonX(person : Person){
   def speak = println(s"My name is ${person.name}")
  }

  Person("foo", 10).speak

  case class Pound(value : BigDecimal)
  case class Kilo(value : BigDecimal)

  implicit def kiloToPound(kilo : Kilo) = Pound(kilo.value * 1.5)

  def poundToString(pound : Pound) = pound.toString

  poundToString(Pound(10))
  poundToString(Kilo(10))

}



