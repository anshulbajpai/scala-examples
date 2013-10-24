case class PersonX(name : String, age : Int)

var baz = PersonX("baz", 15)

val PersonX(nameX, ageX) = baz

println((nameX,ageX))

baz match {
  case PersonX("baz", _) => println("Found baz")
  case _ => println("Not found!!")
}

class Person(val name : String, val age : Int)

val foo = new Person("foo", 10)

object Person{
  def apply(name : String, age : Int) = new Person(name, age)
  def unapply(person : Person) = Some(person.name, person.age)
}

val bar = Person("bar", 11)
val Person(name,age) = foo

println((name,age))

foo match {
  case Person("foo", _) => println("Found foo")
  case _ => println("Not found!!")
}

case class Request(url : String)

object DomainPort {
  val pattern = "http://(.*?):(.*?)/.*".r
  def unapply(request : Request) = request.url match {
      case pattern(domain, port) => Some(domain, port.toInt)
      case _ => None
  }
}

object MyDomain{
  def unapply(domain : String) = domain match {
    case "mydomain" => Some(true)
    case _ => Some(false)
  }
}

val request = Request("http://mydomain:8080/controller/action")

request match {
  case DomainPort(MyDomain(true), 8080) => println("Found request at mydomain and port 8080")
  case _ => println("Request ignored")
}