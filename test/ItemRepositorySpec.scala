import org.scalatest.FlatSpec
import repository._

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */
class ItemRepositorySpec extends FlatSpec {
  "ItemRepository" should "have 2 items" in {
    val repo = new ItemRepository

    assert(repo.count() == 2)
  }

  "Adding new Item to ItemRepository" should "return 3" in {
    val repo = new ItemRepository

    repo.add(new Item(id=1, name="Item 3", quantity = 3))

    assert(repo.count() == 3)
  }

  "Get item by Id" should "return that item" in {
    val repo = new ItemRepository

    val item = new Item(id = 22, name = "Item 3", quantity = 3)
    repo.add(item)
    
    assert(repo.get(22).contains(item))
    assert(repo.get(23).isEmpty)



  }
}
