package repository

import javax.inject.Singleton
import scala.collection.mutable.ListBuffer

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */

trait ItemTrait {
  var quantity: Int

  def id: Long

  def name: String
}

case class Item(override val id: Long, override val name: String, override var quantity: Int)
  extends ItemTrait

case class CheckoutItem(override val id: Long, override val name: String, override var quantity: Int = 0)
  extends ItemTrait


trait ItemListTrait[A <: ItemTrait] {
  def items: ListBuffer[A]

  def getItem(id: Long): Option[A] = {
    try {
      Some(items.filter(p => p.id == id).head)
    } catch {
      case e: NoSuchElementException => None
    }
  }
}

@Singleton
case class ItemList(items: ListBuffer[Item]) extends ItemListTrait[Item] {
  def this() = this(ListBuffer())

  def takeOff(id: Long, amount: Int): Unit = getItem(id) match {
    case Some(i) => i.quantity -= amount
    case None => None
  }
}


@Singleton
case class Checkout(var items: ListBuffer[CheckoutItem] = ListBuffer()) extends ItemListTrait[CheckoutItem] {
  def this(items: List[CheckoutItem]) = this(items.to[ListBuffer])

  def this(itemList: ItemList) = this(
    itemList.items.map(i => CheckoutItem(i.id, i.name)).toList
  )
}