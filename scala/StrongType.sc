
trait Validated {
  type No <: Validated
  type Yes <: Validated
}

type Error = String

case class Address[V <: Validated](line1: String, line2: String, city: String)

case class Order(id: String, address: Address[Yes])

trait AddressValidator {
  def validate(address: Address[_]): Either[Error, Address[Yes]]
}

trait OrderRepository {
  def get(id: String): Either[Error, Order]

  def save(order: Order): Either[Error, Order]
}

trait OrderService {

  val addressValidator: AddressValidator
  val orderRepository: OrderRepository

  def addAddressToOrder(id: String, newAddress: Address[_]): Either[Error, Order] = for {
    validatedAddress <- addressValidator.validate(newAddress).right
    order <- orderRepository.get(id).right
    storedOrder <- orderRepository.save(order.copy(address = validatedAddress)).right
  } yield storedOrder
}


