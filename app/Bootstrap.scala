  import javax.inject.Inject

import com.google.inject.{AbstractModule, ImplementedBy}
import repository.{Item, ItemList}

@ImplementedBy(classOf[BootstrapItemList])
trait Bootstrap {
  def start(): Unit
}

/**
  * (c) Piotr Plenik <piotr.plenik@gmail.com>
  *
  * For the full copyright and license information, please view the LICENSE
  * file that was distributed with this source code.
  */
class BootstrapItemList @Inject()(items: ItemList) extends Bootstrap {
  def start() = {
    items.items +=
      Item(
        id = 1,
        name = "Item A",
        quantity = 20
      )

    items.items +=
      Item(
        id = 2,
        name = "Item B",
        quantity = 10
      )
    println("Loaded sample items")
  }

  start()
}

class Module extends AbstractModule {
  override def configure() = {
    bind(classOf[Bootstrap]).to(classOf[BootstrapItemList]).asEagerSingleton()
  }
}
