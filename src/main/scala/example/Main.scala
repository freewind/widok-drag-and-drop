package example

import org.widok._
import org.widok.bindings.HTML.Container.Generic
import org.widok.bindings.HTML.Image
import org.widok.html._

object Main extends PageApplication {

  val userOnDragging = Var(Option.empty[User])

  case class User(name: String, image: Option[String])

  val users = Buffer[User](User("Jack", None), User("Mike", Some("images/aaa.jpeg")), User("Jeff", None))

  def view() = div(
    div(
      h1("Welcome to Widok!"),
      p("Drag the image into another box!")
    ),
    div(
      users.map(user => MyBox(user))
    )
  )

  def ready() {
    log("Page loaded.")
  }


  object MyBox {
    def apply(user: User) = {

      def toImage(src: String) = {
        val image = new MyImage(src)
        image.onDragStart(_ => userOnDragging := Some(user))
        image.onDragEnd(_ => userOnDragging := None)
      }

      val image = user.image.map(toImage).getOrElse(span())

      new MyBox(user, image)
    }
  }

  class MyBox(user: User, image: View) extends Generic(image) {
    private val dragover = Var(false)
    css("box")
    cssState(dragover, "dragover")
    onDragEnter(_ => dragover := true)
    onDragOver(e => e.preventDefault())
    onDragLeave(_ => dragover := false)
    onDrop { e =>
      e.preventDefault()
      dragover := false
      for {
        draggingU <- userOnDragging.get
        if draggingU != user
      } {
        users.replace(user, user.copy(image = draggingU.image))
        users.replace(draggingU, draggingU.copy(image = None))
      }
    }
  }

  class MyImage(src: String) extends Image(src) {
    private val dragging = Var(false)
    attribute("draggable", "true")
    cssState(dragging, "dragging")
    onDragStart { e =>
      dragging := true
    }
    onDragEnd { _ =>
      dragging := false
    }
  }

}





