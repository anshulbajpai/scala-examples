class Weight(val value: Int) extends AnyVal {
  def +(weight: Weight) = new Weight(value + weight.value)

  def toPound = new Weight(value * 2)
}

new Weight(4) + new Weight(5)

class RichIntX(val self: Int) extends AnyVal {
  def toHexStringX: String = java.lang.Integer.toHexString(self)
}


// supposedly work
//2.toHexStringX









