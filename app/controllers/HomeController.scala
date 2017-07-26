package controllers

import javax.inject._

import play.api.mvc._
import repository.{Checkout, ItemList, _}
import play.api.data._
import play.api.data.Forms._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(mcc: MessagesControllerComponents, items: ItemList)
  extends MessagesAbstractController(mcc) {

  val itemListForm: Form[Checkout] = Form(
    mapping(
      "items" -> list(
        // note: skipping `name` field
        mapping(
          "id" -> longNumber,
          "quantity" -> number
        )
        // itemListForm -> CheckoutItem
        ((id, quantity) => CheckoutItem(id, "", quantity))
        // CheckoutItem -> itemListForm
        ((ci: CheckoutItem) => Some(ci.id, ci.quantity))
      )
    )(Checkout.apply)(Checkout.unapply)
  )

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: MessagesRequest[AnyContent] =>
    val checkout = new Checkout(items)

    Ok(views.html.index(items, itemListForm.fill(checkout)))
  }

  def submit() = Action { implicit request: MessagesRequest[AnyContent] =>
    itemListForm.bindFromRequest.fold(
      formWithErrors => {
        println("Errors: " + formWithErrors)
        BadRequest(views.html.index(items, formWithErrors))
      },
      checkout => {
        checkout.items.map(i => items.takeOff(i.id, i.quantity))

        Ok(views.html.submit(items, checkout))
      }
    )
  }

}
