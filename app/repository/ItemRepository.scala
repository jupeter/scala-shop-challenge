package repository

import javax.inject.Singleton

import scala.collection.mutable.ListBuffer

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */

case class Item(id: Long, name: String, quantity: Int)

@Singleton
case class ItemList(items: Seq[Item])

trait ItemDao {
  def add(item: Item): ListBuffer[Item]
  def findAll: ListBuffer[Item]
  def get(id: Long): Option[Item]
  def count(): Int
}

@Singleton
class ItemRepository extends ItemDao {
  val list = ListBuffer(
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
  )

  def add(item: Item): ListBuffer[Item] = list += item

  def findAll: ListBuffer[Item] = list

  def get(id: Long): Option[Item] = list.filter(_.id == id).headOption

  def count(): Int = list.size
}
