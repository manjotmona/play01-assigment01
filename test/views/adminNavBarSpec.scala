package views


import models.form.{AssignmentForm, UserLoginForm, UserSignupForm}
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class adminNavBarSpec extends PlaySpec with Mockito {

  "adminNavBar page======" in new App {

    val request = FakeRequest(GET,"/adminNav")
    val mockedForm = mock[AssignmentForm]
    val mockedFlash = mock[play.api.mvc.Flash]
    val mockedMessageProvider = mock[MessagesProvider]
    val mockedSession = mock[play.api.mvc.Session]


    val html = views.html.adminNavBar()(mockedSession)

    contentAsString(html) must include("View User")
  }

}