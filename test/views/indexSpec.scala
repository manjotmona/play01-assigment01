package views

import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class indexSpec extends PlaySpec with Mockito {

  "index page======" in new App {

   // val request = FakeRequest()
    //val mockedForm = mock[UserForm]
    val html = views.html.index()

    contentAsString(html) must include("PLAY ASSIGNMENT!")
  }



  "store page======" in new App {

    // val request = FakeRequest()
    //val mockedForm = mock[UserForm]
    val html = views.html.index()

    contentAsString(html) must include("PLAY ASSIGNMENT!")
  }

}