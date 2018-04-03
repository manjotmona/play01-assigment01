package views



import models.form.UserLoginForm
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class loginSpec extends PlaySpec with Mockito {

  "login page======" in new App {

    val request = FakeRequest(POST,"/login").withFlash().withHeaders()
    val mockedForm = mock[UserLoginForm]
    val mockedFlash = mock[play.api.mvc.Flash]
    val mockedMessageProvider = mock[MessagesProvider]


    val html = views.html.login(mockedForm.userLoginForm)(request,mockedMessageProvider,mockedFlash)

    contentAsString(html) must include("Username")
  }


}