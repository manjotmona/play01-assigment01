package views


import models.form.{UserLoginForm, UserSignupForm, forgetPasswordForm}
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class forgetPasswordSpec extends PlaySpec with Mockito {

  "forgetPassword page======" in new App {

    val request = FakeRequest(POST,"/forgetPassword")
    val mockedForm = mock[forgetPasswordForm]
    val mockedFlash = mock[play.api.mvc.Flash]
    val mockedMessageProvider = mock[MessagesProvider]



    val html = views.html.forgetPassword(mockedForm.userLoginForm)(request,mockedMessageProvider,mockedFlash)

    contentAsString(html) must include("Password")
  }


}