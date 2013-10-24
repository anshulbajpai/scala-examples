import java.util.Date

case class Order(id : String, date : Date, price : BigDecimal)

def isOrderAfterMarch(order : example.Order): Boolean = order.date.after(new Date(2013, 02, 28))

def hasPriceGreaterThanTen(order : example.Order): Boolean = order.price > 10

def applyPromotionalDiscount1(order : example.Order) = if (isOrderAfterMarch(order) || hasPriceGreaterThanTen(order)) order.copy(price = order.price * 0.9) else order

applyPromotionalDiscount1(Order("pen", new Date(2013,02, 01), 9))
applyPromotionalDiscount1(Order("pen", new Date(2013,03, 01), 9))
applyPromotionalDiscount1(Order("pen", new Date(2013,02, 01), 11))

def applyPromotionalDiscount2(order : example.Order) = order match {
  case Order(_, date, price) if date.after(new Date(2013, 02, 28)) || price > 10  => order.copy(price = order.price * 0.9)
  case _ => order
}

applyPromotionalDiscount2(Order("pen", new Date(2013,02, 01), 9))
applyPromotionalDiscount2(Order("pen", new Date(2013,03, 01), 9))
applyPromotionalDiscount2(Order("pen", new Date(2013,02, 01), 11))

object AfterMarch {
  def unapply(date : Date) = if(date.after(new Date(2013, 02, 28))) Some(true) else None
}
object PriceGreaterThanTen {
  def unapply(price : BigDecimal) = if(price > 10) Some(true) else None
}
def applyPromotionalDiscount3(order : example.Order) = order match {
  case Order(_, AfterMarch(true), _)  => order.copy(price = order.price * 0.9)
  case Order(_, _, PriceGreaterThanTen(true))  => order.copy(price = order.price * 0.9)
  case _ => order
}
applyPromotionalDiscount3(Order("pen", new Date(2013,02, 01), 9))
applyPromotionalDiscount3(Order("pen", new Date(2013,03, 01), 9))
applyPromotionalDiscount3(Order("pen", new Date(2013,02, 01), 11))

val newToString = new PartialFunction[Any,String] {
  def apply(v1: Any): String = if (isDefinedAt(v1)) v1.toString else throw new IllegalArgumentException

  def isDefinedAt(x: Any): Boolean = x match {
    case _ : Int => true
    case _ : Boolean => true
    case _ : String => false
  }
}
newToString(1)
newToString(true)
//newToString("foo")
val newToStringX : PartialFunction[Any, String] = {
  case x : Int => x.toString
  case x : Boolean => x.toString
}
newToStringX(1)
newToStringX(true)
//newToStringX("foo")


def isAfterMarch : PartialFunction[example.Order,example.Order] = {
  case order @ Order(_, AfterMarch(true), _) => order
}
def isPriceGreaterThanTen : PartialFunction[example.Order,example.Order] = {
  case order@Order(_, _, PriceGreaterThanTen(true)) => order
}
def promoteOrder : PartialFunction[example.Order, example.Order] = {
  case order : example.Order => order.copy(price = order.price * 0.9)
}
def applyPromotionalDiscount4(order : example.Order) = isAfterMarch orElse isPriceGreaterThanTen andThen promoteOrder applyOrElse(order,{o : example.Order => o})
applyPromotionalDiscount4(Order("pen", new Date(2013,02, 01), 9))


applyPromotionalDiscount4(Order("pen", new Date(2013,03, 01), 9))


applyPromotionalDiscount4(Order("pen", new Date(2013,02, 01), 11))
