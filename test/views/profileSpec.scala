package views


import models.form.UserLoginForm
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class profileSpec extends PlaySpec with Mockito {

  "profile page======" in new App {

    val request = FakeRequest(GET,"/login")

    val mockedSession = mock[play.api.mvc.Session]



    val html = views.html.profile()(mockedSession)

    contentAsString(html) must include("Username")
  }


}
