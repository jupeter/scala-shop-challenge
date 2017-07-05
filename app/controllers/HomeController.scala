package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import repository.{ItemRepository, _}
import play.api.data._
import play.api.data.Forms._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, repo: ItemRepository, mcc: MessagesControllerComponents)
  extends MessagesAbstractController(mcc) {


  val itemForm = Form(
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "quantity" -> number(min = 0)
    )(Item.apply)(Item.unapply)
  )

  val itemListForm = Form(
    mapping(
      "items" -> seq(itemForm.mapping)
    )(ItemList.apply)(ItemList.unapply)
  )

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items = repo.findAll

    itemListForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(items, formWithErrors))
      },
      userData => {
        Redirect(routes.HomeController.submit())
      }
    )

    Ok(views.html.index(items, itemListForm))
  }

  def submit() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items = repo.findAll

    Ok(views.html.submit(items))
  }
}
