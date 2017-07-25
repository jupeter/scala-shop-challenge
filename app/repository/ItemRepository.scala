package repository

import javax.inject.Singleton

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */

case class Item(id: Long, name: String, var quantity: Int)

@Singleton
case class ItemList(items: Seq[Item]) {
  def this() = this(Seq(
    Item(
      id = 1,
      name = "Item A",
      quantity = 20
    ),
    Item(
      id = 2,
      name = "Item B",
      quantity = 10
    )
  ))

  def getItem(id: Long): Item = items.filter(p => id == p.id).head


  def takeOff(id: Long, amount: Int): Unit = getItem(id).quantity -= amount
}

case class CheckoutItem(id: Long, name: String, quantity: Int = 0)

@Singleton
case class Checkout(var items: List[CheckoutItem] = List()) {

  def this(itemList: ItemList) = this(
    itemList.items.map(i => CheckoutItem(i.id, i.name)).toList
  )

  def getItem(id: Long): CheckoutItem = items.filter(p => id == p.id).head


}