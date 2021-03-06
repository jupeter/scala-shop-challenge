package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import repository._

import scala.collection.mutable.ListBuffer

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val list = ItemList(ListBuffer(Item(1, "Item name", 12)))

      val controller = new HomeController(stubControllerComponents(), list)
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Currently available in out shop")
      contentAsString(home) must include("Item name")
    }

    "order specific items" in {
      val list = ItemList(ListBuffer(Item(1, "Item name", 12)))

      val controller = new HomeController(stubControllerComponents(), list)
      val submit = controller.submit().apply(
        FakeRequest(
          POST,
          "/submit",
          FakeHeaders(),
          """ {"name": "New Group", "collabs": ["foo", "asdf"]} """
        ).withFormUrlEncodedBody(
          "items[0].id" -> "1",
          "items[0].quantity" -> "5"
        )
      )

      status(submit) mustBe OK
      contentType(submit) mustBe Some("text/html")
      contentAsString(submit) must include("Item name: 5")
    }
  }
}
