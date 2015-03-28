package example

import org.widok._
import org.widok.bindings.HTML.Container.Generic
import org.widok.bindings.HTML.Image
import org.widok.html._

object Main extends PageApplication {

  val userOnDragging = Opt[User]()

  case class User(name: String, image: Opt[String])

  val users = Buffer[User](User("Jack", Opt()), User("Mike", Opt("images/aaa.jpeg")), User("Jeff", Opt()))

  def view() = div(
    div(
      h1("Welcome to Widok!"),
      p("Drag the image into another box!")
    ),
    div(
      users.map(user => div(new MyBox(user), user.image))
    )
  )

  def ready() {
    log("Page loaded.")
  }

  def toImage(user: User) = {
    user.image.map { img =>
      val image = new MyImage(img)
      image.isDragging.attach {
        if (_) userOnDragging := user
        else userOnDragging.clear()
      }
      image
    }
  }

  class MyBox(user: User) extends Generic(toImage(user)) {
    private val dragover = Var(false)
    css("box")
    cssState(dragover, "dragover")
    onDragEnter(_ => dragover := true)
    onDragOver(e => e.preventDefault())
    onDragLeave(_ => dragover := false)
    onDrop { e =>
      e.preventDefault()
      dragover := false
      if (!userOnDragging.contains$(user)) {
        val src = userOnDragging.get
        user.image := src.image.get
        src.image.clear()
      }
    }
    //    onClick {
    //      _ =>
    //        println("########### on click, the image should be removed !!!!")
    //        user.image.clear()
    //    }
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
    def isDragging: ReadChannel[Boolean] = dragging
  }

}





