import org.scalatest.FlatSpec
import repository._

import scala.collection.mutable.ListBuffer
import org.scalatest.OptionValues._
import org.scalatest.Matchers._

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */
class ItemListSpec extends FlatSpec {
  "Get item by Id" should "return that item" in {
    val list = new ListBuffer[Item]
    val item1 = Item(1, "Item 1", 5)
    val item2 = Item(2, "Item 2", 12)
    list += item1
    list += item2


    val itemLists = ItemList(list)

    assert(item1 == itemLists.getItem(1).getOrElse(false))
    assert(item2 == itemLists.getItem(2).getOrElse(false))
    assert(item1 != itemLists.getItem(2).getOrElse(false))
    assert(false == itemLists.getItem(99).getOrElse(false))
  }

  "Take off from item" should "take off quantity" in {
    val list = new ListBuffer[Item]
    val item1 = Item(1, "Item 1", 5)

    val itemLists = ItemList(list)
    list += item1

    assert(5 == item1.quantity)

    itemLists.takeOff(1, 99)
    itemLists.getItem(1).value should have (
      'quantity (5)
    )

    itemLists.takeOff(1, 2)
    itemLists.getItem(1).value should have (
      'quantity (3)
    )

    itemLists.takeOff(1, 3)
    itemLists.getItem(1).value should have (
      'quantity (0)
    )
    
    itemLists.takeOff(1, 5)
    itemLists.getItem(1).value should have (
      'quantity (0)
    )


  }
}
