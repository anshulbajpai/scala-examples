val names = List("John", "Mary", "Steve")
names map  {_.charAt(0)}
names flatMap {_.toCharArray filter {_.isLower} map {_.toUpper}}

val lowerLettersAsUpper = for{
  eachName <- names
  letters <- eachName.toCharArray
  upper <-  Some(letters.toUpper) if letters.isLower
} yield upper

case class Address(city : String)

case class Person(name : String, age : Int, address : Option[example.Address])


def validate(person : Person) = for{
       address <- person.address
       city <- Some(address.city) if city == "Pune"
} yield true

validate(Person("foo", 10, None))
validate(Person("foo", 10, Some(Address("pune"))))
validate(Person("foo", 10, Some(Address("Pune"))))

object Pune{
  def unapply(address : example.Address) = address match {
    case Address("Pune") => Some(true)
    case _ => None
  }
}

def validateX(person : Person) = for{
 Pune(true) <- person.address
}yield true

validateX(Person("foo", 10, None))
validateX(Person("foo", 10, Some(Address("pune"))))
validateX(Person("foo", 10, Some(Address("Pune"))))

