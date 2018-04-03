package views

import models.form.{UserLoginForm, UserSignupForm}
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class signupSpec extends PlaySpec with Mockito {

  "signup page======" in new App {

    val request = FakeRequest(POST,"/login").withFlash().withHeaders()
    val mockedForm = mock[UserSignupForm]

    val mockedMessageProvider = mock[MessagesProvider]


    val html = views.html.signup(mockedForm.userSignupForm)(request,mockedMessageProvider)

    contentAsString(html) must include("First Name")
  }


}