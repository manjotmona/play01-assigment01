package views


import models.form.{UserLoginForm, UserSignupForm}
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class userProfileSpec extends PlaySpec with Mockito {

  "userProfile page======" in new App {

    val request = FakeRequest(GET,"/userProfile")
    val mockedForm = mock[UserSignupForm]
    val mockedFlash = mock[play.api.mvc.Flash]
    val mockedMessageProvider = mock[MessagesProvider]
    val mockedSession = mock[play.api.mvc.Session]


    val html = views.html.userProfile(mockedForm.userSignupForm)(request,mockedMessageProvider,mockedSession)

    contentAsString(html) must include("Password")
  }


}