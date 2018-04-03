package views

import models.form.{AssignmentForm, UserLoginForm, UserSignupForm}
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.i18n.MessagesProvider
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}



class addAssignmentSpec extends PlaySpec with Mockito {

  "addAssignment page======" in new App {

    val request = FakeRequest(GET,"/userProfile")
    val mockedForm = mock[AssignmentForm]
    val mockedFlash = mock[play.api.mvc.Flash]
    val mockedMessageProvider = mock[MessagesProvider]
    val mockedSession = mock[play.api.mvc.Session]


    val html = views.html.addAssignment(mockedForm.assignmentFormMapping)(request,mockedMessageProvider,mockedFlash,mockedSession)

    contentAsString(html) must include("Title")
  }


}